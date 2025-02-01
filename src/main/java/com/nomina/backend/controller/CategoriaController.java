package com.nomina.backend.controller;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nomina.backend.Iservice.IcategoriaService;
import com.nomina.backend.dto.CategoriaDTO;
import com.nomina.backend.model.Categoria;


@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private IcategoriaService categoriaService;


    @GetMapping
    public ResponseEntity<?> getAllCategorias() {
        try {
            List<CategoriaDTO> categoriaDTOs = categoriaService.getAllCategorias();
            return categoriaDTOs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(categoriaDTOs);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoriaById(@PathVariable int id) {
        try {
            Optional<CategoriaDTO> categoriaDTO = categoriaService.getCategoriaById(id);
            return categoriaDTO.isPresent() ? ResponseEntity.ok(categoriaDTO.get()) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        try {
            CategoriaDTO createdCategoria = categoriaService.createCategoria(categoriaDTO);
            return createdCategoria != null ? ResponseEntity.status(HttpStatus.CREATED).body(createdCategoria) :
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la categoría.");
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategoria(@PathVariable int id, @RequestBody CategoriaDTO categoriaDTO) {
        try {
            return categoriaService.updateCategoria(id, categoriaDTO) ? ResponseEntity.ok("OK") :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoría no encontrada");
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping("/busqueda")
    public ResponseEntity<?> findByNombre(@RequestParam String nombre) {
        try {
            List<Categoria> categoriaDTOs = categoriaService.findByNombre(nombre);
            return ResponseEntity.ok(categoriaDTOs);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
}