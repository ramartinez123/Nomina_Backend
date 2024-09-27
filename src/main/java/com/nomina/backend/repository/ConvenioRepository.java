package com.nomina.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nomina.backend.model.Categoria;
import com.nomina.backend.model.Convenio;

@Repository
public interface ConvenioRepository extends JpaRepository<Convenio, Integer> {
	List<Convenio> findByNombre(String name);
}