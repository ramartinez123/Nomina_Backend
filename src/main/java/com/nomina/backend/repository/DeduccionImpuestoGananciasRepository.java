package com.nomina.backend.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nomina.backend.model.DeduccionImpuestoGanancias;

@Repository
public interface DeduccionImpuestoGananciasRepository extends JpaRepository<DeduccionImpuestoGanancias, Integer> {

	@Query("SELECT d.valor FROM DeduccionImpuestoGanancias d WHERE :fecha BETWEEN d.fechaInicio AND d.fechaFin AND d.idTipoDeduccion = :tipoDeduccion")
    Integer obtenerDeduccion(@Param("fecha") Date fecha, @Param("tipoDeduccion") Integer tipoDeduccion);
}