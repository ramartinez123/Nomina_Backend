package com.nomina.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nomina.backend.Iservice.IpuestoService;
import com.nomina.backend.dto.PuestoDTO;
import com.nomina.backend.model.Puesto;

@RestController
@RequestMapping("/api/puestos")
public class PuestoController {

    @Autowired
    private IpuestoService puestoService;

    // Obtener todos los puestos
    @GetMapping
    public ResponseEntity<?> getAllPuestos() {
        try {
            List<Puesto> puestos = puestoService.listPuesto();

            // Verificar si la lista de puestos está vacía
            if (puestos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // No hay contenido
            }

            List<PuestoDTO> puestoDTOs = puestos.stream()
                    .map(puesto -> new PuestoDTO(
                            puesto.getIdPuesto(),
                            puesto.getNombre(),
                            puesto.getDescripcion()))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(puestoDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Obtener un puesto por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPuestoById(@PathVariable int id) {
        try {
            Optional<Puesto> puestoOpt = puestoService.findById(id);

            if (puestoOpt.isPresent()) {
                Puesto puesto = puestoOpt.get();
                PuestoDTO puestoDTO = new PuestoDTO(
                        puesto.getIdPuesto(),
                        puesto.getNombre(),
                        puesto.getDescripcion()
                );
                return ResponseEntity.ok(puestoDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Crear un nuevo puesto
    @PostMapping
    public ResponseEntity<?> createPuesto(@RequestBody PuestoDTO puestoDTO) {
        try {
            Puesto puesto = new Puesto();
            puesto.setNombre(puestoDTO.getNombre());
            puesto.setDescripcion(puestoDTO.getDescripcion());

            // Guarda el puesto y obtiene el ID
            int savedId = puestoService.savePuesto(puesto);
            if (savedId == -1) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al guardar el puesto.");
            }
            puestoDTO.setIdPuesto(savedId);
            return ResponseEntity.status(HttpStatus.CREATED).body(puestoDTO);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Actualizar un puesto existente
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePuesto(@PathVariable int id, @RequestBody PuestoDTO puestoDTO) {
        try {
            Optional<Puesto> existingPuestoOpt = puestoService.findById(id);

            if (existingPuestoOpt.isPresent()) {
                Puesto existingPuesto = existingPuestoOpt.get();

                existingPuesto.setNombre(puestoDTO.getNombre());
                existingPuesto.setDescripcion(puestoDTO.getDescripcion());

                int updatedId = puestoService.savePuesto(existingPuesto);
                if (updatedId == -1) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error al actualizar el puesto.");
                }
                return ResponseEntity.ok("OK");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Puesto no encontrado");
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor");
    }
}
