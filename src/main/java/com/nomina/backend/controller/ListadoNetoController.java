package com.nomina.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nomina.backend.dto.LiquidacionDetalleDTO;
import com.nomina.backend.repository.DetalleLiquidacionRepository;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/liquidaciones")
public class ListadoNetoController {

    @Autowired
    private DetalleLiquidacionRepository detalleLiquidacionRepository;

    /**
     * Endpoint para obtener liquidaciones del concepto 491 filtradas por mes y año.
     * @param mes Mes de la liquidación (1-12)
     * @param anio Año de la liquidación
     * @return Lista de LiquidacionDetalleDTO
     */
    @GetMapping("/concepto-491")
    public List<LiquidacionDetalleDTO> getByConcepto491(
       int mes,
       int anio
    ) {
        // Validaciones simples para mes y año
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
        }
        if (anio < 1900 || anio > 2100) {
            throw new IllegalArgumentException("El año debe estar en un rango razonable (1900-2100).");
        }
        
        return detalleLiquidacionRepository.findByConcepto491AndMesAndAnio(mes, anio);
    }
}