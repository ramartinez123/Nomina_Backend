package com.nomina.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomina.backend.model.Empleado;
import com.nomina.backend.model.EstadoEmpleado;
import com.nomina.backend.model.Motivo;
import com.nomina.backend.repository.EmpleadoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BajaEmpleadoService {


	    @Autowired
	    private EmpleadoRepository empleadoRepository;    

	    public void darDeBajaEmpleado(Integer idEmpleado, java.sql.Date fechaFin, String motivo) {
	        Optional<Empleado> empleadoOpt = empleadoRepository.findById(idEmpleado);
	        if (empleadoOpt.isPresent()) {
	            Empleado empleado = empleadoOpt.get();
	            empleado.setFechaFin(fechaFin); 
	            empleado.setEstadoEmpleado(EstadoEmpleado.INACTIVO); 
	            empleado.setMotivo(Motivo.valueOf(motivo.toUpperCase()));  
	            empleadoRepository.save(empleado);
	        } else {
	            throw new EntityNotFoundException("Empleado no encontrado");
	        }
	    }	    
	}