package com.nomina.backend.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nomina.backend.model.ConceptoSalarial;
import com.nomina.backend.model.DetalleLiquidacion;
import com.nomina.backend.model.Empleado;
import com.nomina.backend.repository.ConceptoSalarialRepository;
import com.nomina.backend.repository.DetalleLiquidacionRepository;
import com.nomina.backend.repository.EmpleadoRepository;

@Service
public class LiquidacionTotalesHaberesService {

	@Autowired
	private DetalleLiquidacionRepository detalleLiquidacionRepository;

	@Autowired
	private EmpleadoRepository empleadoRepository;

	@Autowired
	private ConceptoSalarialRepository conceptoSalarialRepository;

	private enum ConceptoTipo {
		IMP_APORTES, IMP_GANANCIAS, IMP_INDEMNIZACION, SUELDO_TOTAL, IMP_AGUINALDO
	}

	public void sumarConceptosYRegistrar() {
		// Fecha actual e inicio del mes
		Date fechaActual = new Date(System.currentTimeMillis());
		Date fechaInicio = obtenerFechaInicioDelMes();

		// Obtener todos los detalles de liquidación
		List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findAll();

		// Mapas para acumular montos por empleado por tipo de concepto
		Map<ConceptoTipo, Map<Integer, Integer>> sumasPorConcepto = inicializarMapasDeSumas();

		// Iterar y acumular montos por tipo de concepto
		for (DetalleLiquidacion detalle : detalles.stream()
				.filter(d -> d.getConceptoSalarial().getId() >= 1 && d.getConceptoSalarial().getId() <= 89)
				.collect(Collectors.toList())) {

			Integer empleadoId = detalle.getEmpleado().getId();
			Integer monto = detalle.getMonto();

			acumularMonto(detalle, empleadoId, monto, sumasPorConcepto);
		}

		// Registrar los totales para cada tipo de concepto usando sus IDs específicos
		registrarTotales(sumasPorConcepto, fechaActual, fechaInicio);
		
		//liquidacionRetencionesService.procesarYRegistrarNuevosDetalles();
	}

	private Date obtenerFechaInicioDelMes() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return new Date(calendar.getTimeInMillis());
	}

	private Map<ConceptoTipo, Map<Integer, Integer>> inicializarMapasDeSumas() {
		Map<ConceptoTipo, Map<Integer, Integer>> map = new EnumMap<>(ConceptoTipo.class);
		for (ConceptoTipo tipo : ConceptoTipo.values()) {
			map.put(tipo, new HashMap<>());
		}
		return map;
	}

	private void acumularMonto(DetalleLiquidacion detalle, Integer empleadoId, Integer monto,
			Map<ConceptoTipo, Map<Integer, Integer>> sumasPorConcepto) {
		ConceptoSalarial concepto = detalle.getConceptoSalarial();

		if (concepto.getImpAportes()) {
			sumasPorConcepto.get(ConceptoTipo.IMP_APORTES).merge(empleadoId, monto, Integer::sum);
		}
		if (concepto.getImpGanancias()) {
			sumasPorConcepto.get(ConceptoTipo.IMP_GANANCIAS).merge(empleadoId, monto, Integer::sum);
		}
		if (concepto.getImpIndemnizacion()) {
			sumasPorConcepto.get(ConceptoTipo.IMP_INDEMNIZACION).merge(empleadoId, monto, Integer::sum);
		}
		if (concepto.getSueldoTotal()) {
			sumasPorConcepto.get(ConceptoTipo.SUELDO_TOTAL).merge(empleadoId, monto, Integer::sum);
		}
		if (concepto.getImpSac()) {
			sumasPorConcepto.get(ConceptoTipo.IMP_AGUINALDO).merge(empleadoId, monto, Integer::sum);
		}
	}

	private void registrarTotales(Map<ConceptoTipo, Map<Integer, Integer>> sumasPorConcepto,
			Date fechaActual, Date fechaInicio) {
		registrarTotalesPorConcepto(sumasPorConcepto.get(ConceptoTipo.IMP_APORTES), fechaActual, fechaInicio, 91);
		registrarTotalesPorConcepto(sumasPorConcepto.get(ConceptoTipo.IMP_GANANCIAS), fechaActual, fechaInicio, 92);
		registrarTotalesPorConcepto(sumasPorConcepto.get(ConceptoTipo.IMP_INDEMNIZACION), fechaActual, fechaInicio, 93);
		registrarTotalesPorConcepto(sumasPorConcepto.get(ConceptoTipo.SUELDO_TOTAL), fechaActual, fechaInicio, 94);
		registrarTotalesPorConcepto(sumasPorConcepto.get(ConceptoTipo.IMP_AGUINALDO), fechaActual, fechaInicio, 95);
	}

	private void registrarTotalesPorConcepto(Map<Integer, Integer> sumaPorEmpleado, Date fechaActual,
			Date fechaInicio, int conceptoId) {
		for (Map.Entry<Integer, Integer> entry : sumaPorEmpleado.entrySet()) {
			Integer empleadoId = entry.getKey();
			Integer sumaTotal = entry.getValue();

			// Verificar si ya existe un registro para el empleado, concepto y período
			boolean existeRegistro = detalleLiquidacionRepository.existsByEmpleadoIdAndConceptoSalarialIdAndPeriodo(
					empleadoId, conceptoId, fechaInicio);

			if (existeRegistro) {
				System.out.println("Registro ya existente para el empleado " + empleadoId +
						", concepto " + conceptoId + " y periodo " + fechaInicio);
				continue; // Evitar duplicados y pasar al siguiente
			}

			Empleado empleado = empleadoRepository.findById(empleadoId)
					.orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
			ConceptoSalarial concepto = conceptoSalarialRepository.findById(conceptoId)
					.orElseThrow(() -> new RuntimeException("ConceptoSalarial no encontrado"));

			// Crear y guardar el nuevo registro de DetalleLiquidacion
			DetalleLiquidacion nuevoRegistro = new DetalleLiquidacion();
			nuevoRegistro.setEmpleado(empleado);
			nuevoRegistro.setMonto(sumaTotal);
			nuevoRegistro.setConceptoSalarial(concepto);
			nuevoRegistro.setPeriodo(fechaInicio);
			nuevoRegistro.setFechaLiquidacion(fechaActual);

			detalleLiquidacionRepository.save(nuevoRegistro);
		}
	}
}
