package com.nomina.backend.controller;

import com.nomina.backend.dto.ConvenioDTO;
import com.nomina.backend.service.ConvenioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/convenios")
public class ConvenioController {

    @Autowired
    private ConvenioService convenioService;

    // Método para listar todos los convenios
    @GetMapping
    public ResponseEntity<List<ConvenioDTO>> listarConvenios() {
        try {
            List<ConvenioDTO> convenios = convenioService.listConvenios();
            return new ResponseEntity<>(convenios, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para obtener un convenio por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerConvenioPorId(@PathVariable int id) {
        try {
            Optional<ConvenioDTO> convenioDTO = convenioService.getConvenioById(id);
            return convenioDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para crear un nuevo convenio
    @PostMapping
    public ResponseEntity<?> crearConvenio(@RequestBody ConvenioDTO convenioDTO) {
        try {
            ConvenioDTO creadoConvenio = convenioService.createConvenio(convenioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(creadoConvenio);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para actualizar un convenio
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarConvenio(@PathVariable int id, @RequestBody ConvenioDTO convenioDTO) {
        try {
            ConvenioDTO actualizadoConvenio = convenioService.updateConvenio(id, convenioDTO);
            return ResponseEntity.ok(actualizadoConvenio);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para manejar excepciones y devolver un error genérico
    private ResponseEntity<List<ConvenioDTO>> handleException(Exception e) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
    }
}