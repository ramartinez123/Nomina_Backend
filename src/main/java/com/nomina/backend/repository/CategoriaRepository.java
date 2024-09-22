package com.nomina.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nomina.backend.model.Banco;
import com.nomina.backend.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	List<Categoria> findByCategoriaName(String name);
}