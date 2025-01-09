package com.nomina.backend.service;

import com.nomina.backend.model.ConceptoSalarial;
import com.nomina.backend.model.DetalleLiquidacion;
import com.nomina.backend.model.Empleado;
import com.nomina.backend.repository.ConceptoSalarialRepository;
import com.nomina.backend.repository.DetalleLiquidacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Service
public class LiquidacionCargasSocialesService {

    @Autowired
    private DetalleLiquidacionRepository detalleLiquidacionRepository;

    @Autowired
    private ConceptoSalarialRepository conceptoSalarialRepository;

    public void procesarCargasSociales(int mes, int anio) {
        Date fechaActual = new Date(System.currentTimeMillis());

        // Obtener la fecha de inicio del mes
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date fechaInicio = new Date(calendar.getTimeInMillis());
        System.out.println("Fecha de inicio del mes: " + fechaInicio);

        // Obtener los registros del concepto 91
        List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findByConceptoSalarialAndFechaLiquidacion(91, mes, anio);;
        System.out.println("Número de detalles obtenidos para concepto 91: " + detalles.size());

        // Obtener conceptos
        ConceptoSalarial[] conceptos = {	
                conceptoSalarialRepository.findById(1001).orElseThrow(() -> new RuntimeException("ConceptoSalarial 1001 no encontrado")),
                conceptoSalarialRepository.findById(1002).orElseThrow(() -> new RuntimeException("ConceptoSalarial 1002 no encontrado")),
                conceptoSalarialRepository.findById(1003).orElseThrow(() -> new RuntimeException("ConceptoSalarial 1003 no encontrado")),
                conceptoSalarialRepository.findById(1004).orElseThrow(() -> new RuntimeException("ConceptoSalarial 1004 no encontrado")),
                conceptoSalarialRepository.findById(1005).orElseThrow(() -> new RuntimeException("ConceptoSalarial 1005 no encontrado"))
        };

        for (DetalleLiquidacion detalle : detalles) {
            // Verificar si la fecha de liquidación coincide

           // if (((Date) detalle.getFechaLiquidacion()).toLocalDate().isEqual(fechaActual.toLocalDate()) && detalle.getEmpleado() != null) {
                System.out.println("Procesando detalle para empleado: " + detalle.getEmpleado().getId());

                for (ConceptoSalarial concepto : conceptos) {
                    // Verifica los valores de monto y concepto
                    System.out.println("Monto: " + detalle.getMonto() + ", Valor concepto: " + concepto.getValor());

                    int montoFinal = calcularMonto(detalle.getMonto(), concepto.getValor());

                    System.out.println("Monto final calculado: " + montoFinal);

                    if (montoFinal > 0) {
                        guardarNuevoDetalle(detalle.getEmpleado(), montoFinal, concepto, fechaInicio, fechaActual);
                    } else {
                        System.out.println("Monto calculado no válido: " + montoFinal);
                    }
                
            }
        }
    }

    // Método para calcular el monto
    private int calcularMonto(int monto, int valor) {
        return monto * valor / 100;
    }

    // Método para guardar el nuevo detalle
    private void guardarNuevoDetalle(Empleado empleado, int montoFinal, ConceptoSalarial concepto, Date periodo, Date fechaLiquidacion) {
        DetalleLiquidacion nuevoDetalle = new DetalleLiquidacion();
        nuevoDetalle.setEmpleado(empleado);
        nuevoDetalle.setMonto(montoFinal);
        nuevoDetalle.setConceptoSalarial(concepto);
        nuevoDetalle.setPeriodo(periodo);
        nuevoDetalle.setFechaLiquidacion(fechaLiquidacion);
        System.out.println("Guardando nuevo detalle: " + nuevoDetalle);
        detalleLiquidacionRepository.save(nuevoDetalle);
    }
}
