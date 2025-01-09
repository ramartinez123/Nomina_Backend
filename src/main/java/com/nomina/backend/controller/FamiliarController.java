package com.nomina.backend.controller;

import com.nomina.backend.dto.FamiliarDTO;
import com.nomina.backend.service.FamiliarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/familiares")
public class FamiliarController {

    @Autowired
    private FamiliarService familiarService;

    @GetMapping
    public ResponseEntity<?> listarFamiliares() {
        try {
            List<FamiliarDTO> familiares = familiarService.getAllFamiliares();
            return ResponseEntity.ok(familiares);
        } catch (Exception e) {
            return manejarExcepcion(e);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerFamiliarPorId(@PathVariable int id) {
        try {
            FamiliarDTO familiar = familiarService.getFamiliarById(id);
            return ResponseEntity.ok(familiar);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return manejarExcepcion(e);
        }
    }


    @PostMapping
    public ResponseEntity<?> crearFamiliar(@RequestBody FamiliarDTO familiarDTO) {
        try {
            FamiliarDTO familiarCreado = familiarService.createFamiliar(familiarDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(familiarCreado);
        } catch (Exception e) {
            return manejarExcepcion(e);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarFamiliar(@PathVariable int id, @RequestBody FamiliarDTO familiarDTO) {
        try {
            FamiliarDTO familiarActualizado = familiarService.updateFamiliar(id, familiarDTO);
            return ResponseEntity.ok(familiarActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return manejarExcepcion(e);
        }
    }

     private ResponseEntity<?> manejarExcepcion(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
    }
}