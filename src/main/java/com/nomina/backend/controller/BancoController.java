package com.nomina.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nomina.backend.Iservice.IbancoService;
import com.nomina.backend.model.Banco;

@RestController
@RequestMapping("/api/bancos")
public class BancoController {

    @Autowired
    private IbancoService bancoService;

    // Obtener todos los bancos
    @GetMapping
    public ResponseEntity<List<Banco>> getAllBancos() {
        List<Banco> bancos = bancoService.listBanco();
        return new ResponseEntity<>(bancos, HttpStatus.OK);
    }

    // Obtener un banco por ID
    @GetMapping("/{id}")
    public ResponseEntity<Banco> getBancoById(@PathVariable int id) {
        Optional<Banco> banco = bancoService.listIdBanco(id);
        return banco.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo banco
    @PostMapping
    public ResponseEntity<Banco> createBanco(@RequestBody Banco banco) {
        int bancoId = bancoService.saveBanco(banco);
        if (bancoId != -1) {
            return new ResponseEntity<>(banco, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Actualizar un banco existente
    @PutMapping("/{id}")
    public ResponseEntity<Banco> updateBanco(@PathVariable int id, @RequestBody Banco banco) {
        Optional<Banco> existingBanco = bancoService.listIdBanco(id);
        if (existingBanco.isPresent()) {
            banco.setIdBanco(id); // Asegúrate de que el ID sea correcto
            bancoService.saveBanco(banco);
            return new ResponseEntity<>(banco, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un banco
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanco(@PathVariable int id) {
        boolean isDeleted = bancoService.deleteBanco(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Buscar bancos por nombre
    @GetMapping("/search")
    public ResponseEntity<List<Banco>> findByBancoName(@RequestParam String name) {
        List<Banco> bancos = bancoService.findByBancoName(name);
        return new ResponseEntity<>(bancos, HttpStatus.OK);
    }
}
