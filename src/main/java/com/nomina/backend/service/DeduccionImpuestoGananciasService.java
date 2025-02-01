package com.nomina.backend.service;

import com.nomina.backend.model.DeduccionImpuestoGanancias;
import com.nomina.backend.repository.DeduccionImpuestoGananciasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeduccionImpuestoGananciasService {

    @Autowired
    private DeduccionImpuestoGananciasRepository repository;

    // Obtener todas las deducciones
    public List<DeduccionImpuestoGanancias> getAllDeducciones() {
        return repository.findAll();
    }

    // Obtener una deducción por ID
    public DeduccionImpuestoGanancias getDeduccionById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deducción no encontrada con ID: " + id));
    }

    // Crear una nueva deducción
    public DeduccionImpuestoGanancias createDeduccion(DeduccionImpuestoGanancias deduccion) {
        return repository.save(deduccion);
    }

    // Actualizar una deducción existente
    public DeduccionImpuestoGanancias updateDeduccion(int id, DeduccionImpuestoGanancias nuevaDeduccion) {
        DeduccionImpuestoGanancias deduccionExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deducción no encontrada con ID: " + id));

        deduccionExistente.setIdTipoDeduccion(nuevaDeduccion.getIdTipoDeduccion());
        deduccionExistente.setNombre(nuevaDeduccion.getNombre());
        deduccionExistente.setFechaInicio(nuevaDeduccion.getFechaInicio());
        deduccionExistente.setFechaFin(nuevaDeduccion.getFechaFin());
        deduccionExistente.setValor(nuevaDeduccion.getValor());

        return repository.save(deduccionExistente);
    }

    // Eliminar una deducción por ID
    public void deleteDeduccion(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Deducción no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }
}