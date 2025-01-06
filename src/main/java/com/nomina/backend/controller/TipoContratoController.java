package com.nomina.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomina.backend.model.TipoContrato;


import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/enumes")
public class TipoContratoController {
	
    @GetMapping("/contrato")
    public List<String> getTipoContrato() {
        return Arrays.stream(TipoContrato.values())
                     .map(TipoContrato::getTipo)
                     .toList();
    }
}
