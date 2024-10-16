package com.nomina.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nomina.backend.model.DetalleLiquidacion;


public interface DetalleLiquidacionRepository extends JpaRepository<DetalleLiquidacion, Integer> {
	   // Método para buscar los detalles por el ID de la novedad
    List<DetalleLiquidacion> findByEmpleado_Id(Integer empleadoId);
    List<DetalleLiquidacion> findByConceptoSalarial_Id(Integer conceptoSalarialId);
    @Query("SELECT dl.monto FROM DetalleLiquidacion dl WHERE dl.empleado.id = :empleadoId AND dl.conceptoSalarial.id = :conceptoId")
    Integer obtenerValorConcepto(@Param("empleadoId") int empleadoId, @Param("conceptoId") int conceptoId);
	List<DetalleLiquidacion> findByEmpleadoId(Integer id);
	List<DetalleLiquidacion> findByEmpleadoIdAndFechaLiquidacionBetween(Integer id, LocalDate fechaInicio, LocalDate fechaFin);
}
