package com.nomina.backend.controller;

import com.nomina.backend.service.AsientoComtablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/asientos-contables")
public class AsientoContablesController {

    @Autowired
    private AsientoComtablesService asientoComtablesService;

    /**
     * Endpoint para obtener la suma de los conceptos por cuenta contable con un campo adicional de tipo
     * filtrado por mes y año.
     * 
     * @param mes El mes para filtrar los datos.
     * @param año El año para filtrar los datos.
     * @return ResponseEntity con el resultado del procesamiento de conceptos por cuenta contable.
     */
    @GetMapping("/suma-por-cuenta")
    public ResponseEntity<List<Map<String, Object>>> obtenerSumaPorCuentaContable(
            @RequestParam int mes, 
            @RequestParam int anio) {
        // Llamar al servicio para generar el JSON con el campo adicional
        List<Map<String, Object>> resultado = asientoComtablesService.generarJsonConDH(mes, anio);

        // Devolver la respuesta con los datos
        return ResponseEntity.ok(resultado);
    }
}