package com.nomina.backend.controller;

import com.nomina.backend.model.DeduccionImpuestoGanancias;
import com.nomina.backend.service.DeduccionImpuestoGananciasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/deducciones")
public class DeduccionImpuestoGananciasController {

    @Autowired
    private DeduccionImpuestoGananciasService service;

    // Obtener todas las deducciones
    @GetMapping
    public ResponseEntity<List<DeduccionImpuestoGanancias>> getAllDeducciones() {
        List<DeduccionImpuestoGanancias> deducciones = service.getAllDeducciones();
        return ResponseEntity.ok(deducciones);
    }

    // Obtener una deducción por ID
    @GetMapping("/{id}")
    public ResponseEntity<DeduccionImpuestoGanancias> getDeduccionById(@PathVariable int id) {
        DeduccionImpuestoGanancias deduccion = service.getDeduccionById(id);
        return ResponseEntity.ok(deduccion);
    }

    // Crear una nueva deducción
    @PostMapping
    public ResponseEntity<DeduccionImpuestoGanancias> createDeduccion(@RequestBody DeduccionImpuestoGanancias deduccion) {
        DeduccionImpuestoGanancias nuevaDeduccion = service.createDeduccion(deduccion);
        return ResponseEntity.status(201).body(nuevaDeduccion); // 201: Creado
    }

    // Actualizar una deducción existente
    @PutMapping("/{id}")
    public ResponseEntity<DeduccionImpuestoGanancias> updateDeduccion(@PathVariable int id, @RequestBody DeduccionImpuestoGanancias deduccion) {
        DeduccionImpuestoGanancias deduccionActualizada = service.updateDeduccion(id, deduccion);
        return ResponseEntity.ok(deduccionActualizada);
    }

    // Eliminar una deducción por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeduccion(@PathVariable int id) {
        service.deleteDeduccion(id);
        return ResponseEntity.noContent().build(); // 204: No Content
    }
}