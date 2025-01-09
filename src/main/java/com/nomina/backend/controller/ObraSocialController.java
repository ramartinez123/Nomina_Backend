package com.nomina.backend.controller;

import com.nomina.backend.dto.ObraSocialDTO;
import com.nomina.backend.service.ObraSocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/obras-sociales")
public class ObraSocialController {

    @Autowired
    private ObraSocialService obraSocialService;

    @GetMapping
    public ResponseEntity<?> getAllObrasSociales() {
        try {
            List<ObraSocialDTO> obrasSociales = obraSocialService.listarObraSocial();
            return new ResponseEntity<>(obrasSociales, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getObraSocialById(@PathVariable int id) {
        try {
            Optional<ObraSocialDTO> obraSocialOpt = obraSocialService.findById(id);
            return obraSocialOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> createObraSocial(@RequestBody ObraSocialDTO obraSocialDTO) {
        try {
            ObraSocialDTO createdObraSocial = obraSocialService.createObraSocial(obraSocialDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdObraSocial);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateObraSocial(@PathVariable int id, @RequestBody ObraSocialDTO obraSocialDTO) {
        try {
            ObraSocialDTO updatedObraSocial = obraSocialService.updateObraSocial(id, obraSocialDTO);
            return ResponseEntity.ok(updatedObraSocial);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteObraSocial(@PathVariable int id) {
        try {
            boolean deleted = obraSocialService.deleteObraSocial(id);
            if (deleted) {
                return ResponseEntity.ok("Obra social eliminada exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Obra social no encontrada");
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
}