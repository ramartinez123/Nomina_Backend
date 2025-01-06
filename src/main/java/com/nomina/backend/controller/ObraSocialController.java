package com.nomina.backend.controller;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nomina.backend.Iservice.IobraSocialService;
import com.nomina.backend.dto.ObraSocialDTO;
import com.nomina.backend.model.ObraSocial;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/obras-sociales")
public class ObraSocialController {

    @Autowired
    private IobraSocialService obraSocialService;

    // Obtener todas las obras sociales
    @GetMapping
    public ResponseEntity<?> getAllObrasSociales() {
        try {
            List<ObraSocial> obrasSociales = obraSocialService.listObraSocial();

            if (obrasSociales.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // No hay contenido
            }

            List<ObraSocialDTO> obraSocialDTOs = obrasSociales.stream()
                    .map(obrasocial -> new ObraSocialDTO(
                            obrasocial.getIdObraSocial(),
                            obrasocial.getNombre(),
                            obrasocial.getDescripcion()))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(obraSocialDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Obtener una obra social por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getObraSocialById(@PathVariable int id) {
        try {
            Optional<ObraSocial> obraSocialOpt = obraSocialService.findById(id);

            if (obraSocialOpt.isPresent()) {
                ObraSocial obraSocial = obraSocialOpt.get();
                ObraSocialDTO obraSocialDTO = new ObraSocialDTO(
                        obraSocial.getIdObraSocial(),
                        obraSocial.getNombre(),
                        obraSocial.getDescripcion()
                );
                return ResponseEntity.ok(obraSocialDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Crear una nueva obra social
    @PostMapping
    public ResponseEntity<?> createObraSocial(@RequestBody ObraSocialDTO obraSocialDTO) {
        try {
            ObraSocial obraSocial = new ObraSocial();
            obraSocial.setNombre(obraSocialDTO.getNombre());
            obraSocial.setDescripcion(obraSocialDTO.getDescripcion());

            // Guarda la obra social y obtiene el ID
            int savedId = obraSocialService.saveObraSocial(obraSocial);
            if (savedId == -1) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al guardar la obra social.");
            }
            obraSocialDTO.setIdObraSocial(savedId);
            return ResponseEntity.status(HttpStatus.CREATED).body(obraSocialDTO);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Actualizar una obra social existente
    @PutMapping("/{id}")
    public ResponseEntity<String> updateObraSocial(@PathVariable int id, @RequestBody ObraSocialDTO obraSocialDTO) {
        try {
            Optional<ObraSocial> existingObraSocialOpt = obraSocialService.findById(id);

            if (existingObraSocialOpt.isPresent()) {
                ObraSocial existingObraSocial = existingObraSocialOpt.get();

                existingObraSocial.setNombre(obraSocialDTO.getNombre());
                existingObraSocial.setDescripcion(obraSocialDTO.getDescripcion());

                int updatedId = obraSocialService.saveObraSocial(existingObraSocial);
                if (updatedId == -1) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error al actualizar la obra social.");
                }
                return ResponseEntity.ok("OK");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Obra social no encontrada");
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Buscar obras sociales por nombre
    @GetMapping("/busqueda")
    public ResponseEntity<?> findByNombre(@RequestParam String nombre) {
        try {
            List<ObraSocial> obrasSociales = obraSocialService.findByNombre(nombre);

            List<ObraSocialDTO> obraSocialDTOs = obrasSociales.stream()
                    .map(obrasocial -> new ObraSocialDTO(
                            obrasocial.getIdObraSocial(),
                            obrasocial.getNombre(),
                            obrasocial.getDescripcion()))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(obraSocialDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor");
    }
}