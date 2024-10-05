package com.nomina.backend.controller;

import com.nomina.backend.service.LiquidacionServiceSueldo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/liquidacion") // Define el path base para el controlador
public class LiquidacionController {

    @Autowired
    private LiquidacionServiceSueldo liquidacionService;

    @PostMapping("/realizar") // Define el endpoint para realizar la liquidación
    public ResponseEntity<String> realizarLiquidacion() {
        try {
            liquidacionService.realizarLiquidacion(); // Llama al servicio para realizar la liquidación
            return ResponseEntity.ok("Liquidación realizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al realizar la liquidación: " + e.getMessage());
        }
    }
}