package com.nomina.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nomina.backend.exception.EmpleadoAlreadyActiveException;
import com.nomina.backend.exception.EmpleadoNotFoundException;
import com.nomina.backend.model.Empleado;
import com.nomina.backend.model.EstadoEmpleado;
import com.nomina.backend.repository.EmpleadoRepository;
import jakarta.transaction.Transactional;

@Service
public class AnulaBajaEmpleadoService {

    private static final Logger logger = LoggerFactory.getLogger(AnulaBajaEmpleadoService.class);

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Transactional
    public void anulaBajaEmpleado(Integer idEmpleado) {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
            .orElseThrow(() -> new EmpleadoNotFoundException("Empleado no encontrado con id: " + idEmpleado));

        // Verificar si el empleado ya está activo
        if (empleado.getEstadoEmpleado().equals(EstadoEmpleado.ACTIVO)) {
            throw new EmpleadoAlreadyActiveException("El empleado con id " + idEmpleado + " ya está activo");
        }

        // Loguear antes de hacer el cambio
        logger.info("Anulando la baja del empleado con id: {}", idEmpleado);

        // Anular la baja
        empleado.setFechaFin(null); 
        empleado.setEstadoEmpleado(EstadoEmpleado.ACTIVO); 
        empleado.setMotivo(null);

        empleadoRepository.save(empleado);

        // Loguear después de guardar los cambios
        logger.info("El empleado con id {} ha sido reactivado", idEmpleado);
    }
}