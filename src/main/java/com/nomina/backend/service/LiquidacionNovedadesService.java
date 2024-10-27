package com.nomina.backend.service;

import com.nomina.backend.model.DetalleLiquidacion;
import com.nomina.backend.model.NovedadLiquidacion;
import com.nomina.backend.repository.DetalleLiquidacionRepository;
import com.nomina.backend.repository.NovedadLiquidacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LiquidacionNovedadesService {

    private static final Logger logger = LoggerFactory.getLogger(LiquidacionNovedadesService.class);

    @Autowired
    private NovedadLiquidacionRepository novedadLiquidacionRepository;

    @Autowired
    private DetalleLiquidacionRepository detalleLiquidacionRepository;

    @Autowired
    private LiquidacionTotalesHaberesService liquidacionTotalesHaberesService;

    public void procesarNovedades() {
        List<Integer> conceptoIds = List.of(11, 31, 85);
        Date fechaActual = new Date(System.currentTimeMillis());
        Date fechaInicio = obtenerFechaInicio();
        Date fechaFin = obtenerFechaFin();

        // Obtener novedades para procesar
        List<NovedadLiquidacion> novedades = novedadLiquidacionRepository.findByConceptoSalarialIdAndFecha(conceptoIds, fechaInicio, fechaFin);
        logger.info("Número de novedades encontradas: {}", novedades.size());

        // Mapa para almacenar suma por empleado
        Map<Integer, Integer> sumaPorEmpleado = new HashMap<>();

        // Procesar novedades
        for (NovedadLiquidacion novedad : novedades) {
            procesarDetallesPorEmpleado(novedad, sumaPorEmpleado);
        }

        // Imprimir la suma por empleado
        imprimirSumaPorEmpleado(sumaPorEmpleado);

        // Procesar liquidaciones
        procesarNovedades(novedades, sumaPorEmpleado, fechaActual, fechaInicio);

        liquidacionTotalesHaberesService.sumarConceptosYRegistrar();
       
    }

    private Date obtenerFechaInicio() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new Date(calendar.getTimeInMillis());
    }

    private Date obtenerFechaFin() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new Date(calendar.getTimeInMillis());
    }

    private void procesarDetallesPorEmpleado(NovedadLiquidacion novedad, Map<Integer, Integer> sumaPorEmpleado) {
        List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findByEmpleado_Id(novedad.getEmpleado().getId());
        for (DetalleLiquidacion detalle : detalles) {
            if (List.of(1, 2, 3, 4).contains(detalle.getConceptoSalarial().getId())) {
                sumaPorEmpleado.merge(novedad.getEmpleado().getId(), detalle.getMonto(), Integer::sum);
            }
        }
    }

    private void imprimirSumaPorEmpleado(Map<Integer, Integer> sumaPorEmpleado) {
        logger.info("Suma por empleado:");
        sumaPorEmpleado.forEach((empleadoId, suma) -> logger.info("Empleado ID: {}, Suma: {}", empleadoId, suma));
    }

    private void procesarNovedades(List<NovedadLiquidacion> novedades, Map<Integer, Integer> sumaPorEmpleado, Date fechaActual, Date fechaInicio) {
        for (NovedadLiquidacion novedad : novedades) {
            Integer empleadoId = novedad.getEmpleado().getId();
            Integer cantidadNovedades = novedad.getCantidad();
            Integer sumaTotal = sumaPorEmpleado.get(empleadoId);

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
                return cantidadNovedades; // Esto puede ser revisado si es redundante
            default:
                return 0; // Si el concepto no es relevante
        }
    }

    private void guardarDetalleLiquidacion(NovedadLiquidacion novedad, int resultado, Date fechaActual, Date fechaInicio) {
        // Verificar si ya existe un detalle de liquidación para el mismo empleado y concepto
        Optional<DetalleLiquidacion> detalleExistente = detalleLiquidacionRepository.findByEmpleado_IdAndConceptoSalarial_Id(novedad.getEmpleado().getId(), novedad.getConcepto().getId());
        
        // Solo guardar si no existe
        if (!detalleExistente.isPresent()) {
            DetalleLiquidacion nuevoDetalle = new DetalleLiquidacion();
            nuevoDetalle.setEmpleado(novedad.getEmpleado());
            nuevoDetalle.setConceptoSalarial(novedad.getConcepto());
            nuevoDetalle.setMonto(resultado);
            nuevoDetalle.setFechaLiquidacion(fechaActual);
            nuevoDetalle.setPeriodo(fechaInicio);
            detalleLiquidacionRepository.save(nuevoDetalle);
            logger.info("Detalle liquidación guardado para empleado ID: {}, Monto: {}", novedad.getEmpleado().getId(), resultado);
        } else {
            logger.warn("Ya existe un detalle de liquidación para el empleado ID: {} y concepto ID: {}", novedad.getEmpleado().getId(), novedad.getConcepto().getId());
        }
    }
}
