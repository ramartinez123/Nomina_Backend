package com.nomina.backend.controller;

import com.nomina.backend.model.EscalaGanancias;
import com.nomina.backend.service.EscalaGananciasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/escalas-ganancias")
public class EscalaGananciasController {

    @Autowired
    private EscalaGananciasService escalaGananciasService;

    // Obtener todas las escalas
    @GetMapping
    public ResponseEntity<List<EscalaGanancias>> getAllEscalas() {
        List<EscalaGanancias> escalas = escalaGananciasService.getAllEscalas();
        return ResponseEntity.ok(escalas);
    }

    // Obtener una escala por ID
    @GetMapping("/{id}")
    public ResponseEntity<EscalaGanancias> getEscalaById(@PathVariable Integer id) {
        EscalaGanancias escala = escalaGananciasService.getEscalaById(id);
        return ResponseEntity.ok(escala);
    }

    // Crear una nueva escala
    @PostMapping
    public ResponseEntity<EscalaGanancias> createEscala(@RequestBody EscalaGanancias escalaGanancias) {
        EscalaGanancias nuevaEscala = escalaGananciasService.createEscala(escalaGanancias);
        return ResponseEntity.status(201).body(nuevaEscala); // 201: Creado
    }

    // Actualizar una escala existente
    @PutMapping("/{id}")
    public ResponseEntity<EscalaGanancias> updateEscala(@PathVariable Integer id, @RequestBody EscalaGanancias escalaGanancias) {
        EscalaGanancias escalaActualizada = escalaGananciasService.updateEscala(id, escalaGanancias);
        return ResponseEntity.ok(escalaActualizada);
    }

    // Eliminar una escala por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEscala(@PathVariable Integer id) {
        escalaGananciasService.deleteEscala(id);
        return ResponseEntity.noContent().build(); // 204: No Content
    }
}
