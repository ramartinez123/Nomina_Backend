package com.nomina.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nomina.backend.exception.EmpleadoNotFoundException;
import com.nomina.backend.exception.MotivoInvalidoException;
import com.nomina.backend.model.Empleado;
import com.nomina.backend.model.EstadoEmpleado;
import com.nomina.backend.model.Motivo;
import com.nomina.backend.repository.EmpleadoRepository;

@Service
public class BajaEmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;    

    public void darDeBajaEmpleado(Integer idEmpleado, java.sql.Date fechaFin, String motivo) {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
            .orElseThrow(() -> new EmpleadoNotFoundException("Empleado no encontrado con id: " + idEmpleado));
        
        empleado.setFechaFin(fechaFin); 
        empleado.setEstadoEmpleado(EstadoEmpleado.INACTIVO); 

        // Validar el motivo
        if (!isMotivoValido(motivo)) {
            throw new MotivoInvalidoException("Motivo no válido: " + motivo);
        }
        
        empleado.setMotivo(Motivo.valueOf(motivo.toUpperCase()));  
        empleadoRepository.save(empleado);
    }    

    private boolean isMotivoValido(String motivo) {
        try {
            Motivo.valueOf(motivo.toUpperCase());
            return true; 
        } catch (IllegalArgumentException e) {
            return false; 
        }
    }
}