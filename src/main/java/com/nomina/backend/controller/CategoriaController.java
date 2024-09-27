package com.nomina.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nomina.backend.Iservice.IcategoriaService;
import com.nomina.backend.Iservice.IconvenioService;
import com.nomina.backend.dto.CategoriaDTO;
import com.nomina.backend.model.Categoria;
import com.nomina.backend.model.Convenio;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private IcategoriaService categoriaService;
    
    @Autowired
	private IconvenioService convenioService;

    // Obtener todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() {
        List<Categoria> categorias = categoriaService.listCategoria();
        List<CategoriaDTO> categoriaDTOs = categorias.stream()
                .map(categoria -> new CategoriaDTO(categoria.getIdCategoria(),
                									categoria.getConvenio().getIdConvenio(),
                									categoria.getNombre(),
                									categoria.getDescripcion()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(categoriaDTOs, HttpStatus.OK);
    }

    // Obtener una categoría por ID Cambiar
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable int id) {
        Optional<Categoria> categoria = categoriaService.listIdCategoria(id);
        return categoria.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva categoría
    @PostMapping
    public ResponseEntity<Categoria> createCategoria(@RequestBody CategoriaDTO categoriaDTO) {

        Optional<Convenio> convenioOpt = convenioService.findById(categoriaDTO.getIdConvenio());

        if (!convenioOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Categoria categoria = new Categoria();
        categoria.setConvenio(convenioOpt.get()); 
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());

        categoriaService.saveCategoria(categoria);

        return new ResponseEntity<>(HttpStatus.CREATED); 
    }

    // Actualizar una categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable int id, @RequestBody CategoriaDTO categoriaDTO) {
        Optional<Categoria> existingCategoriaOpt = categoriaService.listIdCategoria(id);

        if (existingCategoriaOpt.isPresent()) {
            Categoria existingCategoria = existingCategoriaOpt.get();

            if (categoriaDTO.getIdConvenio() != null) {
                Optional<Convenio> convenioOpt = convenioService.findById(categoriaDTO.getIdConvenio());

                if (!convenioOpt.isPresent()) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Si el convenio no existe, retornar error
                }

                existingCategoria.setConvenio(convenioOpt.get()); // Asignar el convenio existente
            }

            existingCategoria.setNombre(categoriaDTO.getNombre());
            existingCategoria.setDescripcion(categoriaDTO.getDescripcion());

            categoriaService.saveCategoria(existingCategoria);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si la categoría no existe
        }
    }


    // Buscar categorías por nombre
    @GetMapping("/search")
    public ResponseEntity<List<Categoria>> findByNombre(@RequestParam String nombre) {
        List<Categoria> categorias = categoriaService.findByNombre(nombre);
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }
}