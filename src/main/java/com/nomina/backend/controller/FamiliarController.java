package com.nomina.backend.controller;

import com.nomina.backend.dto.FamiliarDTO;

import com.nomina.backend.service.FamiliarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/familiares")
public class FamiliarController {

    @Autowired
    private FamiliarService familiarService;

    @GetMapping
    public List<FamiliarDTO> getAllFamiliares() {
        return familiarService.getAllFamiliares();
    }

    @GetMapping("/{id}")
    public FamiliarDTO getFamiliarById(@PathVariable int id) {
        return familiarService.getFamiliarById(id);
    }

    @PostMapping
    public FamiliarDTO createFamiliar(@RequestBody FamiliarDTO familiarDTO) {
        return familiarService.createFamiliar(familiarDTO);
    }

    @PutMapping("/{id}")
    public FamiliarDTO updateFamiliar(@PathVariable int id, @RequestBody FamiliarDTO familiarDTO) {
        return familiarService.updateFamiliar(id, familiarDTO);
    }
}