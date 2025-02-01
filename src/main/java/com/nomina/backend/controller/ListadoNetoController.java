package com.nomina.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nomina.backend.dto.ListadoNetoDTO;
import com.nomina.backend.service.ListadoNetoService;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/liquidaciones")
public class ListadoNetoController {

    @Autowired
    private ListadoNetoService listadoNetoService;

    @GetMapping("/concepto-491")
    public ResponseEntity<List<ListadoNetoDTO>> getByConcepto491(
        @RequestParam int mes,
        @RequestParam int anio
    ) {
        try {
            List<ListadoNetoDTO> detalles = listadoNetoService.obtenerLiquidacionesPorConcepto491(mes, anio);
            if (detalles.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(detalles);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}