package com.nomina.backend.service;

import com.nomina.backend.model.*;
import com.nomina.backend.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class LiquidacionSueldoService {

	private static final Logger logger = LoggerFactory.getLogger(LiquidacionSueldoService.class);

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
	private LiquidacionNovedadesService liquidacionNovedadesService;

	public void realizarLiquidacion() {
		List<Empleado> empleados = empleadoRepository.findAll();
		LocalDate now = LocalDate.now();
		Date fechaLiquidacion = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date periodo = Date.from(now.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

		for (Empleado empleado : empleados) {
	        logger.info("Procesando liquidación para el empleado con ID: {}", empleado.getId());

	        if (empleado.getConvenio() != null && empleado.getConvenio().getIdConvenio() == 1) {
	            // Procesar liquidación para empleado con convenio válido
	            procesarLiquidacionEmpleado(empleado, fechaLiquidacion, periodo);
	        } else {
	            logger.warn("Convenio es null o no es válido para el empleado con ID: {}", empleado.getId());
	        }

			// Revisar salario excedente
			salarioExcedenteRepository.findByEmpleado_Id(empleado.getId()).ifPresent(salarioExcedente -> {
				try {
					// Crear y guardar el detalle de liquidación para el salario excedente
					crearDetalleLiquidacion(empleado, salarioExcedente.getConceptoSalarial(),
							salarioExcedente.getValor(), fechaLiquidacion, periodo);
					logger.info(
							"Se ha guardado el detalle de liquidación para el salario excedente del empleado con ID: {}",
							empleado.getId());
				} catch (Exception e) {
					logger.error(
							"Error al crear detalle de liquidación para el salario excedente del empleado con ID: {}",
							empleado.getId(), e);
				}
			});

			// Procesar novedades para el empleado actual
			liquidacionNovedadesService.procesarNovedades();
			logger.info("Procesamiento de novedades finalizado para el empleado con ID: {}", empleado.getId());
		}

		logger.info("Finalizada la liquidación para todos los empleados.");
	}

	private void procesarLiquidacionEmpleado(Empleado empleado, Date fechaLiquidacion, Date periodo) {


		// Obtener valores de historicoValoresCategoria
		Integer idCategoria = empleado.getCategoria().getIdCategoria();
		Optional<HistoricoValoresCategoria> optionalHistorico = historicoValoresCategoriaRepository
				.findById(idCategoria);

		if (optionalHistorico.isPresent()) {
			HistoricoValoresCategoria ultimoHistorico = optionalHistorico.get();
			Integer montoCategoria = ultimoHistorico.getSalario();
			Integer montoAlmuerzo = ultimoHistorico.getAlmuerzo();

			// Crear detalle de liquidación para salario
			DetalleLiquidacion detalleSalario = crearDetalleLiquidacion(empleado,
			conceptoSalarialRepository.findById(2).orElse(null), montoCategoria, fechaLiquidacion, periodo);
			detalleLiquidacionRepository.save(detalleSalario);
			logger.info("Se ha guardado el detalle de liquidación para salario del empleado con ID: {}",
					empleado.getId());

			// Crear detalle de liquidación para almuerzo
			DetalleLiquidacion detalleAlmuerzo = crearDetalleLiquidacion(empleado,
			conceptoSalarialRepository.findById(4).orElse(null), montoAlmuerzo, fechaLiquidacion, periodo);
			detalleLiquidacionRepository.save(detalleAlmuerzo);
			logger.info("Se ha guardado el detalle de liquidación para almuerzo del empleado con ID: {}",
					empleado.getId());

			// Establecer el adicional por permanencia
			calcularAdicionalPermanencia(empleado, fechaLiquidacion, periodo);
		} else {
			logger.warn("No se encontró histórico de valores de categoría para el empleado con ID: {}",
					empleado.getId());
		}
	}	
	
    // Adicional por antiguedad
	private DetalleLiquidacion crearDetalleLiquidacion(Empleado empleado, int conceptoId, Integer monto,
			Date fechaLiquidacion, Date periodo) {
		ConceptoSalarial conceptoSalarial = conceptoSalarialRepository.findById(conceptoId).orElse(null);
		return crearDetalleLiquidacion(empleado, conceptoSalarial, monto, fechaLiquidacion, periodo);
	}
	
	// Detalle liquidacion Adicional Salarial
	private DetalleLiquidacion crearDetalleLiquidacion(Empleado empleado, ConceptoSalarial conceptoSalarial,
			Integer monto, Date fechaLiquidacion, Date periodo) {
		if (conceptoSalarial != null) {
			DetalleLiquidacion detalle = new DetalleLiquidacion();
			detalle.setEmpleado(empleado);
			detalle.setConceptoSalarial(conceptoSalarial);
			detalle.setMonto(monto);
			detalle.setFechaLiquidacion(fechaLiquidacion);
			detalle.setPeriodo(periodo);
			detalleLiquidacionRepository.save(detalle);
			return detalle;
		} else {
			logger.error("Concepto salarial no encontrado");
			throw new IllegalArgumentException("Concepto salarial no encontrado");
		}
	}
	
	//Adicional por permanencia
	private void calcularAdicionalPermanencia(Empleado empleado, Date fechaLiquidacion, Date periodo) {
		Date fechaIngreso = empleado.getFechaInicio();
		LocalDate fechaIngresoDate = null;

		if (fechaIngreso != null) {
			fechaIngresoDate = ((java.sql.Date) fechaIngreso).toLocalDate();
		} else {
			logger.warn("fechaIngreso es null para el empleado con ID: {}", empleado.getId());
			return;
		}

		LocalDate fechaLiquidacionDate = fechaLiquidacion.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		Period diferencia = Period.between(fechaIngresoDate, fechaLiquidacionDate);
		int anosAntiguedad = diferencia.getYears();
		logger.info("Años de antigüedad para el empleado con ID {}: {}", empleado.getId(), anosAntiguedad);

		// Validar que los años de antigüedad estén dentro del rango de 1 a 21
		if (anosAntiguedad >= 1 && anosAntiguedad <= 21) {
			Optional<AdicionalPermanencia> adicionalOpt = adicionalPermanenciaRepository.findById(anosAntiguedad);
			if (adicionalOpt.isPresent()) {
				AdicionalPermanencia adicional = adicionalOpt.get();
				Integer montoAdicional = adicional.getMonto();

				DetalleLiquidacion detallePermanencia = crearDetalleLiquidacion(empleado, 5, montoAdicional,
						fechaLiquidacion, periodo);
				detalleLiquidacionRepository.save(detallePermanencia);
				logger.info(
						"Se ha guardado el detalle de liquidación por adicional de permanencia para el empleado con ID: {}",
						empleado.getId());
			} else {
				logger.warn("No se encontró adicional por permanencia para la antigüedad: {} del empleado con ID: {}",
						anosAntiguedad, empleado.getId());
			}
		} else {
			logger.warn("Los años de antigüedad no están en el rango válido para el empleado con ID: {}",
					empleado.getId());
		}
	}
}