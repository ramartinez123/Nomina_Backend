package com.nomina.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomina.backend.Iservice.IdepartamentoService;
import com.nomina.backend.model.Departamento;
import com.nomina.backend.repository.DepartamentoRepository; // Asegúrate de que este repositorio existe

@Service
public class DepartamentoService implements IdepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository; // Inyección de dependencia del repositorio

    @Override
    public List<Departamento> listDepartamento() {
        return departamentoRepository.findAll(); // Retorna todos los departamentos
    }

    @Override
    public Optional<Departamento> listIdDepartamento(int id) {
        return departamentoRepository.findById(id); // Busca un departamento por su ID
    }

    @Override
    public int saveDepartamento(Departamento departamento) {
        try {
            Departamento savedDepartamento = departamentoRepository.save(departamento); // Guarda el departamento en la base de datos
            return savedDepartamento.getIdDepartamento(); // Retorna el ID del departamento guardado
        } catch (Exception e) {
            System.err.println("Error al guardar el departamento: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean deleteDepartamento(int id) {
        try {
            if (departamentoRepository.existsById(id)) { // Verifica si el departamento existe antes de intentar eliminarlo
                departamentoRepository.deleteById(id);
                return true; // Devuelve true si la eliminación fue exitosa
            } else {
                return false; // Devuelve false si el departamento no existe
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar el departamento: " + e.getMessage());
            return false; // Devuelve false en caso de error
        }
    }

    @Override
    public List<Departamento> findByNombre(String nombre) {
        return departamentoRepository.findByNombre(nombre); // Busca departamentos por su nombre
    }
}