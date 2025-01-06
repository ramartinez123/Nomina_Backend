package com.nomina.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nomina.backend.Iservice.IdepartamentoService;
import com.nomina.backend.dto.DepartamentoDTO;
import com.nomina.backend.model.Departamento;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    @Autowired
    private IdepartamentoService departamentoService;

    // Obtener todos los departamentos
    @GetMapping
    public ResponseEntity<?> getAllDepartamentos() {
        try {
            List<Departamento> departamentos = departamentoService.listDepartamento();

            if (departamentos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            List<DepartamentoDTO> departamentoDTOs = departamentos.stream()
                    .map(departamento -> new DepartamentoDTO(
                            departamento.getIdDepartamento(),
                            departamento.getNombre(),
                            departamento.getDescripcion()))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(departamentoDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Obtener un departamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartamentoById(@PathVariable int id) {
        try {
            Optional<Departamento> departamentoOpt = departamentoService.findById(id);

            if (departamentoOpt.isPresent()) {
                Departamento departamento = departamentoOpt.get();
                DepartamentoDTO departamentoDTO = new DepartamentoDTO(
                        departamento.getIdDepartamento(),
                        departamento.getNombre(),
                        departamento.getDescripcion()
                );
                return ResponseEntity.ok(departamentoDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Crear un nuevo departamento
    @PostMapping
    public ResponseEntity<?> createDepartamento(@RequestBody DepartamentoDTO departamentoDTO) {
        try {
            Departamento departamento = new Departamento();
            departamento.setNombre(departamentoDTO.getNombre());
            departamento.setDescripcion(departamentoDTO.getDescripcion());

            int savedId = departamentoService.saveDepartamento(departamento);
            if (savedId == -1) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al guardar el departamento.");
            }
            departamentoDTO.setIdDepartamento(savedId);
            return ResponseEntity.status(HttpStatus.CREATED).body(departamentoDTO);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Actualizar un departamento existente
    @PutMapping("/{id}")
    public ResponseEntity<String> updateDepartamento(@PathVariable int id, @RequestBody DepartamentoDTO departamentoDTO) {
        try {
            Optional<Departamento> existingDepartamentoOpt = departamentoService.findById(id);

            if (existingDepartamentoOpt.isPresent()) {
                Departamento existingDepartamento = existingDepartamentoOpt.get();
                existingDepartamento.setNombre(departamentoDTO.getNombre());
                existingDepartamento.setDescripcion(departamentoDTO.getDescripcion());

                int updatedId = departamentoService.saveDepartamento(existingDepartamento);
                if (updatedId == -1) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error al actualizar el departamento.");
                }
                return ResponseEntity.ok("Departamento actualizado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Departamento no encontrado");
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Buscar departamentos por nombre
    @GetMapping("/busqueda")
    public ResponseEntity<?> findByNombre(@RequestParam String nombre) {
        try {
            List<Departamento> departamentos = departamentoService.findByNombre(nombre);

            List<DepartamentoDTO> departamentoDTOs = departamentos.stream()
                    .map(departamento -> new DepartamentoDTO(
                            departamento.getIdDepartamento(),
                            departamento.getNombre(),
                            departamento.getDescripcion()))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(departamentoDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor");
    }
}
