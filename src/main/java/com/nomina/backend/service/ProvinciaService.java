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
    private ProvinciaRepository provinciaRepository; 

    @Override
    public List<Provincia> listProvincia() {
        return provinciaRepository.findAll(); 
    }

    @Override
	public Optional<Provincia> findById(Integer id) {
		return provinciaRepository.findById(id);
	}

    @Override
    public int saveProvincia(Provincia provincia) {
        try {
            Provincia savedProvincia = provinciaRepository.save(provincia); 
            return savedProvincia.getIdProvincia(); 
        } catch (Exception e) {
            System.err.println("Error al guardar la provincia: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean deleteProvincia(int id) {
        try {
            if (provinciaRepository.existsById(id)) { 
                provinciaRepository.deleteById(id);
                return true; 
            } else {
                return false; 
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar la provincia: " + e.getMessage());
            return false; 
        }
    }

    @Override
    public List<Provincia> findByNombre(String nombre) {
        return provinciaRepository.findByNombreIgnoreCase(nombre); 
    }
}