package com.nomina.backend.service;

import com.nomina.backend.model.*;
import com.nomina.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.nomina.backend.model.NovedadLiquidacion;

@Service
public class LiquidacionServiceSueldo {

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
	private LiquidacionServiceNovedades liquidacionServiceNovedades;

	public void realizarLiquidacion() {

		List<Empleado> empleados = empleadoRepository.findAll();

		// Obtener la fecha de liquidación una sola vez
		LocalDate now = LocalDate.now();
		Date fechaLiquidacion = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());

		// Establecer el periodo (por ejemplo, el primer día del mes actual)
		LocalDate periodoDate = now.withDayOfMonth(1);
		Date periodo = Date.from(periodoDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		for (Empleado empleado : empleados) {
			if (empleado.getConvenio().getIdConvenio() == 1) {
				// Obtener valores de historicoValoresCategoria
				Integer idCategoria = empleado.getCategoria().getIdCategoria();
				Optional<HistoricoValoresCategoria> optionalHistorico = historicoValoresCategoriaRepository.findById(idCategoria);

				// Supongamos que tomas el último registro
				if (optionalHistorico.isPresent()) {
					HistoricoValoresCategoria ultimoHistorico = optionalHistorico.get();
					Integer montoCategoria = ultimoHistorico.getSalario();
					Integer montoAlmuerzo = ultimoHistorico.getAlmuerzo(); 

					// Crear detalle de liquidación para salario
					DetalleLiquidacion detalleSalario = new DetalleLiquidacion();
					detalleSalario.setEmpleado(empleado);
					Optional<ConceptoSalarial> optionalConceptoSalarial = conceptoSalarialRepository.findById(2); // Cambia '2' por el ID que necesites

					
					// Establecer el concepto salarial para convenio
					if (optionalConceptoSalarial.isPresent()) {
						detalleSalario.setConceptoSalarial(optionalConceptoSalarial.get());
					} else {
						System.out.println("Concepto salarial no encontrado para salario");
					}

					detalleSalario.setMonto(montoCategoria);
					detalleSalario.setFechaLiquidacion(fechaLiquidacion);
					detalleSalario.setPeriodo(periodo);
					detalleLiquidacionRepository.save(detalleSalario);

					// Crear detalle de liquidación para almuerzo
					DetalleLiquidacion detalleAlmuerzo = new DetalleLiquidacion();
					detalleAlmuerzo.setEmpleado(empleado);
					Optional<ConceptoSalarial> optionalConceptoSalarial2 = conceptoSalarialRepository.findById(4); // Cambia '4' por el ID que necesites

					
					// Establecer el concepto salarial para almuerzo
					if (optionalConceptoSalarial2.isPresent()) {
						detalleAlmuerzo.setConceptoSalarial(optionalConceptoSalarial2.get());
					} else {
						System.out.println("Concepto salarial no encontrado para almuerzo");
					}

					detalleAlmuerzo.setMonto(montoAlmuerzo);
					detalleAlmuerzo.setFechaLiquidacion(fechaLiquidacion);
					detalleAlmuerzo.setPeriodo(periodo);
					detalleLiquidacionRepository.save(detalleAlmuerzo);
					
					// Establecer el adicional por permanencia
					// Calcular antigüedad para adicional de permanencia
					
					Date fechaIngreso = empleado.getFechaInicio();			
					LocalDate fechaIngresoDate = null;
					if (fechaIngreso != null) {
					        // Convertir java.sql.Date a LocalDate directamente
					        fechaIngresoDate = ((java.sql.Date) fechaIngreso).toLocalDate();
					} else {
					        System.out.println("fechaIngreso es null");
					    }

					// Imprimir la fechaLiquidacion antes de convertir
					LocalDate fechaLiquidacionDate = null;
					if (fechaLiquidacion != null) {
					    fechaLiquidacionDate = fechaLiquidacion.toInstant()
					            .atZone(ZoneId.systemDefault())
					            .toLocalDate();
					} else {
					    System.out.println("fechaLiquidacion es null");
					}
					
					
					if (fechaIngresoDate != null && fechaLiquidacionDate != null) {
						Period diferencia = Period.between(fechaIngresoDate,fechaLiquidacionDate);
						int anosAntiguedad = diferencia.getYears();
						System.out.println(anosAntiguedad);
						// Validar que los años de antigüedad estén dentro del rango de 1 a 21
						if (anosAntiguedad >= 1 && anosAntiguedad <= 21) {
							Optional<AdicionalPermanencia> adicionalOpt = adicionalPermanenciaRepository.findById(anosAntiguedad);
							if (adicionalOpt.isPresent()) {
								AdicionalPermanencia adicional = adicionalOpt.get();
								Integer montoAdicional = adicional.getMonto();
								
								ConceptoSalarial conceptoSalarial = conceptoSalarialRepository.findById(5)
									    .orElseThrow(() -> new IllegalArgumentException("Concepto Salarial no encontrado con ID 5"));							

								// Crear detalle de liquidación para el adicional por permanencia
								DetalleLiquidacion detallePermanencia = new DetalleLiquidacion();
								detallePermanencia.setEmpleado(empleado);
								detallePermanencia.setPeriodo(periodo);
								detallePermanencia.setConceptoSalarial(conceptoSalarial);
								detallePermanencia.setMonto(montoAdicional);
								detallePermanencia.setFechaLiquidacion(fechaLiquidacion);
								detalleLiquidacionRepository.save(detallePermanencia);
							} else {
								System.out.println("No se encontró adicional por permanencia para la antigüedad: " + anosAntiguedad);
							}
						}
					} else {
						System.out.println("Fechas de ingreso o liquidación no válidas para el empleado: " + empleado.getId());
					}
				}
			}
			
            // Revisar salario excedente
            Optional<SalarioExcedente> salariosExcedentes = salarioExcedenteRepository.findByEmpleado_Id(empleado.getId());
            if (salariosExcedentes.isPresent()) {
                SalarioExcedente salarioExcedente = salariosExcedentes.get();
                DetalleLiquidacion detalleExcedente = new DetalleLiquidacion();
                detalleExcedente.setEmpleado(empleado);
                detalleExcedente.setConceptoSalarial(salarioExcedente.getConceptoSalarial());
                detalleExcedente.setMonto(salarioExcedente.getValor());
                detalleExcedente.setFechaLiquidacion(fechaLiquidacion);
                detalleExcedente.setPeriodo(periodo);
                detalleLiquidacionRepository.save(detalleExcedente);
            }
		}
		liquidacionServiceNovedades.obtenerNovedadesPorConceptosYFecha();
	}
	
}