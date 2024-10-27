package com.nomina.backend.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nomina.backend.model.ConceptoSalarial;
import com.nomina.backend.model.DetalleLiquidacion;
import com.nomina.backend.model.Empleado;
import com.nomina.backend.repository.ConceptoSalarialRepository;
import com.nomina.backend.repository.DetalleLiquidacionRepository;
import com.nomina.backend.repository.EmpleadoRepository;

@Service
public class LiquidacionTotalesRetencionesService {

	@Autowired
	private DetalleLiquidacionRepository detalleLiquidacionRepository;

	@Autowired
	private EmpleadoRepository empleadoRepository;

	@Autowired
	private ConceptoSalarialRepository conceptoSalarialRepository;

	@Autowired
	private LiquidacionImpuestoGananciasService liquidacionImpuestoGananciasService;

	private static final Logger logger = LoggerFactory.getLogger(LiquidacionTotalesRetencionesService.class);
	
	Calendar calendar = Calendar.getInstance();
    private static final int CONCEPTO_ID_189 = 189;
    private static final int CONCEPTO_ID_101 = 101;
    
	public void sumarConceptosYRegistrar() {
    	Date fechaActual = new Date(System.currentTimeMillis());
        Date fechaInicio = obtenerFechaInicioDelMes();   
		calendar.set(Calendar.DAY_OF_MONTH, 1);


		// Obtener todos los detalles de liquidación
		List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findAll();
		logger.info("Total de Detalles de Liquidación: {}", detalles.size());

		// Filtrar los detalles para los conceptos del 1 al 89
		List<DetalleLiquidacion> detallesFiltrados = detalles.stream().filter(
				detalle -> detalle.getConceptoSalarial().getId() >= CONCEPTO_ID_101 && detalle.getConceptoSalarial().getId() <= CONCEPTO_ID_189)
				.collect(Collectors.toList());
		logger.info("Detalles Filtrados (Conceptos 101 a 189): {}", detallesFiltrados.size());

		// Mapas para acumular montos por empleado
		Map<Integer, Integer> sumaImpAportesPorEmpleado = new HashMap<>();
		Map<Integer, Integer> sumaImpGananciasPorEmpleado = new HashMap<>();

		// Iterar sobre los detalles
		for (DetalleLiquidacion detalle : detallesFiltrados) {
			Integer empleadoId = detalle.getEmpleado().getId();
			Integer monto = detalle.getMonto();

			// Verificar si es ImpAportes o ImpGanancias y acumular el monto en el mapa
			// correspondiente
			if (detalle.getConceptoSalarial().getImpAportes()) {
				sumaImpAportesPorEmpleado.merge(empleadoId, monto, Integer::sum);
				logger.debug("Acumulando ImpAportes para empleado ID {}: monto actual {}", empleadoId, sumaImpAportesPorEmpleado.get(empleadoId));
			}

			if (detalle.getConceptoSalarial().getImpGanancias()) {
				sumaImpGananciasPorEmpleado.merge(empleadoId, monto, Integer::sum);
				logger.debug("Acumulando ImpGanancias para empleado ID {}: monto actual {}", empleadoId, sumaImpGananciasPorEmpleado.get(empleadoId));	
			}
		}

		// Registrar los totales por empleado
		registrarTotalesPorConcepto(sumaImpAportesPorEmpleado, fechaActual, fechaInicio, 191); // true para ImpAportes
		registrarTotalesPorConcepto(sumaImpGananciasPorEmpleado, fechaActual, fechaInicio, 192); // false para

		liquidacionImpuestoGananciasService.calcularImpuestoGananciasTodos(fechaActual);
	}
	
	private Date obtenerFechaInicioDelMes() {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new Date(calendar.getTimeInMillis());
    }

	private void registrarTotalesPorConcepto(Map<Integer, Integer> sumaPorEmpleado, Date fechaActual, Date fechaInicio,
			int conceptoId) {
		for (Map.Entry<Integer, Integer> entry : sumaPorEmpleado.entrySet()) {
			Integer empleadoId = entry.getKey();
			Integer sumaTotal = entry.getValue();

			// Busca el empleado existente en lugar de crear uno nuevo
			Empleado empleado = empleadoRepository.findById(empleadoId)
					.orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

			// Crea un concepto salarial dependiendo de si es Aporte o Ganancias
			ConceptoSalarial concepto = conceptoSalarialRepository.findById(conceptoId)
					.orElseThrow(() -> new RuntimeException("ConceptoSalarial no encontrado"));

			// Crear y guardar el nuevo registro de DetalleLiquidacion
			DetalleLiquidacion nuevoRegistro = new DetalleLiquidacion();
			nuevoRegistro.setEmpleado(empleado); // Usa el empleado existente
			nuevoRegistro.setMonto(sumaTotal);
			nuevoRegistro.setConceptoSalarial(concepto); // Usa el concepto existente
			nuevoRegistro.setPeriodo(fechaInicio);
			nuevoRegistro.setFechaLiquidacion(fechaActual);

			// Guardar el registro en la base de datos
			detalleLiquidacionRepository.save(nuevoRegistro);
			logger.info("Registro guardado para empleado ID {}: monto {}, concepto ID {}", empleadoId, sumaTotal, conceptoId);
		}
	}
}
