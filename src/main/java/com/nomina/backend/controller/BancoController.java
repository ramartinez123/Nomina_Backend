package com.nomina.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nomina.backend.Iservice.IbancoService;
import com.nomina.backend.dto.BancoDTO;
import com.nomina.backend.model.Banco;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/bancos")
public class BancoController {

    @Autowired
    private IbancoService bancoService;

    // Obtener todos los bancos
    @GetMapping
    public ResponseEntity<?> getAllBancos() {
        try {
            List<Banco> bancos = bancoService.listBanco();

            // Verificar si la lista de bancos está vacía
            if (bancos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // No hay contenido
            }

            List<BancoDTO> bancoDTOs = bancos.stream()
                    .map(banco -> new BancoDTO(
                            banco.getIdBanco(),
                            banco.getNombre(),
                            banco.getDescripcion()))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(bancoDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Obtener un banco por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBancoById(@PathVariable int id) {
        try {
            Optional<Banco> bancoOpt = bancoService.findById(id);

            if (bancoOpt.isPresent()) {
                Banco banco = bancoOpt.get();
                BancoDTO bancoDTO = new BancoDTO(
                        banco.getIdBanco(),
                        banco.getNombre(),
                        banco.getDescripcion()
                );
                return ResponseEntity.ok(bancoDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Crear un nuevo banco
    @PostMapping
    public ResponseEntity<?> createBanco(@RequestBody BancoDTO bancoDTO) {
        try {
            Banco banco = new Banco();
            banco.setNombre(bancoDTO.getNombre());
            banco.setDescripcion(bancoDTO.getDescripcion());

            // Guarda el banco y obtiene el ID
            int savedId = bancoService.saveBanco(banco);
            if (savedId == -1) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al guardar el banco.");
            }
            bancoDTO.setIdBanco(savedId);
            return ResponseEntity.status(HttpStatus.CREATED).body(bancoDTO);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Actualizar un banco existente
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBanco(@PathVariable int id, @RequestBody BancoDTO bancoDTO) {
        try {
            Optional<Banco> existingBancoOpt = bancoService.findById(id);

            if (existingBancoOpt.isPresent()) {
                Banco existingBanco = existingBancoOpt.get();

                existingBanco.setNombre(bancoDTO.getNombre());
                existingBanco.setDescripcion(bancoDTO.getDescripcion());

                int updatedId = bancoService.saveBanco(existingBanco);
                if (updatedId == -1) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error al actualizar el banco.");
                }
                return ResponseEntity.ok("OK");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Banco no encontrado");
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Buscar bancos por nombre
    @GetMapping("/busqueda")
    public ResponseEntity<?> findByNombre(@RequestParam String nombre) {
        try {
            List<Banco> bancos = bancoService.findByNombre(nombre);

            // Convertir cada Banco a BancoDTO
            List<BancoDTO> bancoDTOs = bancos.stream()
                    .map(banco -> new BancoDTO(
                            banco.getIdBanco(),
                            banco.getNombre(),
                            banco.getDescripcion()))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(bancoDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor");
    }
}