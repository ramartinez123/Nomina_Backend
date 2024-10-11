package com.nomina.backend.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nomina.backend.model.Familiar;

@Repository
public interface FamiliarRepository extends JpaRepository<Familiar, Integer> {
	// Contar cónyuges
    @Query("SELECT COUNT(f) FROM Familiar f WHERE f.empleado.id = :empleadoId AND f.idParentesco = 'C' AND f.fechaInicio <= :mesLiquidacion AND (f.fechaFin IS NULL OR f.fechaFin >= :mesLiquidacion)")
    Integer contarConyuges(@Param("empleadoId") int empleadoId, @Param("mesLiquidacion") Date mesLiquidacion);

    // Contar hijos
    @Query("SELECT COUNT(f) FROM Familiar f WHERE f.empleado.id = :empleadoId AND f.idParentesco = 'H' AND f.fechaInicio <= :mesLiquidacion AND (f.fechaFin IS NULL OR f.fechaFin >= :mesLiquidacion)")
    Integer contarHijos(@Param("empleadoId") int empleadoId, @Param("mesLiquidacion") Date mesLiquidacion);
}