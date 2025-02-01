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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	@Autowired
	private LiquidacionTotalesHaberesService liquidacionTotalesHaberesService;

	public void realizarLiquidacion() {
		List<Empleado> empleados = empleadoRepository.findAll();
		LocalDate now = LocalDate.now();
		Date fechaLiquidacion = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date periodo = Date.from(now.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

		eliminarRegistrosPreviosDelPeriodo(periodo);

		// Cargar todos los Conceptos Salariales en un mapa
		Map<Integer, ConceptoSalarial> conceptoMap = cargarConceptosSalariales();

		for (Empleado empleado : empleados) {
			logger.info("Procesando liquidación para el empleado con ID: {}", empleado.getId());

			if (empleado.getConvenio() != null && empleado.getConvenio().getIdConvenio() == 1) {
				// Procesar liquidación para empleado con convenio válido
				procesarLiquidacionEmpleado(empleado, fechaLiquidacion, periodo, conceptoMap);
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
			liquidacionNovedadesService.procesarNovedades(empleado); // Llamar con el empleado individualmente
			logger.info("Procesamiento de novedades finalizado para el empleado con ID: {}", empleado.getId());
		}

		// Actualizar los totales de haberes
		liquidacionTotalesHaberesService.sumarConceptosYRegistrar();
		logger.info("Finalizada la liquidación para todos los empleados.");
	}

	private void eliminarRegistrosPreviosDelPeriodo(Date periodo) {
		if (periodo == null) {
			logger.error("El periodo proporcionado es nulo. No se puede continuar con la eliminación.");
			throw new IllegalArgumentException("El periodo no puede ser nulo.");
		}

		java.sql.Date sqlPeriodo = new java.sql.Date(periodo.getTime());

		try {
			List<DetalleLiquidacion> registrosPrevios = detalleLiquidacionRepository.findByPeriodo(sqlPeriodo);
			if (!registrosPrevios.isEmpty()) {
				logger.info("Se encontraron {} registros previos para el periodo {}. Procediendo a eliminarlos.",
						registrosPrevios.size(), sqlPeriodo);
				detalleLiquidacionRepository.deleteAll(registrosPrevios);
				logger.info("Registros previos eliminados exitosamente.");
			} else {
				logger.info("No se encontraron registros previos para el periodo {}.", sqlPeriodo);
			}
		} catch (Exception e) {
			logger.error("Error al intentar eliminar los registros previos para el periodo {}: {}", sqlPeriodo,
					e.getMessage());
			throw e;
		}
	}

	private Map<Integer, ConceptoSalarial> cargarConceptosSalariales() {
		Map<Integer, ConceptoSalarial> conceptoMap = new HashMap<>();
		List<ConceptoSalarial> conceptosSalariales = conceptoSalarialRepository.findAll();

		for (ConceptoSalarial concepto : conceptosSalariales) {
			conceptoMap.put(concepto.getId(), concepto);
		}

		return conceptoMap;
	}

	private void procesarLiquidacionEmpleado(Empleado empleado, Date fechaLiquidacion, Date periodo,
			Map<Integer, ConceptoSalarial> conceptoMap) {
		Integer idCategoria = empleado.getCategoria().getIdCategoria();
		Optional<HistoricoValoresCategoria> optionalHistorico = historicoValoresCategoriaRepository
				.findById(idCategoria);

		if (optionalHistorico.isPresent()) {
			HistoricoValoresCategoria ultimoHistorico = optionalHistorico.get();
			Integer montoCategoria = ultimoHistorico.getSalario();
			Integer montoAlmuerzo = ultimoHistorico.getAlmuerzo();

			// Obtener ConceptoSalarial usando el Map
			ConceptoSalarial salarioConcepto = conceptoMap.get(2); // 2 es el ID del concepto salario
			ConceptoSalarial almuerzoConcepto = conceptoMap.get(4); // 4 es el ID del concepto almuerzo

			// Crear y guardar el detalle de liquidación para salario
			crearDetalleLiquidacion(empleado, salarioConcepto, montoCategoria, fechaLiquidacion, periodo);
			logger.info("Se ha guardado el detalle de liquidación para salario del empleado con ID: {}",
					empleado.getId());

			// Crear y guardar el detalle de liquidación para almuerzo
			crearDetalleLiquidacion(empleado, almuerzoConcepto, montoAlmuerzo, fechaLiquidacion, periodo);
			logger.info("Se ha guardado el detalle de liquidación para almuerzo del empleado con ID: {}",
					empleado.getId());

			// Calcular adicional por permanencia
			calcularAdicionalPermanencia(empleado, fechaLiquidacion, periodo, conceptoMap);
		} else {
			logger.warn("No se encontró histórico de valores de categoría para el empleado con ID: {}",
					empleado.getId());
		}
	}

	private void procesarSalarioExcedente(Empleado empleado, Date fechaLiquidacion, Date periodo) {
		salarioExcedenteRepository.findByEmpleado_Id(empleado.getId()).ifPresent(salarioExcedente -> {
			try {
				// Crear y guardar el detalle de liquidación para el salario excedente
				crearDetalleLiquidacion(empleado, salarioExcedente.getConceptoSalarial(), salarioExcedente.getValor(),
						fechaLiquidacion, periodo);
				logger.info(
						"Se ha guardado el detalle de liquidación para el salario excedente del empleado con ID: {}",
						empleado.getId());
			} catch (Exception e) {
				logger.error("Error al crear detalle de liquidación para el salario excedente del empleado con ID: {}",
						empleado.getId(), e);
			}
		});
	}

	private void calcularAdicionalPermanencia(Empleado empleado, Date fechaLiquidacion, Date periodo,
			Map<Integer, ConceptoSalarial> conceptoMap) {
		Date fechaIngreso = empleado.getFechaInicio();
		LocalDate fechaIngresoDate = fechaIngreso != null ? ((java.sql.Date) fechaIngreso).toLocalDate() : null;

		if (fechaIngresoDate == null) {
			logger.warn("Fecha de ingreso es null para el empleado con ID: {}", empleado.getId());
			return;
		}

		LocalDate fechaLiquidacionDate = fechaLiquidacion.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Period diferencia = Period.between(fechaIngresoDate, fechaLiquidacionDate);
		int anosAntiguedad = diferencia.getYears();
		logger.info("Años de antigüedad para el empleado con ID {}: {}", empleado.getId(), anosAntiguedad);

		if (anosAntiguedad >= 1 && anosAntiguedad <= 21) {
			Optional<AdicionalPermanencia> adicionalOpt = adicionalPermanenciaRepository.findById(anosAntiguedad);
			if (adicionalOpt.isPresent()) {
				AdicionalPermanencia adicional = adicionalOpt.get();
				Integer montoAdicional = adicional.getMonto();

				// Obtener ConceptoSalarial usando el Map
				ConceptoSalarial permanenciaConcepto = conceptoMap.get(5); // 5 es el ID del concepto adicional por
																			// permanencia

				crearDetalleLiquidacion(empleado, permanenciaConcepto, montoAdicional, fechaLiquidacion, periodo);
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
			logger.error("Concepto salarial no encontrado para el empleado con ID: {}", empleado.getId());
			throw new IllegalArgumentException("Concepto salarial no encontrado");
		}
	}
}