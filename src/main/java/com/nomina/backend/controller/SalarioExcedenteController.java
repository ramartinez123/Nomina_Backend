package com.nomina.backend.controller;

import com.nomina.backend.dto.SalarioExcedenteDTO;
import com.nomina.backend.service.SalarioExcedenteService;

import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> createSalarioExcedente(@RequestBody SalarioExcedenteDTO salarioExcedenteDTO) {
        try {
            SalarioExcedenteDTO salarioExcedenteCreado = salarioExcedenteService.createSalarioExcedente(salarioExcedenteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(salarioExcedenteCreado);
        } catch (Exception e) {
            return manejarExcepcion(e);
        }
    }

    // Actualizar un Salario Excedente
    @GetMapping("/{id}")
    public ResponseEntity<?> getSalarioExcedenteById(@PathVariable int id) {
        try {
            SalarioExcedenteDTO salarioExcedente = salarioExcedenteService.getSalarioExcedenteById(id);
            return ResponseEntity.ok(salarioExcedente);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Salario Excedente no encontrado: " + e.getMessage());
        } catch (Exception e) {
            return manejarExcepcion(e);
        }
    }

    // Listar todos los Salarios Excedentes
    @GetMapping
    public ResponseEntity<?> getAllSalariosExcedentes() {
        try {
            List<SalarioExcedenteDTO> salariosExcedentes = salarioExcedenteService.getAllSalariosExcedentes();
            return ResponseEntity.ok(salariosExcedentes);  
        } catch (Exception e) {
            return manejarExcepcion(e);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSalarioExcedente(@PathVariable int id, @RequestBody SalarioExcedenteDTO salarioExcedenteDTO) {
        try {
            SalarioExcedenteDTO salarioExcedenteActualizado = salarioExcedenteService.updateSalarioExcedente(id, salarioExcedenteDTO);
            return ResponseEntity.ok(salarioExcedenteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Salario Excedente no encontrado: " + e.getMessage());
        } catch (Exception e) {
            return manejarExcepcion(e);
        }
    }
    
    private ResponseEntity<?> manejarExcepcion(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
    }
}