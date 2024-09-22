package com.nomina.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nomina.backend.model.Departamento;
import com.nomina.backend.model.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
	List<Empleado> findByEmpleadoName(String name);
}