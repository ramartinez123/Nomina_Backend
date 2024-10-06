package com.nomina.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nomina.backend.model.DetalleLiquidacion;


public interface DetalleLiquidacionRepository extends JpaRepository<DetalleLiquidacion, Integer> {
	   // Método para buscar los detalles por el ID de la novedad
    List<DetalleLiquidacion> findByEmpleado_Id(Integer empleadoId);
    List<DetalleLiquidacion> findByConceptoSalarial_Id(Integer conceptoSalarialId);
}
