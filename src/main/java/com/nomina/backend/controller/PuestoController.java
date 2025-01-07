package com.nomina.backend.controller;

import com.nomina.backend.dto.PuestoDTO;
import com.nomina.backend.service.PuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/puestos")
public class PuestoController {

    @Autowired
    private PuestoService puestoService;

    // Método para listar todos los puestos
    @GetMapping
    public ResponseEntity<List<PuestoDTO>> listarPuestos() {
        try {
            List<PuestoDTO> puestos = puestoService.listPuestos();
            return new ResponseEntity<>(puestos, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para obtener un puesto por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPuestoPorId(@PathVariable int id) {
        try {
            Optional<PuestoDTO> puestoDTO = puestoService.getPuestoById(id);
            return puestoDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para crear un nuevo puesto
    @PostMapping
    public ResponseEntity<?> crearPuesto(@RequestBody PuestoDTO puestoDTO) {
        try {
            PuestoDTO creadoPuesto = puestoService.createPuesto(puestoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(creadoPuesto);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para actualizar un puesto
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPuesto(@PathVariable int id, @RequestBody PuestoDTO puestoDTO) {
        try {
            PuestoDTO actualizadoPuesto = puestoService.updatePuesto(id, puestoDTO);
            return ResponseEntity.ok(actualizadoPuesto);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para manejar excepciones y devolver un error genérico
    private ResponseEntity<List<PuestoDTO>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
    }
}