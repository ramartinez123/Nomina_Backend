package com.nomina.backend.service;

import java.sql.Date;
import java.util.Calendar;
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
    public void sumarConceptosYRegistrar() {
        // Obtener la fecha actual
        Date fechaActual = new Date(System.currentTimeMillis());

        // Obtener todos los detalles de liquidación
        List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findAll();
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date fechaInicio = new Date(calendar.getTimeInMillis());
        
        // Filtrar los detalles para los conceptos del 1 al 89
        List<DetalleLiquidacion> detallesFiltrados = detalles.stream()
            .filter(detalle -> detalle.getConceptoSalarial().getId() >= 1 && detalle.getConceptoSalarial().getId() <= 89)
            .collect(Collectors.toList());
        
        // Mapa para acumular montos por empleado
        Map<Integer, Integer> sumaPorEmpleado = new HashMap<>();

        for (DetalleLiquidacion detalle : detalles) {
            Integer empleadoId = detalle.getEmpleado().getId();
            Integer monto = detalle.getMonto();

            // Sumar el monto al empleado correspondiente
            sumaPorEmpleado.merge(empleadoId, monto, Integer::sum);

            // Verificar imp_aportes e imp_ganancias
            procesarConcepto(detalle, fechaActual,fechaInicio);
        }

        // Registrar las sumas totales por empleado
        registrarSumaTotalPorEmpleado(sumaPorEmpleado, fechaActual);
    }

    private void procesarConcepto(DetalleLiquidacion detalle, Date fechaActual,Date fechaInicio) {
        if (detalle.getConceptoSalarial().getImpAportes()) {
            // Insertar registro en concepto 91
            guardarNuevoDetalle(detalle, 91, fechaActual,fechaInicio);
        } else if (detalle.getConceptoSalarial().getImpGanancias()) {
            // Insertar registro en concepto 92
            guardarNuevoDetalle(detalle, 92, fechaActual,fechaInicio);
        }
    }

    private void guardarNuevoDetalle(DetalleLiquidacion detalle, Integer conceptoId, Date fechaActual,  Date fechaInicio) {
        // Busca el concepto existente en lugar de crear uno nuevo
        ConceptoSalarial concepto = conceptoSalarialRepository.findById(conceptoId)
            .orElseThrow(() -> new RuntimeException("ConceptoSalarial no encontrado"));

        DetalleLiquidacion nuevoDetalle = new DetalleLiquidacion();
        nuevoDetalle.setEmpleado(detalle.getEmpleado()); // Ya está persistido
        nuevoDetalle.setMonto(detalle.getMonto());
        nuevoDetalle.setConceptoSalarial(concepto); // Usa el concepto existente
        nuevoDetalle.setPeriodo(fechaInicio);
        nuevoDetalle.setFechaLiquidacion(fechaActual);
        detalleLiquidacionRepository.save(nuevoDetalle);
    }

    private void registrarSumaTotalPorEmpleado(Map<Integer, Integer> sumaPorEmpleado, Integer conceptoId,Date fechaActual,Date fechaInicio) {
        for (Map.Entry<Integer, Integer> entry : sumaPorEmpleado.entrySet()) {
            Integer empleadoId = entry.getKey();
            Integer sumaTotal = entry.getValue();

            // Busca el empleado existente en lugar de crear uno nuevo
            Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

            DetalleLiquidacion nuevoRegistro = new DetalleLiquidacion();
            nuevoRegistro.setEmpleado(empleado); // Usa el empleado existente
            nuevoRegistro.setMonto(sumaTotal);
            nuevoRegistro.setConceptoSalarial(conceptoId); // Usa el concepto existente
            nuevoRegistro.setPeriodo(fechaInicio);
            nuevoRegistro.setFechaLiquidacion(fechaActual);
            nuevoRegistro.setFechaLiquidacion(fechaActual);
            
            // Guardar el registro en la base de datos		
            detalleLiquidacionRepository.save(nuevoRegistro);
        }
    }
}