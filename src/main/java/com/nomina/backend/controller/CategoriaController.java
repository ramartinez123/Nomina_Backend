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
	public ResponseEntity<?> getAllCategorias() {
	    try {
	        List<Categoria> categorias = categoriaService.listCategoria();
	        
	        // Verificar si la lista de categorías está vacía
	        if (categorias.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // No hay contenido
	        }
	        
	        List<CategoriaDTO> categoriaDTOs = categorias.stream()
	                .map(categoria -> new CategoriaDTO(
	                        categoria.getIdCategoria(),
	                        categoria.getConvenio().getIdConvenio(),
	                        categoria.getNombre(),
	                        categoria.getDescripcion()))
	                .collect(Collectors.toList());      
	        return new ResponseEntity<>(categoriaDTOs, HttpStatus.OK);
	    } catch (Exception e) {       
	    	return handleException(e); 
	    }
	}

	// Obtener una categoría por ID 
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoriaById(@PathVariable int id) {
	    try {
	        Optional<Categoria> categoriaBis = categoriaService.findById(id);

	        if (categoriaBis.isPresent()) {
	            Categoria categoria = categoriaBis.get();
	            CategoriaDTO categoriaDTO = new CategoriaDTO(
	                    categoria.getIdCategoria(),
	                    categoria.getConvenio().getIdConvenio(),
	                    categoria.getNombre(),
	                    categoria.getDescripcion()
	            );
	            return ResponseEntity.ok(categoriaDTO);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	    	return handleException(e); 
	    }
	}

	// Crear una nueva categoría
	@PostMapping
	public ResponseEntity<?> createCategoria(@RequestBody CategoriaDTO categoriaDTO) {
	    try {
	        Optional<Convenio> convenioOpt = convenioService.findById(categoriaDTO.getIdConvenio());

	        if (!convenioOpt.isPresent()) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body("Convenio no encontrado."); 
	        }

	        Categoria categoria = new Categoria();
	        categoria.setConvenio(convenioOpt.get());
	        categoria.setNombre(categoriaDTO.getNombre());
	        categoria.setDescripcion(categoriaDTO.getDescripcion());

	        // Guarda la categoría y obtiene el ID
	        int savedId = categoriaService.saveCategoria(categoria);
	        if (savedId == -1) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Error al guardar la categoría.");
	        }
	        categoriaDTO.setIdCategoria(savedId);
	        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaDTO);
	        
	    } catch (Exception e) {
	    	return handleException(e); 
	    }
	}

	// Actualizar una categoría existente
	@PutMapping("/{id}")
	public ResponseEntity<String> updateCategoria(@PathVariable int id, @RequestBody CategoriaDTO categoriaDTO) {
	    try {
	        Optional<Categoria> existingCategoriaOpt = categoriaService.findById(id);

	        if (existingCategoriaOpt.isPresent()) {
	            Categoria existingCategoria = existingCategoriaOpt.get();

	            if (categoriaDTO.getIdConvenio() != null) {
	                Optional<Convenio> convenioOpt = convenioService.findById(categoriaDTO.getIdConvenio());

	                if (!convenioOpt.isPresent()) {
	                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                            .body("Convenio no encontrado"); 
	                }

	                existingCategoria.setConvenio(convenioOpt.get()); 
	            }

	            existingCategoria.setNombre(categoriaDTO.getNombre());
	            existingCategoria.setDescripcion(categoriaDTO.getDescripcion());

	            int updatedId = categoriaService.saveCategoria(existingCategoria);
	            if (updatedId == -1) {
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                        .body("Error al actualizar la categoría.");
	            }
	            return ResponseEntity.ok("OK"); 
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body("Categoría no encontrada"); 
	        }
	    } catch (Exception e) {

	        return  handleException(e);
	    }
	}

	// Buscar categorías por nombre
	@GetMapping("/busqueda")
	public ResponseEntity<?> findByNombre(@RequestParam String nombre) {
		try {
			List<Categoria> categorias = categoriaService.findByNombre(nombre);

			// Convertir cada Categoria a CategoriaDTO
			List<CategoriaDTO> categoriaDTOs = categorias.stream()
					.map(categoria -> new CategoriaDTO(
							categoria.getIdCategoria(),
							categoria.getConvenio().getIdConvenio(),
							categoria.getNombre(),
							categoria.getDescripcion()))
					.collect(Collectors.toList());

			return new ResponseEntity<>(categoriaDTOs, HttpStatus.OK);
		} catch  (Exception e)  {
			return handleException(e);
		}
	}
	
	private ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor");
    }
}