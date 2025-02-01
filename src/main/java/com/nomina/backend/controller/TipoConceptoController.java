package com.nomina.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomina.backend.model.TipoConcepto;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/enumes")
public class TipoConceptoController {

    @GetMapping("/tipos-concepto")
    public List<String> getTiposConcepto() {
        return Arrays.stream(TipoConcepto.values())
                     .map(Enum::name) // Convierte cada enum en su representación de string (nombre del enum).
                     .toList();
    }
}