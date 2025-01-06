package com.nomina.backend.controller;

import com.nomina.backend.dto.NovedadLiquidacionDTO;
import com.nomina.backend.service.NovedadLiquidacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/novedades")
public class NovedadLiquidacionController {

    @Autowired
    private NovedadLiquidacionService novedadLiquidacionService;

    // Obtener todas las novedades
    @GetMapping
    public ResponseEntity<List<NovedadLiquidacionDTO>> getAllNovedades() {
        List<NovedadLiquidacionDTO> novedades = novedadLiquidacionService.getAllNovedades();
        return ResponseEntity.ok(novedades);
    }

    // Obtener novedad por ID
    @GetMapping("/{id}")
    public ResponseEntity<NovedadLiquidacionDTO> getNovedadById(@PathVariable Integer id) {
        NovedadLiquidacionDTO novedad = novedadLiquidacionService.getNovedadById(id);
        return ResponseEntity.ok(novedad);
    }

    // Crear una nueva novedad
    @PostMapping
    public ResponseEntity<NovedadLiquidacionDTO> createNovedad(@RequestBody NovedadLiquidacionDTO novedadDTO) {
        NovedadLiquidacionDTO novedadCreada = novedadLiquidacionService.createNovedad(novedadDTO);
        return ResponseEntity.status(201).body(novedadCreada); // 201: Creado
    }

    // Actualizar una novedad existente
    @PutMapping("/{id}")
    public ResponseEntity<NovedadLiquidacionDTO> updateNovedad(@PathVariable Integer id, @RequestBody NovedadLiquidacionDTO novedadDTO) {
        NovedadLiquidacionDTO novedadActualizada = novedadLiquidacionService.updateNovedad(id, novedadDTO);
        return ResponseEntity.ok(novedadActualizada);
    }

    // Eliminar una novedad por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNovedad(@PathVariable Integer id) {
        novedadLiquidacionService.deleteNovedad(id);
        return ResponseEntity.noContent().build(); // 204: No Content
    }
}