package com.nomina.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nomina.backend.model.Convenio;
import com.nomina.backend.model.Departamento;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
	List<Departamento> findByDepartamentoName(String name);
}