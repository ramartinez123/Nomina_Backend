package com.nomina.backend.repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nomina.backend.dto.DetalleLiquidacionDTO;
import com.nomina.backend.dto.LiquidacionDetalleDTO;
import com.nomina.backend.model.DetalleLiquidacion;


public interface DetalleLiquidacionRepository extends JpaRepository<DetalleLiquidacion, Integer> {
	   // Método para buscar los detalles por el ID de la novedad
    List<DetalleLiquidacion> findByEmpleado_Id(Integer empleadoId);
    
    List<DetalleLiquidacion> findByConceptoSalarial_Id(Integer conceptoSalarialId);
    
    @Query("SELECT dl.monto FROM DetalleLiquidacion dl WHERE dl.empleado.id = :empleadoId AND dl.conceptoSalarial.id = :conceptoId")
    Integer obtenerValorConcepto(@Param("empleadoId") int empleadoId, @Param("conceptoId") int conceptoId);   
	List<DetalleLiquidacion> findByEmpleadoId(Integer id);
	
	List<DetalleLiquidacion> findByEmpleadoIdAndFechaLiquidacionBetween(Integer id, LocalDate fechaInicio, LocalDate fechaFin);

	Optional<DetalleLiquidacion> findByEmpleado_IdAndConceptoSalarial_Id(Integer id, Integer id2);

	boolean existsByEmpleadoIdAndConceptoSalarialIdAndPeriodo(Integer empleadoId, int conceptoId, Date fechaInicio);

    List<DetalleLiquidacion> findByPeriodo(Date periodo);
	
	Optional<DetalleLiquidacion> findByEmpleado_IdAndConceptoSalarial_IdAndFechaLiquidacion(Integer id,
			Integer conceptoSalarialId, java.util.Date fechaLiquidacion);

	List<DetalleLiquidacion> findByPeriodoBetween(java.util.Date fechaInicio, java.util.Date fechaFin);
	
	@Query("SELECT new com.nomina.backend.dto.LiquidacionDetalleDTO(e.id, e.apellido, e.nombre, dl.monto) " +
		       "FROM DetalleLiquidacion dl " +
		       "JOIN dl.empleado e " +
		       "WHERE dl.conceptoSalarial.id = 491 AND FUNCTION('MONTH', dl.periodo) = :mes " +
		       "AND FUNCTION('YEAR', dl.periodo) = :anio")
		List<LiquidacionDetalleDTO> findByConcepto491AndMesAndAnio(@Param("mes") int mes, @Param("anio") int anio);

	List<DetalleLiquidacion> findByEmpleadoIdAndConceptoSalarialIdAndPeriodo(Integer empleadoId, Integer conceptoId,
			java.util.Date periodo);
	
	
}
