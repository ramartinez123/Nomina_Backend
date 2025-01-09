package com.nomina.backend.controller;

import com.nomina.backend.dto.EmpleadoAgrupadoDTO;
import com.nomina.backend.service.DetalleNetoBancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api")
public class DetalleNetoBancoController {

    @Autowired
    private DetalleNetoBancoService detalleNetoBancoService;

    @GetMapping("/empleados/agrupados-por-banco")
    public ResponseEntity<List<EmpleadoAgrupadoDTO>> listarAgrupadosPorBanco(
        @RequestParam int mes,
        @RequestParam int anio
    		) {
        try {

            List<EmpleadoAgrupadoDTO> empleadosAgrupados = detalleNetoBancoService.obtenerAgrupadosPorBanco(mes, anio);


            if (empleadosAgrupados.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(empleadosAgrupados);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(null);
        }
    }
}