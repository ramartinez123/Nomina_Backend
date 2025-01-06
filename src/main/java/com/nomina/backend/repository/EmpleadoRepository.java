package com.nomina.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nomina.backend.dto.EmpleadoAgrupadoDTO;
import com.nomina.backend.model.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
	List<Empleado> findByNombre(String name);

	@Query("SELECT new com.nomina.backend.dto.EmpleadoAgrupadoDTO(e.banco.nombre, e.cbu, e.apellido, e.nombre, d.monto) " +
		       "FROM Empleado e " +
		       "JOIN e.detalleLiquidaciones d " +
		       "JOIN d.conceptoSalarial c " +
		       "WHERE c.id = 491 " +  // Filtrar por concepto 491
		       "AND FUNCTION('MONTH', d.periodo) = :mes " +  // Filtrar por mes
		       "AND FUNCTION('YEAR', d.periodo) = :anio")    // Filtrar por año
		List<EmpleadoAgrupadoDTO> agrupadosPorBanco(@Param("mes") int mes, @Param("anio") int anio);}
