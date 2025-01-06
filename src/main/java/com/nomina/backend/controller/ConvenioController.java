package com.nomina.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nomina.backend.Iservice.IconvenioService;
import com.nomina.backend.dto.ConvenioDTO;
import com.nomina.backend.model.Convenio;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/convenios")
public class ConvenioController {

    @Autowired
    private IconvenioService convenioService;

    // Obtener todos los convenios
    @GetMapping
    public ResponseEntity<?> getAllConvenios() {
        try {
            List<Convenio> convenios = convenioService.listConvenio();

            // Verificar si la lista de convenios está vacía
            if (convenios.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // No hay contenido
            }

            List<ConvenioDTO> convenioDTOs = convenios.stream()
                    .map(convenio -> new ConvenioDTO(
                            convenio.getIdConvenio(),
                            convenio.getNombre(),
                            convenio.getDescripcion()))
                            
                    .collect(Collectors.toList());
            return new ResponseEntity<>(convenioDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Obtener un convenio por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getConvenioById(@PathVariable int id) {
        try {
            Optional<Convenio> convenioOpt = convenioService.findById(id);

            if (convenioOpt.isPresent()) {
                Convenio convenio = convenioOpt.get();
                ConvenioDTO convenioDTO = new ConvenioDTO(
                        convenio.getIdConvenio(),
                        convenio.getNombre(),
                        convenio.getDescripcion()
                                              
                );
                return ResponseEntity.ok(convenioDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Crear un nuevo convenio
    @PostMapping
    public ResponseEntity<?> createConvenio(@RequestBody ConvenioDTO convenioDTO) {
        try {
            Convenio convenio = new Convenio();
            convenio.setNombre(convenioDTO.getNombre());
            convenio.setDescripcion(convenioDTO.getDescripcion());

            // Guarda el convenio y obtiene el ID
            int savedId = convenioService.saveConvenio(convenio);
            if (savedId == -1) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al guardar el convenio.");
            }
            convenioDTO.setIdConvenio(savedId);
            return ResponseEntity.status(HttpStatus.CREATED).body(convenioDTO);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Actualizar un convenio existente
    @PutMapping("/{id}")
    public ResponseEntity<String> updateConvenio(@PathVariable int id, @RequestBody ConvenioDTO convenioDTO) {
        try {
            Optional<Convenio> existingConvenioOpt = convenioService.findById(id);

            if (existingConvenioOpt.isPresent()) {
                Convenio existingConvenio = existingConvenioOpt.get();

                existingConvenio.setNombre(convenioDTO.getNombre());
                existingConvenio.setDescripcion(convenioDTO.getDescripcion());

                int updatedId = convenioService.saveConvenio(existingConvenio);
                if (updatedId == -1) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error al actualizar el convenio.");
                }
                return ResponseEntity.ok("OK");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Convenio no encontrado");
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Manejar excepciones
    private ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor");
    }
}