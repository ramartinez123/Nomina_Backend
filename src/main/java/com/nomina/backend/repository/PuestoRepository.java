package com.nomina.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nomina.backend.model.Provincia;
import com.nomina.backend.model.Puesto;

@Repository
public interface PuestoRepository extends JpaRepository<Puesto, Integer> {
	List<Puesto> findByNombre(String name);
}