package com.nomina.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomina.backend.Iservice.IempleadoService;
import com.nomina.backend.model.Empleado;
import com.nomina.backend.repository.EmpleadoRepository; // Asegúrate de que este repositorio existe

@Service
public class EmpleadoService implements IempleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository; // Inyección de dependencia del repositorio

    @Override
    public List<Empleado> listEmpleado() {
        return empleadoRepository.findAll(); // Retorna todos los empleados
    }

    @Override
	public Optional<Empleado> findById(Integer id) {
		return empleadoRepository.findById(id);
	}

    @Override
    public int saveEmpleado(Empleado empleado) {
        try {
            Empleado savedEmpleado = empleadoRepository.save(empleado); // Guarda el empleado en la base de datos
            return savedEmpleado.getId(); // Retorna el ID del empleado guardado
        } catch (Exception e) {
            System.err.println("Error al guardar el empleado: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean deleteEmpleado(int id) {
        try {
            if (empleadoRepository.existsById(id)) { // Verifica si el empleado existe antes de intentar eliminarlo
                empleadoRepository.deleteById(id);
                return true; // Devuelve true si la eliminación fue exitosa
            } else {
                return false; // Devuelve false si el empleado no existe
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar el empleado: " + e.getMessage());
            return false; // Devuelve false en caso de error
        }
    }

    @Override
    public List<Empleado> findByNombre(String nombre) {
        return empleadoRepository.findByNombre(nombre); // Busca empleados por su nombre
    }
}