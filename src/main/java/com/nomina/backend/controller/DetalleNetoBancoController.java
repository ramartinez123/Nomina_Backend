package com.nomina.backend.controller;

import com.nomina.backend.dto.EmpleadoAgrupadoDTO;
import com.nomina.backend.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api")
public class DetalleNetoBancoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    /**
     * Endpoint para listar empleados agrupados por banco, filtrados por mes y año.
     *
     * @param mes El mes para filtrar los registros (1-12).
     * @param anio El año para filtrar los registros.
     * @return ResponseEntity con la estructura de empleados agrupados por banco.
     */
    @GetMapping("/empleados/agrupados-por-banco")
    public ResponseEntity<List<EmpleadoAgrupadoDTO>> listarAgrupadosPorBanco(
        @RequestParam("mes") int mes,
        @RequestParam("anio") int anio
    ) {
        // Obtener los empleados agrupados por banco, filtrados por mes y año
        List<EmpleadoAgrupadoDTO> empleadosAgrupados = empleadoRepository.agrupadosPorBanco(mes, anio);
        
        // Devolver la respuesta con los datos
        return ResponseEntity.ok(empleadosAgrupados);
    }
}