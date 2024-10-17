package com.nomina.backend.service;

import com.nomina.backend.model.ConceptoSalarial;
import com.nomina.backend.model.DetalleLiquidacion;
import com.nomina.backend.model.Empleado;
import com.nomina.backend.model.NovedadLiquidacion;
import com.nomina.backend.repository.ConceptoSalarialRepository;
import com.nomina.backend.repository.DetalleLiquidacionRepository;
import com.nomina.backend.repository.NovedadLiquidacionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Service
public class LiquidacionRetencionesService {

    @Autowired
    private DetalleLiquidacionRepository detalleLiquidacionRepository;

    @Autowired
    private ConceptoSalarialRepository conceptoSalarialRepository;
    
    @Autowired
    private LiquidacionTotalesRetencionesService liquidacionTotalesRetencionesService;
    
    @Autowired
    private NovedadLiquidacionRepository novedadLiquidacionRepository;

    public void procesarYRegistrarNuevosDetalles() {
        Date fechaActual = new Date(System.currentTimeMillis());
        
        // Obtener la fecha de inicio del mes
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date fechaInicio = new Date(calendar.getTimeInMillis());
        System.out.println("Fecha de inicio del mes: " + fechaInicio);

        // Obtener los registros del concepto 91
        List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findByConceptoSalarial_Id(91);
        System.out.println("Número de detalles obtenidos para concepto 91: " + detalles.size());

        // Obtener conceptos
        ConceptoSalarial[] conceptos = {
            conceptoSalarialRepository.findById(101).orElseThrow(() -> new RuntimeException("ConceptoSalarial 101 no encontrado")),
            conceptoSalarialRepository.findById(102).orElseThrow(() -> new RuntimeException("ConceptoSalarial 102 no encontrado")),
            conceptoSalarialRepository.findById(103).orElseThrow(() -> new RuntimeException("ConceptoSalarial 103 no encontrado"))
        };

        for (DetalleLiquidacion detalle : detalles) {
            // Filtrar por fecha de liquidación y empleado
            if (((Date) detalle.getFechaLiquidacion()).toLocalDate().isEqual(fechaActual.toLocalDate()) && detalle.getEmpleado() != null) {
                for (ConceptoSalarial concepto : conceptos) {
                    int montoFinal = calcularMonto(detalle.getMonto(), concepto.getValor());
                    guardarNuevoDetalle(detalle.getEmpleado(), montoFinal, concepto, fechaInicio, fechaActual);
                }
            }
        }
        
        
        
        
        /*List<Integer> conceptoIds = List.of(180, 186);
        
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date fechaFin = new Date(calendar.getTimeInMillis());
        List<NovedadLiquidacion> novedades = novedadLiquidacionRepository.findByConceptoSalarialIdAndFecha(conceptoIds, fechaInicio, fechaFin);
        
        Map<Integer, Integer> sumaPorEmpleado = new HashMap<>();
    	
        for (NovedadLiquidacion novedad : novedades) {
            
            List<DetalleLiquidacion> detallesR = detalleLiquidacionRepository.findByEmpleado_Id(novedad.getEmpleado().getId());
    

            for (DetalleLiquidacion detalle : detallesR) {
                
                if (List.of(2, 3, 4, 5).contains(detalle.getConceptoSalarial().getId())) {
                    sumaPorEmpleado.merge(novedad.getEmpleado().getId(), detalle.getMonto(), Integer::sum);
                   
                }
            }
        }*/
        List<Integer> conceptoIds = List.of(185,285);
        Date fechaFin = new Date(calendar.getTimeInMillis());
        List<NovedadLiquidacion> novedades = novedadLiquidacionRepository.findByConceptoSalarialIdAndFecha(conceptoIds, fechaInicio, fechaFin);
        for (NovedadLiquidacion novedad : novedades) {
            Integer cantidadNovedades = novedad.getCantidad();

            // Verificar el concepto para aplicar la fórmula adecuada
            if (novedad.getConcepto().getId() == 185 || novedad.getConcepto().getId() == 285) {
                int resultado =  cantidadNovedades;

                DetalleLiquidacion nuevoDetalle = new DetalleLiquidacion();
                nuevoDetalle.setEmpleado(novedad.getEmpleado());
                nuevoDetalle.setConceptoSalarial(novedad.getConcepto());
                nuevoDetalle.setMonto(resultado);
                nuevoDetalle.setFechaLiquidacion(fechaActual);
                nuevoDetalle.setPeriodo(fechaInicio); // Establecer como primer día del mes actual

                detalleLiquidacionRepository.save(nuevoDetalle);}}
        liquidacionTotalesRetencionesService.sumarConceptosYRegistrar();
    }

    private int calcularMonto(int monto, int valor) {
        return monto * valor / 100;
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
