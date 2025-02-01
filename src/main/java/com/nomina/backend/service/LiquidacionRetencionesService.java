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

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service
public class LiquidacionRetencionesService {

    private static final Logger logger = LoggerFactory.getLogger(LiquidacionRetencionesService.class);
    private static final int[] CONCEPTO_IDS = {101, 102, 103};
    private static final int CONCEPTO_ID_91 = 91;

    @Autowired
    private DetalleLiquidacionRepository detalleLiquidacionRepository;
    @Autowired
    private ConceptoSalarialRepository conceptoSalarialRepository;
    @Autowired
    private NovedadLiquidacionRepository novedadLiquidacionRepository;
    @Autowired
    private LiquidacionTotalesRetencionesService liquidacionTotalesRetencionesService;

    private final Calendar calendar = Calendar.getInstance();

    public void procesarYRegistrarNuevosDetalles() {
        Date fechaInicio = obtenerFechaInicioDelMes();
        Date fechaActual = new Date(System.currentTimeMillis());

        List<DetalleLiquidacion> detalles = obtenerDetallesConcepto(CONCEPTO_ID_91);
        ConceptoSalarial[] conceptos = obtenerConceptos(CONCEPTO_IDS);

        procesarDetalles(detalles, conceptos, fechaInicio, fechaActual);
        procesarNovedades(List.of(185, 285), fechaInicio, fechaActual);

        liquidacionTotalesRetencionesService.sumarConceptosYRegistrar();
    }

    private List<DetalleLiquidacion> obtenerDetallesConcepto(int conceptoId) {
        List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findByConceptoSalarial_Id(conceptoId);
        logger.info("Número de detalles obtenidos para concepto {}: {}", conceptoId, detalles.size());
        return detalles;
    }

    private ConceptoSalarial[] obtenerConceptos(int[] ids) {
        return Arrays.stream(ids)
            .mapToObj(id -> conceptoSalarialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ConceptoSalarial " + id + " no encontrado")))
            .toArray(ConceptoSalarial[]::new);
    }

    private void procesarDetalles(List<DetalleLiquidacion> detalles, ConceptoSalarial[] conceptos, Date fechaInicio, Date fechaActual) {
        detalles.stream()
            .filter(this::validarDetalle)
            .filter(detalle -> ((Date) detalle.getFechaLiquidacion()).toLocalDate().isEqual(fechaActual.toLocalDate()))
            .forEach(detalle -> {
                for (ConceptoSalarial concepto : conceptos) {
                    int montoFinal = calcularMonto(detalle.getMonto(), concepto.getValor());
                    guardarNuevoDetalle(detalle.getEmpleado(), montoFinal, concepto, fechaInicio, fechaActual);
                }
            });
    }

    private void procesarNovedades(List<Integer> conceptoIds, Date fechaInicio, Date fechaFin) {
        List<NovedadLiquidacion> novedades = novedadLiquidacionRepository.findByConceptoSalarialIdAndFecha(conceptoIds, fechaInicio, fechaFin);

        novedades.stream()
            .filter(novedad -> novedad.getEmpleado() != null && novedad.getCantidad() > 0)
            .forEach(novedad -> guardarNuevoDetalle(
                novedad.getEmpleado(),
                novedad.getCantidad(),
                novedad.getConcepto(),
                fechaInicio,
                new Date(System.currentTimeMillis())
            ));
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

    private int calcularMonto(int monto, int valor) {
        return monto * valor / 100;
    }

    private void guardarNuevoDetalle(Empleado empleado, int monto, ConceptoSalarial concepto, Date periodo, Date fechaLiquidacion) {
        DetalleLiquidacion detalle = new DetalleLiquidacion();
        detalle.setEmpleado(empleado);
        detalle.setMonto(monto);
        detalle.setConceptoSalarial(concepto);
        detalle.setPeriodo(periodo);
        detalle.setFechaLiquidacion(fechaLiquidacion);
        detalleLiquidacionRepository.save(detalle);
        logger.info("Detalle guardado: empleado={}, monto={}, concepto={}", empleado.getId(), monto, concepto.getId());
    }

    private Date obtenerFechaInicioDelMes() {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new Date(calendar.getTimeInMillis());
    }
}