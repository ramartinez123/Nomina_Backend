package com.nomina.backend.service;

import com.nomina.backend.model.Empleado;
import com.nomina.backend.model.DetalleLiquidacion;
import com.nomina.backend.repository.DetalleLiquidacionRepository;
import com.nomina.backend.repository.EmpleadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private DetalleLiquidacionRepository detalleLiquidacionRepository;

    // Método para obtener el listado de ID, nombre y monto del concepto 491 dentro de un rango de fechas
    public List<String> obtenerListadoSueldoNeto(LocalDate fechaInicio, LocalDate fechaFin) {
        List<String> listado = new ArrayList<>();
        List<Empleado> empleados = empleadoRepository.findAll();  // Obtenemos todos los empleados

        for (Empleado empleado : empleados) {
      
            List<DetalleLiquidacion> liquidaciones = detalleLiquidacionRepository.findByEmpleadoIdAndFechaLiquidacionBetween(
                    empleado.getId(), fechaInicio, fechaFin);
            
            // Imprimir el tamaño de las liquidaciones encontradas
            System.out.println("Empleado ID: " + empleado.getId() + ", Liquidaciones encontradas: " + liquidaciones.size());

         
            for (DetalleLiquidacion liquidacion : liquidaciones) {
                if (liquidacion.getConceptoSalarial() != null && liquidacion.getConceptoSalarial().getId() == 491) {
                    // Aquí puedes trabajar con el monto o realizar la lógica que necesites
                    int monto = liquidacion.getMonto();
                    // Procesar el monto...
                    listado.add("ID: " + empleado.getId() + ", Monto: " + monto); 
                }
            }
        }

        return listado;  
    }
}