package com.nomina.backend.controller;

import com.nomina.backend.dto.ConceptoSalarialDTO;
import com.nomina.backend.dto.ConceptoSalarialDTO2;
import com.nomina.backend.model.ConceptoSalarial;
import com.nomina.backend.service.ConceptoSalarialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/conceptos-salariales")
public class ConceptoSalarialController {

    @Autowired
    private ConceptoSalarialService conceptoSalarialService;

    // Método para listar todos los conceptos salariales (usando el DTO simplificado)
    @GetMapping
    public ResponseEntity<List<ConceptoSalarialDTO2>> listarConceptos() {
        List<ConceptoSalarialDTO2> conceptos = conceptoSalarialService.listarConceptos();
        return new ResponseEntity<>(conceptos, HttpStatus.OK);
    }

    // Método para crear un nuevo concepto salarial (usando el DTO completo)
    @PostMapping
    public ResponseEntity<ConceptoSalarial> crearConcepto(@RequestBody ConceptoSalarialDTO conceptoSalarialDTO) {
        ConceptoSalarial conceptoCreado = conceptoSalarialService.crearConcepto(conceptoSalarialDTO);
        return new ResponseEntity<>(conceptoCreado, HttpStatus.CREATED);
    }

    // Método para actualizar un concepto salarial existente (usando el DTO completo)
    @PutMapping("/{id}")
    public ResponseEntity<ConceptoSalarial> actualizarConcepto(
            @PathVariable Integer id,
            @RequestBody ConceptoSalarialDTO conceptoSalarialDTO2) {
        ConceptoSalarial conceptoActualizado = conceptoSalarialService.actualizarConcepto(id, conceptoSalarialDTO2);
        return new ResponseEntity<>(conceptoActualizado, HttpStatus.OK);
    }
}