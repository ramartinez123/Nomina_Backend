package com.nomina.backend.controller;

import com.nomina.backend.dto.LiquidacionRespuestaDTO;
import com.nomina.backend.service.DetalleLiquidacionService;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/liquidaciones")
public class DetalleLiquidacionController {

    @Autowired
    private DetalleLiquidacionService detalleLiquidacionService;

    @GetMapping("/periodo/{mes}/{anio}")
    public LiquidacionRespuestaDTO obtenerLiquidacionesPorPeriodo(@PathVariable int mes, @PathVariable int anio) {

        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
        }
        if (anio < 1900 || anio > Calendar.getInstance().get(Calendar.YEAR) + 1) {
            throw new IllegalArgumentException("El año debe estar en un rango razonable.");
        }

        return detalleLiquidacionService.obtenerLiquidacionesPorPeriodo(mes, anio);
    }
}