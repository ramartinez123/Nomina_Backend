package com.nomina.backend.service;

import com.nomina.backend.model.ConceptoSalarial;
import com.nomina.backend.model.DetalleLiquidacion;
import com.nomina.backend.model.Empleado;
import com.nomina.backend.model.NovedadLiquidacion;
import com.nomina.backend.repository.ConceptoSalarialRepository;
import com.nomina.backend.repository.DetalleLiquidacionRepository;
import com.nomina.backend.repository.NovedadLiquidacionRepository;
import java.sql.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.List;

@Service
public class LiquidacionRetencionesService {

	private static final Logger logger = LoggerFactory.getLogger(LiquidacionRetencionesService.class);

	@Autowired
	private DetalleLiquidacionRepository detalleLiquidacionRepository;

	@Autowired
	private ConceptoSalarialRepository conceptoSalarialRepository;

	@Autowired
	private LiquidacionTotalesRetencionesService liquidacionTotalesRetencionesService;

	@Autowired
	private NovedadLiquidacionRepository novedadLiquidacionRepository;

	Calendar calendar = Calendar.getInstance();
	private static final int CONCEPTO_ID_91 = 91;
	private static final int CONCEPTO_ID_101 = 101;
	private static final int CONCEPTO_ID_102 = 102;
	private static final int CONCEPTO_ID_103 = 103;

	public void procesarYRegistrarNuevosDetalles() {

		Date fechaActual = new Date(System.currentTimeMillis());
		Date fechaInicio = obtenerFechaInicioDelMes();   
		Date fechaFin = new Date(calendar.getTimeInMillis());
		logger.info("Fecha de inicio del mes: {}", fechaInicio);

		// Obtener los registros del concepto 91
		List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findByConceptoSalarial_Id(CONCEPTO_ID_91);
		logger.info("Número de detalles obtenidos para concepto 91: {}", detalles.size());

		// Obtener conceptos
		ConceptoSalarial[] conceptos = {
				conceptoSalarialRepository.findById(CONCEPTO_ID_101).orElseThrow(() -> new RuntimeException("ConceptoSalarial 101 no encontrado")),
				conceptoSalarialRepository.findById(CONCEPTO_ID_102).orElseThrow(() -> new RuntimeException("ConceptoSalarial 102 no encontrado")),
				conceptoSalarialRepository.findById(CONCEPTO_ID_103).orElseThrow(() -> new RuntimeException("ConceptoSalarial 103 no encontrado"))
		};

		for (DetalleLiquidacion detalle : detalles) {

			if (!validarDetalle(detalle)) continue;

			// Filtrar por fecha de liquidación y empleado
			if (((Date) detalle.getFechaLiquidacion()).toLocalDate().isEqual(fechaActual.toLocalDate()) && detalle.getEmpleado() != null) {
				for (ConceptoSalarial concepto : conceptos) {
					int montoFinal = calcularMonto(detalle.getMonto(), concepto.getValor());
					logger.info("Guardando nuevo detalle para empleado: {}, monto final: {}, concepto: {}", detalle.getEmpleado().getId(), montoFinal, concepto.getId());
					guardarNuevoDetalle(detalle.getEmpleado(), montoFinal, concepto, fechaInicio, fechaActual);
				}
			}
		}

		List<Integer> conceptoIds = List.of(185,285);        
		List<NovedadLiquidacion> novedades = novedadLiquidacionRepository.findByConceptoSalarialIdAndFecha(conceptoIds, fechaInicio, fechaFin);

		for (NovedadLiquidacion novedad : novedades) {
			Integer cantidadNovedades = novedad.getCantidad();

			if (novedad.getEmpleado() == null) {
				logger.warn("Novedad sin empleado, se omite: {}", novedad);
				continue;
			}
			if (cantidadNovedades <= 0) {
				logger.warn("Cantidad de novedades no válida, se omite: {}", novedad);
				continue;
			}

			// Verificar el concepto para aplicar la fórmula adecuada
			if (novedad.getConcepto().getId() == 185) {
				int resultado =  cantidadNovedades;

				if (novedad.getEmpleado() != null) {
					logger.info("Guardando nuevo detalle por novedad: empleado: {}, resultado: {}, concepto: {}", novedad.getEmpleado().getId(), resultado, novedad.getConcepto().getId());
					guardarNuevoDetalle(novedad.getEmpleado(), resultado, novedad.getConcepto(), fechaInicio, fechaActual);
				}               
			}else if (novedad.getConcepto().getId() == 285) {
				int resultado =  cantidadNovedades; 

				if (novedad.getEmpleado() != null) {
					logger.info("Guardando nuevo detalle por novedad: empleado: {}, resultado: {}, concepto: {}", novedad.getEmpleado().getId(), resultado, novedad.getConcepto().getId());
					guardarNuevoDetalle(novedad.getEmpleado(), resultado, novedad.getConcepto(), fechaInicio, fechaActual);
				}   
			}
		}
		liquidacionTotalesRetencionesService.sumarConceptosYRegistrar();
	}

	private Date obtenerFechaInicioDelMes() {
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return new Date(calendar.getTimeInMillis());
	}

	private int calcularMonto(int monto, int valor) {
		return monto * valor / 100;
	}

	private boolean validarDetalle(DetalleLiquidacion detalle) {
		if (detalle.getEmpleado() == null) {
			logger.warn("Detalle sin empleado, se omite: {}", detalle);
			return false;
		}
		if (detalle.getMonto() <= 0) {
			logger.warn("Monto no válido para detalle, se omite: {}", detalle);
			return false;
		}
		return true;
	}

	private void guardarNuevoDetalle(Empleado empleado, int montoFinal, ConceptoSalarial concepto, Date periodo, Date fechaLiquidacion) {
		DetalleLiquidacion nuevoDetalle = new DetalleLiquidacion();
		nuevoDetalle.setEmpleado(empleado);
		nuevoDetalle.setMonto(montoFinal);
		nuevoDetalle.setConceptoSalarial(concepto);
		nuevoDetalle.setPeriodo(periodo);
		nuevoDetalle.setFechaLiquidacion(fechaLiquidacion);
		detalleLiquidacionRepository.save(nuevoDetalle);
	}
}