package com.nomina.backend.controller;

import com.nomina.backend.dto.BancoDTO;
import com.nomina.backend.service.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/bancos")
public class BancoController {

    @Autowired
    private BancoService bancoService;

    // Método para listar todos los bancos
    @GetMapping
    public ResponseEntity<List<BancoDTO>> listarBancos() {
        try {
            List<BancoDTO> bancos = bancoService.listarBancos();
            return new ResponseEntity<>(bancos, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para obtener un banco por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerBancoPorId(@PathVariable int id) {
        try {
            Optional<BancoDTO> bancoDTO = bancoService.getBancoById(id);
            return bancoDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para crear un nuevo banco
    @PostMapping
    public ResponseEntity<?> crearBanco(@RequestBody BancoDTO bancoDTO) {
        try {
            BancoDTO creadoBanco = bancoService.createBanco(bancoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(creadoBanco);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para actualizar un banco
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarBanco(@PathVariable int id, @RequestBody BancoDTO bancoDTO) {
        try {
            BancoDTO actualizadoBanco = bancoService.updateBanco(id, bancoDTO);
            return ResponseEntity.ok(actualizadoBanco);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Método para manejar excepciones y devolver un error genérico
    private ResponseEntity<List<BancoDTO>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
    }
}