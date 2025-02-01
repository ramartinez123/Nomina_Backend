package com.nomina.backend.service;

import com.nomina.backend.model.DetalleLiquidacion;
import com.nomina.backend.model.Empleado;
import com.nomina.backend.model.NovedadLiquidacion;
import com.nomina.backend.repository.DetalleLiquidacionRepository;
import com.nomina.backend.repository.NovedadLiquidacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class LiquidacionNovedadesService {

    private static final Logger logger = LoggerFactory.getLogger(LiquidacionNovedadesService.class);

    @Autowired
    private NovedadLiquidacionRepository novedadLiquidacionRepository;

    @Autowired
    private DetalleLiquidacionRepository detalleLiquidacionRepository;

    public void procesarNovedades(Empleado empleado) {
        Date fechaActual = new Date(System.currentTimeMillis());
        Date fechaInicio = obtenerFechaInicio();
        Date fechaFin = obtenerFechaFin();

        // Lista de conceptos de interés
        List<Integer> conceptoIds = List.of(11, 31, 85);

        // Obtener las novedades para procesar solo para el empleado específico
        List<NovedadLiquidacion> novedades = obtenerNovedades(conceptoIds, fechaInicio, fechaFin, empleado);

        if (novedades.isEmpty()) {
            logger.warn("No se encontraron novedades para procesar para el empleado con ID: {}", empleado.getId());
            return;  // Si no hay novedades, se termina el procesamiento
        }

        // Procesar novedades
        Map<Integer, Integer> sumaPorEmpleado = procesarDetallesPorEmpleado(novedades);

        // Imprimir la suma por empleado
        imprimirSumaPorEmpleado(sumaPorEmpleado);

        // Procesar y guardar las novedades de liquidación
        procesarYGuardarLiquidaciones(novedades, sumaPorEmpleado, fechaActual, fechaInicio);


    }

    private Date obtenerFechaInicio() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    private Date obtenerFechaFin() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    private List<NovedadLiquidacion> obtenerNovedades(List<Integer> conceptoIds, Date fechaInicio, Date fechaFin, Empleado empleado) {
        try {
            return novedadLiquidacionRepository.findByEmpleadoAndConceptoSalarialIdAndFecha(empleado, conceptoIds, fechaInicio, fechaFin);
        } catch (Exception e) {
            logger.error("Error al obtener las novedades para el empleado con ID: {}: {}", empleado.getId(), e.getMessage());
            return Collections.emptyList();
        }
    }

    private Map<Integer, Integer> procesarDetallesPorEmpleado(List<NovedadLiquidacion> novedades) {
        Map<Integer, Integer> sumaPorEmpleado = new HashMap<>();

        for (NovedadLiquidacion novedad : novedades) {
            try {
                // Usamos un conjunto para evitar duplicados por empleado y concepto
                Set<String> detallesProcesados = new HashSet<>();

                List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findByEmpleado_Id(novedad.getEmpleado().getId());
                for (DetalleLiquidacion detalle : detalles) {
                    // Crear una clave única que combine el empleado y el concepto
                    String empleadoConceptoKey = detalle.getEmpleado().getId() + "-" + detalle.getConceptoSalarial().getId();

                    // Verificar si ya procesamos este detalle (empleado + concepto)
                    if (!detallesProcesados.contains(empleadoConceptoKey)) {
                        if (List.of(1, 2, 3, 4).contains(detalle.getConceptoSalarial().getId())) {
                            sumaPorEmpleado.merge(novedad.getEmpleado().getId(), detalle.getMonto(), Integer::sum);
                        }

                        // Marcar este detalle como procesado
                        detallesProcesados.add(empleadoConceptoKey);
                    }
                }
            } catch (Exception e) {
                logger.error("Error al procesar detalles de liquidación para el empleado ID: {}: {}", novedad.getEmpleado().getId(), e.getMessage());
            }
        }
        return sumaPorEmpleado;
    }

    private void imprimirSumaPorEmpleado(Map<Integer, Integer> sumaPorEmpleado) {
        logger.info("Suma por empleado:");
        sumaPorEmpleado.forEach((empleadoId, suma) -> logger.info("Empleado ID: {}, Suma: {}", empleadoId, suma));
    }

    private void procesarYGuardarLiquidaciones(List<NovedadLiquidacion> novedades, Map<Integer, Integer> sumaPorEmpleado, Date fechaActual, Date fechaInicio) {
        for (NovedadLiquidacion novedad : novedades) {
            Integer empleadoId = novedad.getEmpleado().getId();
            Integer sumaTotal = sumaPorEmpleado.get(empleadoId);
            Integer cantidadNovedades = novedad.getCantidad();

            if (sumaTotal == null) {
                logger.warn("No hay suma total para el empleado con ID: {}", empleadoId);
                continue; // Si no hay suma, se omite este empleado
            }

            int resultado = calcularResultado(novedad, sumaTotal, cantidadNovedades);
            guardarDetalleLiquidacion(novedad, resultado, fechaActual, fechaInicio);
        }
    }

    private int calcularResultado(NovedadLiquidacion novedad, Integer sumaTotal, Integer cantidadNovedades) {
        switch (novedad.getConcepto().getId()) {
            case 11:
                return (sumaTotal / 30) * cantidadNovedades * -1;
            case 31:
                return ((sumaTotal / 25) * cantidadNovedades) - ((sumaTotal / 30) * cantidadNovedades);
            case 85:
                return cantidadNovedades;  // Revisión necesaria según el tipo de novedad
            default:
                return 0;  // Si el concepto no es relevante
        }
    }

    private void guardarDetalleLiquidacion(NovedadLiquidacion novedad, int resultado, Date fechaActual, Date fechaInicio) {
        try {
            Optional<DetalleLiquidacion> detalleExistente = detalleLiquidacionRepository
                    .findByEmpleado_IdAndConceptoSalarial_Id(novedad.getEmpleado().getId(), novedad.getConcepto().getId());

            if (detalleExistente.isPresent()) {
                logger.warn("Ya existe un detalle de liquidación para el empleado ID: {} y concepto ID: {}", novedad.getEmpleado().getId(), novedad.getConcepto().getId());
            } else {
                DetalleLiquidacion nuevoDetalle = new DetalleLiquidacion();
                nuevoDetalle.setEmpleado(novedad.getEmpleado());
                nuevoDetalle.setConceptoSalarial(novedad.getConcepto());
                nuevoDetalle.setMonto(resultado);
                nuevoDetalle.setFechaLiquidacion(fechaActual);
                nuevoDetalle.setPeriodo(fechaInicio);
                detalleLiquidacionRepository.save(nuevoDetalle);
                logger.info("Detalle liquidación guardado para empleado ID: {}, Monto: {}", novedad.getEmpleado().getId(), resultado);
            }
        } catch (Exception e) {
            logger.error("Error al guardar el detalle de liquidación para empleado ID: {}: {}", novedad.getEmpleado().getId(), e.getMessage());
        }
    }
}