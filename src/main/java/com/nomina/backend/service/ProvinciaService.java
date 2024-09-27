package com.nomina.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomina.backend.Iservice.IprovinciaService;
import com.nomina.backend.model.Provincia;
import com.nomina.backend.repository.ProvinciaRepository; // Asegúrate de que este repositorio existe

@Service
public class ProvinciaService implements IprovinciaService {

    @Autowired
    private ProvinciaRepository provinciaRepository; // Inyección de dependencia del repositorio

    @Override
    public List<Provincia> listProvincia() {
        return provinciaRepository.findAll(); // Retorna todas las provincias
    }

    @Override
    public Optional<Provincia> listIdProvincia(int id) {
        return provinciaRepository.findById(id); // Busca una provincia por su ID
    }

    @Override
    public int saveProvincia(Provincia provincia) {
        try {
            Provincia savedProvincia = provinciaRepository.save(provincia); // Guarda la provincia en la base de datos
            return savedProvincia.getIdProvincia(); // Retorna el ID de la provincia guardada
        } catch (Exception e) {
            System.err.println("Error al guardar la provincia: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean deleteProvincia(int id) {
        try {
            if (provinciaRepository.existsById(id)) { // Verifica si la provincia existe antes de intentar eliminarla
                provinciaRepository.deleteById(id);
                return true; // Devuelve true si la eliminación fue exitosa
            } else {
                return false; // Devuelve false si la provincia no existe
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar la provincia: " + e.getMessage());
            return false; // Devuelve false en caso de error
        }
    }

    @Override
    public List<Provincia> findByNombre(String nombre) {
        return provinciaRepository.findByNombre(nombre); // Busca provincias por su nombre
    }
}