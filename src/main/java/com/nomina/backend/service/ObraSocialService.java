package com.nomina.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomina.backend.Iservice.IobraSocialService;
import com.nomina.backend.model.ObraSocial;
import com.nomina.backend.repository.ObraSocialRepository; // Asegúrate de que este repositorio existe

@Service
public class ObraSocialService implements IobraSocialService {

    @Autowired
    private ObraSocialRepository obraSocialRepository; // Inyección de dependencia del repositorio

    @Override
    public List<ObraSocial> listObraSocial() {
        return obraSocialRepository.findAll(); // Retorna todas las obras sociales
    }

    @Override
    public Optional<ObraSocial> listIdObraSocial(int id) {
        return obraSocialRepository.findById(id); // Busca una obra social por su ID
    }

    @Override
    public int saveObraSocial(ObraSocial obraSocial) {
        try {
            ObraSocial savedObraSocial = obraSocialRepository.save(obraSocial); // Guarda la obra social en la base de datos
            return savedObraSocial.getIdObraSocial(); // Retorna el ID de la obra social guardada
        } catch (Exception e) {
            System.err.println("Error al guardar la obra social: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean deleteObraSocial(int id) {
        try {
            if (obraSocialRepository.existsById(id)) { // Verifica si la obra social existe antes de intentar eliminarla
                obraSocialRepository.deleteById(id);
                return true; // Devuelve true si la eliminación fue exitosa
            } else {
                return false; // Devuelve false si la obra social no existe
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar la obra social: " + e.getMessage());
            return false; // Devuelve false en caso de error
        }
    }

    @Override
    public List<ObraSocial> findByNombre(String nombre) {
        return obraSocialRepository.findByNombre(nombre); // Busca obras sociales por su nombre
    }
}