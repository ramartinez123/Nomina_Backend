package com.nomina.backend.service;

import com.nomina.backend.model.*;
import com.nomina.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class LiquidacionSueldoIdService {

	@Autowired
	private EmpleadoRepository empleadoRepository;

	@Autowired
	private HistoricoValoresCategoriaRepository historicoValoresCategoriaRepository;

	@Autowired
	private SalarioExcedenteRepository salarioExcedenteRepository;

	@Autowired
	private DetalleLiquidacionRepository detalleLiquidacionRepository;

	@Autowired
	private ConceptoSalarialRepository conceptoSalarialRepository;

	@Autowired
	private AdicionalPermanenciaRepository adicionalPermanenciaRepository;
	
	@Autowired
	private LiquidacionNovedadesService liquidacionServiceNovedades;

	public void realizarLiquidacionPorEmpleado(Integer empleadoId) {
	    Empleado empleado = empleadoRepository.findById(empleadoId)
	            .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

	    LocalDate now = LocalDate.now();
	    Date fechaLiquidacion = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
	    LocalDate periodoDate = now.withDayOfMonth(1);
	    Date periodo = Date.from(periodoDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

	    if (empleado.getConvenio().getIdConvenio() == 1) {
	        Integer idCategoria = empleado.getCategoria().getIdCategoria();
	        Optional<HistoricoValoresCategoria> optionalHistorico = historicoValoresCategoriaRepository.findById(idCategoria);

	        if (optionalHistorico.isPresent()) {
	            HistoricoValoresCategoria ultimoHistorico = optionalHistorico.get();
	            Integer montoCategoria = ultimoHistorico.getSalario();
	            Integer montoAlmuerzo = ultimoHistorico.getAlmuerzo();

	            // Detalle de liquidación para salario
	            guardarDetalleLiquidacion(empleado, montoCategoria, 2, fechaLiquidacion, periodo);
	            // Detalle de liquidación para almuerzo
	            guardarDetalleLiquidacion(empleado, montoAlmuerzo, 4, fechaLiquidacion, periodo);

	            // Adicional por permanencia
	            calcularAdicionalPermanencia(empleado, fechaLiquidacion, periodo);
	        }
	    }

	    // Revisar salario excedente
	    Optional<SalarioExcedente> salariosExcedentes = salarioExcedenteRepository.findByEmpleado_Id(empleado.getId());
	    if (salariosExcedentes.isPresent()) {
	        SalarioExcedente salarioExcedente = salariosExcedentes.get();
	        guardarDetalleLiquidacion(empleado, salarioExcedente.getValor(), salarioExcedente.getConceptoSalarial().getId(), fechaLiquidacion, periodo);
	    }
	    
	    liquidacionServiceNovedades.procesarNovedades();
	    
	}

	private void guardarDetalleLiquidacion(Empleado empleado, Integer monto, Integer conceptoSalarialId, Date fechaLiquidacion, Date periodo) {
	    // Verificar si ya existe un detalle similar
	    Optional<DetalleLiquidacion> detalleExistente = detalleLiquidacionRepository.findByEmpleado_IdAndConceptoSalarial_IdAndFechaLiquidacion(empleado.getId(), conceptoSalarialId, fechaLiquidacion);
	    
	    if (detalleExistente.isPresent()) {
	        System.out.println("Ya existe un detalle de liquidación para el empleado: " + empleado.getId() + " con concepto: " + conceptoSalarialId);
	        return; // No crear un nuevo detalle si ya existe
	    }

	    DetalleLiquidacion nuevoDetalle = new DetalleLiquidacion();
	    nuevoDetalle.setEmpleado(empleado);
	    nuevoDetalle.setMonto(monto);
	    nuevoDetalle.setConceptoSalarial(conceptoSalarialRepository.findById(conceptoSalarialId)
	            .orElseThrow(() -> new IllegalArgumentException("Concepto salarial no encontrado")));
	    nuevoDetalle.setFechaLiquidacion(fechaLiquidacion);
	    nuevoDetalle.setPeriodo(periodo);
	    detalleLiquidacionRepository.save(nuevoDetalle);
	}

	private void calcularAdicionalPermanencia(Empleado empleado, Date fechaLiquidacion, Date periodo) {
	    Date fechaIngreso = empleado.getFechaInicio();
	    LocalDate fechaIngresoDate = fechaIngreso != null ? ((java.sql.Date) fechaIngreso).toLocalDate() : null;

	    LocalDate fechaLiquidacionDate = fechaLiquidacion != null ? fechaLiquidacion.toInstant()
	            .atZone(ZoneId.systemDefault()).toLocalDate() : null;

	    if (fechaIngresoDate != null && fechaLiquidacionDate != null) {
	        Period diferencia = Period.between(fechaIngresoDate, fechaLiquidacionDate);
	        int anosAntiguedad = diferencia.getYears();

	        if (anosAntiguedad >= 1 && anosAntiguedad <= 21) {
	            Optional<AdicionalPermanencia> adicionalOpt = adicionalPermanenciaRepository.findById(anosAntiguedad);
	            if (adicionalOpt.isPresent()) {
	                AdicionalPermanencia adicional = adicionalOpt.get();
	                guardarDetalleLiquidacion(empleado, adicional.getMonto(), 5, fechaLiquidacion, periodo);
	            } else {
	                System.out.println("No se encontró adicional por permanencia para la antigüedad: " + anosAntiguedad);
	            }
	        }
	    } else {
	        System.out.println("Fechas de ingreso o liquidación no válidas para el empleado: " + empleado.getId());
	    }
	}}