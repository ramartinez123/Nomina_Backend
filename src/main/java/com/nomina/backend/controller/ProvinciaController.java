package com.nomina.backend.controller;

import com.nomina.backend.dto.ProvinciaDTO;
import com.nomina.backend.service.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/provincias")
public class ProvinciaController {

    @Autowired
    private ProvinciaService provinciaService;

    // Método para listar todas las provincias
    @GetMapping
    public ResponseEntity<List<ProvinciaDTO>> listarProvincias() {
        try {
            List<ProvinciaDTO> provincias = provinciaService.listarProvincias();
            return new ResponseEntity<>(provincias, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para obtener una provincia por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProvinciaPorId(@PathVariable int id) {
        try {
            Optional<ProvinciaDTO> provinciaDTO = provinciaService.getProvinciaById(id);
            return provinciaDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para crear una nueva provincia
    @PostMapping
    public ResponseEntity<?> crearProvincia(@RequestBody ProvinciaDTO provinciaDTO) {
        try {
            ProvinciaDTO creadoProvincia = provinciaService.createProvincia(provinciaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(creadoProvincia);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para actualizar una provincia
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProvincia(@PathVariable int id, @RequestBody ProvinciaDTO provinciaDTO) {
        try {
            ProvinciaDTO actualizadoProvincia = provinciaService.updateProvincia(id, provinciaDTO);
            return ResponseEntity.ok(actualizadoProvincia);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para manejar excepciones y devolver un error genérico
    private ResponseEntity<List<ProvinciaDTO>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
    }
}