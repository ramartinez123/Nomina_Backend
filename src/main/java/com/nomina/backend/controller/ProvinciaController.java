package com.nomina.backend.controller;

import com.nomina.backend.Iservice.IprovinciaService;
import com.nomina.backend.dto.ProvinciaDTO;
import com.nomina.backend.model.Provincia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/provincias")
public class ProvinciaController {

    @Autowired
    private IprovinciaService provinciaService;

    // Obtener todas las provincias
    @GetMapping
    public ResponseEntity<?> getAllProvincias() {
        try {
            List<Provincia> provincias = provinciaService.listProvincia();

            List<ProvinciaDTO> provinciaDTOs = provincias.stream()
                    .map(provincia -> new ProvinciaDTO(
                            provincia.getIdProvincia(),
                            provincia.getNombre()))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(provinciaDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Obtener una provincia por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProvinciaById(@PathVariable Integer id) {
        try {
            Optional<Provincia> provinciaOpt = provinciaService.findById(id);

            if (provinciaOpt.isPresent()) {
                Provincia provincia = provinciaOpt.get();
                ProvinciaDTO provinciaDTO = new ProvinciaDTO(
                        provincia.getIdProvincia(),
                        provincia.getNombre());
                return ResponseEntity.ok(provinciaDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Crear una nueva provincia
    @PostMapping
    public ResponseEntity<?> createProvincia(@RequestBody ProvinciaDTO provinciaDTO) {
        try {
            Provincia provincia = new Provincia();
            provincia.setNombre(provinciaDTO.getNombre());

            // Guarda la provincia y obtiene el ID
            Integer savedId = provinciaService.saveProvincia(provincia);
            provinciaDTO.setIdProvincia(savedId);
            return ResponseEntity.status(HttpStatus.CREATED).body(provinciaDTO);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Actualizar una provincia existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProvincia(@PathVariable Integer id, @RequestBody ProvinciaDTO provinciaDTO) {
        try {
            Optional<Provincia> existingProvinciaOpt = provinciaService.findById(id);

            if (existingProvinciaOpt.isPresent()) {
                Provincia existingProvincia = existingProvinciaOpt.get();
                existingProvincia.setNombre(provinciaDTO.getNombre());

                provinciaService.saveProvincia(existingProvincia);
                return ResponseEntity.ok("Provincia actualizada con éxito.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Provincia no encontrada");
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Eliminar una provincia
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProvincia(@PathVariable Integer id) {
        try {
            Optional<Provincia> provinciaOpt = provinciaService.findById(id);

            if (provinciaOpt.isPresent()) {
                provinciaService.deleteProvincia(id);
                return ResponseEntity.ok("Provincia eliminada con éxito.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Provincia no encontrada");
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Manejo de excepciones
    private ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());
    }
}