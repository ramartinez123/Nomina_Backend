package com.nomina.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nomina.backend.model.Provincia;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {
	List<Provincia> findByNombreIgnoreCase(String nombre);
}