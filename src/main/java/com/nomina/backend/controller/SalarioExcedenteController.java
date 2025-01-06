package com.nomina.backend.controller;

import com.nomina.backend.dto.SalarioExcedenteDTO;
import com.nomina.backend.service.SalarioExcedenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/salarios-excedentes")
public class SalarioExcedenteController {

    private final SalarioExcedenteService salarioExcedenteService;

    public SalarioExcedenteController(SalarioExcedenteService salarioExcedenteService) {
        this.salarioExcedenteService = salarioExcedenteService;
    }

    // Crear un Salario Excedente
    @PostMapping
    public ResponseEntity<SalarioExcedenteDTO> createSalarioExcedente(@RequestBody SalarioExcedenteDTO salarioExcedenteDTO) {
        SalarioExcedenteDTO created = salarioExcedenteService.createSalarioExcedente(salarioExcedenteDTO);
        return ResponseEntity.status(201).body(created);
    }

    // Actualizar un Salario Excedente
    @PutMapping("/{id}")
    public ResponseEntity<SalarioExcedenteDTO> updateSalarioExcedente(@PathVariable int id, @RequestBody SalarioExcedenteDTO salarioExcedenteDTO) {
        SalarioExcedenteDTO updated = salarioExcedenteService.updateSalarioExcedente(id, salarioExcedenteDTO);
        return ResponseEntity.ok(updated);
    }

    // Listar todos los Salarios Excedentes
    @GetMapping
    public ResponseEntity<List<SalarioExcedenteDTO>> getAllSalariosExcedentes() {
        List<SalarioExcedenteDTO> salariosExcedentes = salarioExcedenteService.getAllSalariosExcedentes();
        return ResponseEntity.ok(salariosExcedentes);
    }
}