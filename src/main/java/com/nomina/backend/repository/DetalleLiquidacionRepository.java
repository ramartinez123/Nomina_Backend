package com.nomina.backend.repository;
import java.util.Date;
//import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.nomina.backend.dto.LiquidacionDetalleDTO;
import com.nomina.backend.model.ConceptoSalarial;
import com.nomina.backend.model.DetalleLiquidacion;
import com.nomina.backend.model.Empleado;


	public interface DetalleLiquidacionRepository extends JpaRepository<DetalleLiquidacion, Integer> {
	
	    List<DetalleLiquidacion> findByEmpleado_Id(Integer empleadoId);
	    
	    @Query("SELECT d FROM DetalleLiquidacion d WHERE d.conceptoSalarial.id = :conceptoId AND " +
	            "MONTH(d.fechaLiquidacion) = :mes AND YEAR(d.fechaLiquidacion) = :anio")
	     List<DetalleLiquidacion> findByConceptoSalarialAndFechaLiquidacion(
	         @Param("conceptoId") int conceptoId,
	         @Param("mes") int mes,
	         @Param("anio") int anio
	     );
	    	
		List<DetalleLiquidacion> findByEmpleadoIdAndFechaLiquidacionBetween(Integer id, LocalDate fechaInicio, LocalDate fechaFin);
	
		List<DetalleLiquidacion> findByPeriodoBetween(Date fechaInicio, Date fechaFin);
	
		boolean existsByEmpleadoIdAndConceptoSalarialIdAndPeriodo(Integer empleadoId, int conceptoId, Date fechaInicio);
	
	    List<DetalleLiquidacion> findByPeriodo(Date periodo);
		
		List<DetalleLiquidacion> findByEmpleadoIdAndConceptoSalarialIdAndPeriodo(Integer empleadoId, Integer conceptoId,
				java.util.Date periodo);
		
		List<DetalleLiquidacion> findByEmpleadoId(Integer id);
			    
	    Optional<DetalleLiquidacion> findByEmpleado_IdAndConceptoSalarial_Id(Integer id, Integer id2);
		
	    Optional<DetalleLiquidacion> findByEmpleado_IdAndConceptoSalarial_IdAndFechaLiquidacion(Integer id,
				Integer conceptoSalarialId, java.util.Date fechaLiquidacion);
	
		@Query("SELECT new com.nomina.backend.dto.LiquidacionDetalleDTO(e.id, e.apellido, e.nombre, dl.monto) " +
			       "FROM DetalleLiquidacion dl " +
			       "JOIN dl.empleado e " +
			       "WHERE dl.conceptoSalarial.id = 491 AND FUNCTION('MONTH', dl.periodo) = :mes " +
			       "AND FUNCTION('YEAR', dl.periodo) = :anio")
			List<LiquidacionDetalleDTO> findByConcepto491AndMesAndAnio(@Param("mes") int mes, @Param("anio") int anio);
	
	    @Query("SELECT dl.monto FROM DetalleLiquidacion dl WHERE dl.empleado.id = :empleadoId AND dl.conceptoSalarial.id = :conceptoId")
	    Integer obtenerValorConcepto(@Param("empleadoId") int empleadoId, @Param("conceptoId") int conceptoId);
 
		
		boolean existsByEmpleadoAndConceptoSalarialAndPeriodoAndFechaLiquidacion(
			    Empleado empleado, 
			    ConceptoSalarial concepto, 
			    Date periodo, 
			    Date fechaLiquidacion
			);

	
	
}
