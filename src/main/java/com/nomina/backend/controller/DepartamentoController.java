package com.nomina.backend.controller;

import com.nomina.backend.dto.DepartamentoDTO;
import com.nomina.backend.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    // Método para listar todos los departamentos
    @GetMapping
    public ResponseEntity<List<DepartamentoDTO>> listarDepartamentos() {
        try {
            List<DepartamentoDTO> departamentos = departamentoService.listDepartamento();
            return new ResponseEntity<>(departamentos, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para obtener un departamento por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerDepartamentoPorId(@PathVariable int id) {
        try {
            Optional<DepartamentoDTO> departamentoDTO = departamentoService.getDepartamentoById(id);
            return departamentoDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para crear un nuevo departamento
    @PostMapping
    public ResponseEntity<?> crearDepartamento(@RequestBody DepartamentoDTO departamentoDTO) {
        try {
            DepartamentoDTO creadoDepartamento = departamentoService.createDepartamento(departamentoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(creadoDepartamento);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para actualizar un departamento
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDepartamento(@PathVariable int id, @RequestBody DepartamentoDTO departamentoDTO) {
        try {
            DepartamentoDTO actualizadoDepartamento = departamentoService.updateDepartamento(id, departamentoDTO);
            return ResponseEntity.ok(actualizadoDepartamento);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para manejar excepciones y devolver un error genérico
    private ResponseEntity<List<DepartamentoDTO>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
    }
}