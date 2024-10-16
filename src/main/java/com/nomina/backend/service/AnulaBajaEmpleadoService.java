package com.nomina.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomina.backend.model.Empleado;
import com.nomina.backend.model.EstadoEmpleado;
import com.nomina.backend.repository.EmpleadoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AnulaBajaEmpleadoService {


	    @Autowired
	    private EmpleadoRepository empleadoRepository;    

	    public void anulaBajaEmpleado(Integer idEmpleado) {
	        Optional<Empleado> empleadoOpt = empleadoRepository.findById(idEmpleado);
	        if (empleadoOpt.isPresent()) {
	            Empleado empleado = empleadoOpt.get();
	            empleado.setFechaFin(null); 
	            empleado.setEstadoEmpleado(EstadoEmpleado.ACTIVO); 
	            empleado.setMotivo(null);  
	            empleadoRepository.save(empleado);
	        } else {
	            throw new EntityNotFoundException("Empleado no encontrado");
	        }
	    }	    
	}