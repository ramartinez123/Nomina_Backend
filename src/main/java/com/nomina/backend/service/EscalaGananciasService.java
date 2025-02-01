package com.nomina.backend.service;

import com.nomina.backend.model.EscalaGanancias;
import com.nomina.backend.repository.EscalaGananciasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EscalaGananciasService {

    @Autowired
    private EscalaGananciasRepository escalaGananciasRepository;

    // Obtener todas las escalas
    public List<EscalaGanancias> getAllEscalas() {
        return escalaGananciasRepository.findAll();
    }

    // Obtener una escala por ID
    public EscalaGanancias getEscalaById(Integer id) {
        return escalaGananciasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Escala no encontrada con ID: " + id));
    }

    // Crear una nueva escala
    public EscalaGanancias createEscala(EscalaGanancias escalaGanancias) {
        return escalaGananciasRepository.save(escalaGanancias);
    }

    // Actualizar una escala existente
    public EscalaGanancias updateEscala(Integer id, EscalaGanancias escalaGanancias) {
        EscalaGanancias existingEscala = escalaGananciasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Escala no encontrada con ID: " + id));

        existingEscala.setFechaInicio(escalaGanancias.getFechaInicio());
        existingEscala.setFechaFin(escalaGanancias.getFechaFin());
        existingEscala.setDesde(escalaGanancias.getDesde());
        existingEscala.setHasta(escalaGanancias.getHasta());
        existingEscala.setFijo(escalaGanancias.getFijo());
        existingEscala.setPorcentaje(escalaGanancias.getPorcentaje());
        existingEscala.setExcedente(escalaGanancias.getExcedente());

        return escalaGananciasRepository.save(existingEscala);
    }

    // Eliminar una escala por ID
    public void deleteEscala(Integer id) {
        EscalaGanancias existingEscala = escalaGananciasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Escala no encontrada con ID: " + id));
        escalaGananciasRepository.delete(existingEscala);
    }
}
