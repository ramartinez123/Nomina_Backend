
package com.nomina.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.nomina.backend.service.ListadoService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/liquidacion")
public class ListadoController {
    
    @Autowired
    private ListadoService listadoService;

    @PostMapping("/listadoSueldoNeto")
    public ResponseEntity<List<String>> listadoSueldoNeto(@RequestBody Map<String, String> requestBody) {
        String fechaInicioStr = requestBody.get("fechaInicio");
        String fechaFinStr = requestBody.get("fechaFin");

        // Convertir las cadenas a LocalDate
        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr);

        // Llama al método del servicio
        List<String> listado = listadoService.obtenerListadoSueldoNeto(fechaInicio, fechaFin);

        // Devuelve la lista en la respuesta
        return ResponseEntity.ok(listado);
    }
}