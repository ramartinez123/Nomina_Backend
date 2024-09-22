package com.nomina.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomina.backend.Iservice.IcategoriaService;
import com.nomina.backend.model.Categoria;
import com.nomina.backend.repository.CategoriaRepository; // Asegúrate de que este repositorio existe

@Service
public class CategoriaService implements IcategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository; // Inyección de dependencia del repositorio

    @Override
    public List<Categoria> listCategoria() {
        return categoriaRepository.findAll(); // Retorna todas las categorías
    }

    @Override
    public Optional<Categoria> listIdCategoria(int id) {
        return categoriaRepository.findById(id); // Busca una categoría por su ID
    }

    @Override
    public int saveCategoria(Categoria categoria) {
        try {
            Categoria savedCategoria = categoriaRepository.save(categoria); // Guarda la categoría en la base de datos
            return savedCategoria.getIdCategoria(); // Retorna el ID de la categoría guardada
        } catch (Exception e) {
            System.err.println("Error al guardar la categoría: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean deleteCategoria(int id) {
        try {
            if (categoriaRepository.existsById(id)) { // Verifica si la categoría existe antes de intentar eliminarla
                categoriaRepository.deleteById(id);
                return true; // Devuelve true si la eliminación fue exitosa
            } else {
                return false; // Devuelve false si la categoría no existe
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar la categoría: " + e.getMessage());
            return false; // Devuelve false en caso de error
        }
    }

    @Override
    public List<Categoria> findByCategoriaName(String name) {
        return categoriaRepository.findByCategoriaName(name); // Busca categorías por su nombre
    }
}