package com.nomina.backend.controller;

import com.nomina.backend.service.LiquidacionCargasSocialesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
public class CargasSocialesController {

    @Autowired
    private LiquidacionCargasSocialesService liquidacionCargasSocialesService;


    @GetMapping("/procesar-cargas-sociales")
    public String procesarCargasSociales(@RequestParam int mes, @RequestParam int anio) {
        try {
            // Llamar al servicio para procesar las cargas sociales con mes y año
            liquidacionCargasSocialesService.procesarCargasSociales(mes, anio);
            return "Cargas sociales procesadas exitosamente para el mes " + mes + " y año " + anio;
        } catch (Exception e) {
            // En caso de error, retornar el mensaje de error
            return "Error al procesar las cargas sociales: " + e.getMessage();
        }
    }
}