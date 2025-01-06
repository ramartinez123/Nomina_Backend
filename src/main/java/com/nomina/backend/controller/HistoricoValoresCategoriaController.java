package com.nomina.backend.controller;

import com.nomina.backend.dto.HistoricoValoresCategoriaDTO;
import com.nomina.backend.service.HistoricoValoresCategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/historico-valores-categoria")
public class HistoricoValoresCategoriaController {
    private final HistoricoValoresCategoriaService service;

    public HistoricoValoresCategoriaController(HistoricoValoresCategoriaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<HistoricoValoresCategoriaDTO> create(@RequestBody HistoricoValoresCategoriaDTO dto) {
        HistoricoValoresCategoriaDTO responseDTO = service.save(dto);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoricoValoresCategoriaDTO> update(
            @PathVariable Integer id,
            @RequestBody HistoricoValoresCategoriaDTO dto) {
        if (dto.getId() == null || !dto.getId().equals(id)) {
            throw new RuntimeException("El ID del cuerpo debe coincidir con el ID de la URL");
        }
        HistoricoValoresCategoriaDTO responseDTO = service.save(dto);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoValoresCategoriaDTO> getById(@PathVariable Integer id) {
        HistoricoValoresCategoriaDTO dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<HistoricoValoresCategoriaDTO>> getAll() {
        List<HistoricoValoresCategoriaDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}