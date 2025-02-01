package com.nomina.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomina.backend.model.CuentaContable;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/enumes")
public class CuentaContableController {

    @GetMapping("/cuentas-contables")
    public List<String> getCuentasContables() {
        return Arrays.stream(CuentaContable.values())
                     .map(CuentaContable::getNombre)
                     .toList();
    }
}