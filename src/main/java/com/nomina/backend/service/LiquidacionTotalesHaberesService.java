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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LiquidacionTotalesHaberesService {

    private static final Logger logger = LoggerFactory.getLogger(LiquidacionTotalesHaberesService.class);

    @Autowired
    private DetalleLiquidacionRepository detalleLiquidacionRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ConceptoSalarialRepository conceptoSalarialRepository;

    @Autowired
    private LiquidacionRetencionesService liquidacionRetencionesService;

    private enum ConceptoTipo {
        IMP_APORTES, IMP_GANANCIAS, IMP_INDEMNIZACION, SUELDO_TOTAL, IMP_AGUINALDO
    }

    public void sumarConceptosYRegistrar() {
        // Fecha actual e inicio del mes
        Date fechaActual = new Date(System.currentTimeMillis());
        Date fechaInicio = obtenerFechaInicioDelMes();

        // Obtener todos los detalles de liquidación
        List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findByPeriodo(fechaInicio);

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
        
        // Procesar retenciones (si es necesario)
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

        // Acumulación según tipo de concepto
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
        // Registro de totales por cada tipo de concepto
        for (ConceptoTipo tipo : ConceptoTipo.values()) {
            Map<Integer, Integer> sumaPorEmpleado = sumasPorConcepto.get(tipo);
            if (sumaPorEmpleado != null && !sumaPorEmpleado.isEmpty()) {
                registrarTotalesPorConcepto(sumaPorEmpleado, fechaActual, fechaInicio, obtenerConceptoIdPorTipo(tipo));
            }
        }
    }

    private void registrarTotalesPorConcepto(Map<Integer, Integer> sumaPorEmpleado, Date fechaActual,
                                              Date fechaInicio, int conceptoId) {
        for (Map.Entry<Integer, Integer> entry : sumaPorEmpleado.entrySet()) {
            Integer empleadoId = entry.getKey();
            Integer sumaTotal = entry.getValue();

            // Verificar si ya existe un registro para el empleado, concepto y período
            if (detalleLiquidacionRepository.existsByEmpleadoIdAndConceptoSalarialIdAndPeriodo(
                    empleadoId, conceptoId, fechaInicio)) {
                logger.warn("Registro ya existente para el empleado " + empleadoId +
                        ", concepto " + conceptoId + " y periodo " + fechaInicio);
                continue; // Evitar duplicados y pasar al siguiente
            }

            // Crear y guardar el nuevo registro de DetalleLiquidacion
            guardarDetalleLiquidacion(empleadoId, conceptoId, sumaTotal, fechaActual, fechaInicio);
        }
    }

    private void guardarDetalleLiquidacion(Integer empleadoId, int conceptoId, Integer monto,
                                           Date fechaActual, Date fechaInicio) {
        try {
            Empleado empleado = empleadoRepository.findById(empleadoId)
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

            ConceptoSalarial concepto = conceptoSalarialRepository.findById(conceptoId)
                    .orElseThrow(() -> new RuntimeException("ConceptoSalarial no encontrado"));

            DetalleLiquidacion nuevoRegistro = new DetalleLiquidacion();
            nuevoRegistro.setEmpleado(empleado);
            nuevoRegistro.setMonto(monto);
            nuevoRegistro.setConceptoSalarial(concepto);
            nuevoRegistro.setPeriodo(fechaInicio);
            nuevoRegistro.setFechaLiquidacion(fechaActual);

            detalleLiquidacionRepository.save(nuevoRegistro);
            logger.info("Detalle de liquidación registrado para el empleado ID: {} y concepto ID: {}", empleadoId, conceptoId);

        } catch (RuntimeException e) {
            logger.error("Error al guardar detalle de liquidación para el empleado ID: {} y concepto ID: {}",
                    empleadoId, conceptoId, e);
        }
    }

    private int obtenerConceptoIdPorTipo(ConceptoTipo tipo) {
        switch (tipo) {
            case IMP_APORTES: return 91;
            case IMP_GANANCIAS: return 92;
            case IMP_INDEMNIZACION: return 93;
            case SUELDO_TOTAL: return 94;
            case IMP_AGUINALDO: return 95;
            default: throw new IllegalArgumentException("Tipo de concepto no válido");
        }
    }
}