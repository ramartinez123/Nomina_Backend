package com.nomina.backend.repository;


import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.nomina.backend.model.NovedadLiquidacion;

@Repository
public interface NovedadLiquidacionRepository extends JpaRepository<NovedadLiquidacion, Integer> {
	 // Método para buscar novedades por concepto y rango de fecha
    @Query("SELECT n FROM NovedadLiquidacion n WHERE n.conceptoSalarial.id IN :ids AND n.fechaInicio BETWEEN :fechaInicio AND :fechaFin")
    List<NovedadLiquidacion> findByConceptoSalarialIdAndFecha(@Param("ids") List<Integer> ids, 
                                                                @Param("fechaInicio") Date fechaInicio, 
                                                                @Param("fechaFin") Date fechaFin);
}
