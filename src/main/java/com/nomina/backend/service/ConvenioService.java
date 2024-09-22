package com.nomina.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomina.backend.Iservice.IconvenioService;
import com.nomina.backend.model.Convenio;
import com.nomina.backend.repository.ConvenioRepository; // Asegúrate de que este repositorio existe

@Service
public class ConvenioService implements IconvenioService {

    @Autowired
    private ConvenioRepository convenioRepository; // Inyección de dependencia del repositorio

    @Override
    public List<Convenio> listConvenio() {
        return convenioRepository.findAll(); // Retorna todos los convenios
    }

    @Override
    public Optional<Convenio> listIdConvenio(int id) {
        return convenioRepository.findById(id); // Busca un convenio por su ID
    }

    @Override
    public int saveConvenio(Convenio convenio) {
        try {
            Convenio savedConvenio = convenioRepository.save(convenio); // Guarda el convenio en la base de datos
            return savedConvenio.getIdConvenio(); // Retorna el ID del convenio guardado
        } catch (Exception e) {
            System.err.println("Error al guardar el convenio: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean deleteConvenio(int id) {
        try {
            if (convenioRepository.existsById(id)) { // Verifica si el convenio existe antes de intentar eliminarlo
                convenioRepository.deleteById(id);
                return true; // Devuelve true si la eliminación fue exitosa
            } else {
                return false; // Devuelve false si el convenio no existe
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar el convenio: " + e.getMessage());
            return false; // Devuelve false en caso de error
        }
    }

    @Override
    public List<Convenio> findByConvenioName(String name) {
        return convenioRepository.findByConvenioName(name); // Busca convenios por su nombre
    }

}