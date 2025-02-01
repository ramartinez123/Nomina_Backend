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

    private static final int CONCEPTO_ID_101 = 101;
    private static final int CONCEPTO_ID_189 = 189;
    private static final int CONCEPTO_TOTAL_APORTES = 191;
    private static final int CONCEPTO_TOTAL_GANANCIAS = 192;

    private final Calendar calendar = Calendar.getInstance();

    public void sumarConceptosYRegistrar() {
        Date fechaInicio = obtenerFechaInicioDelMes();
        Date fechaActual = new Date(System.currentTimeMillis());

        List<DetalleLiquidacion> detalles = obtenerDetallesFiltrados(fechaInicio);

        Map<Integer, Integer> sumaImpAportesPorEmpleado = new HashMap<>();
        Map<Integer, Integer> sumaImpGananciasPorEmpleado = new HashMap<>();

        acumularMontosPorEmpleado(detalles, sumaImpAportesPorEmpleado, sumaImpGananciasPorEmpleado);

        registrarTotalesPorConcepto(sumaImpAportesPorEmpleado, fechaActual, fechaInicio, CONCEPTO_TOTAL_APORTES);
        registrarTotalesPorConcepto(sumaImpGananciasPorEmpleado, fechaActual, fechaInicio, CONCEPTO_TOTAL_GANANCIAS);

        liquidacionImpuestoGananciasService.calcularImpuestoGananciasTodos(fechaInicio);
    }

    private Date obtenerFechaInicioDelMes() {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new Date(calendar.getTimeInMillis());
    }

    private List<DetalleLiquidacion> obtenerDetallesFiltrados(Date fechaInicio) {
        List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findByPeriodo(fechaInicio);
        logger.info("Total de Detalles de Liquidación: {}", detalles.size());

        return detalles.stream()
            .filter(detalle -> detalle.getConceptoSalarial().getId() >= CONCEPTO_ID_101 && detalle.getConceptoSalarial().getId() <= CONCEPTO_ID_189)
            .collect(Collectors.toList());
    }

    private void acumularMontosPorEmpleado(
        List<DetalleLiquidacion> detalles,
        Map<Integer, Integer> sumaImpAportesPorEmpleado,
        Map<Integer, Integer> sumaImpGananciasPorEmpleado
    ) {
        for (DetalleLiquidacion detalle : detalles) {
            Integer empleadoId = detalle.getEmpleado().getId();
            Integer monto = detalle.getMonto();

            if (detalle.getConceptoSalarial().getImpAportes()) {
                sumaImpAportesPorEmpleado.merge(empleadoId, monto, Integer::sum);
                logger.debug("Acumulando ImpAportes para empleado ID {}: monto actual {}", empleadoId, sumaImpAportesPorEmpleado.get(empleadoId));
            }

            if (detalle.getConceptoSalarial().getImpGanancias()) {
                sumaImpGananciasPorEmpleado.merge(empleadoId, monto, Integer::sum);
                logger.debug("Acumulando ImpGanancias para empleado ID {}: monto actual {}", empleadoId, sumaImpGananciasPorEmpleado.get(empleadoId));
            }
        }
    }

    private void registrarTotalesPorConcepto(Map<Integer, Integer> sumaPorEmpleado, Date fechaActual, Date fechaInicio, int conceptoId) {
        sumaPorEmpleado.forEach((empleadoId, sumaTotal) -> {
            Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

            ConceptoSalarial concepto = conceptoSalarialRepository.findById(conceptoId)
                .orElseThrow(() -> new RuntimeException("ConceptoSalarial no encontrado"));

            if (detalleLiquidacionRepository.existsByEmpleadoIdAndConceptoSalarialIdAndPeriodo(empleadoId, conceptoId, fechaInicio)) {
                logger.warn("Ya existe un registro para empleado ID {}: concepto ID {}, periodo {}", empleadoId, conceptoId, fechaInicio);
            } else {
                DetalleLiquidacion nuevoRegistro = new DetalleLiquidacion();
                nuevoRegistro.setEmpleado(empleado);
                nuevoRegistro.setMonto(sumaTotal);
                nuevoRegistro.setConceptoSalarial(concepto);
                nuevoRegistro.setPeriodo(fechaInicio);
                nuevoRegistro.setFechaLiquidacion(fechaActual);

                detalleLiquidacionRepository.save(nuevoRegistro);
                logger.info("Registro guardado para empleado ID {}: monto {}, concepto ID {}", empleadoId, sumaTotal, conceptoId);
            }
        });
    }
}