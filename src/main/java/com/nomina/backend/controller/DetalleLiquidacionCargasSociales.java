package com.nomina.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomina.backend.dto.DetalleLiquidacionDTO;
import com.nomina.backend.service.DetalleLiquidacionCargasSociaelsService;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/cargas-sociales")
public class DetalleLiquidacionCargasSociales {

    @Autowired
    private DetalleLiquidacionCargasSociaelsService service;

    @GetMapping
    public ResponseEntity<List<DetalleLiquidacionDTO>> listarLiquidaciones() {
        return ResponseEntity.ok(service.obtenerLiquidaciones());
    }
}