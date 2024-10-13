package com.nomina.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nomina.backend.model.EscalaGanancias;

import java.util.Date;

@Repository
public interface EscalaGananciasRepository extends JpaRepository<EscalaGanancias, Integer> {
    
    // Método personalizado para buscar el rango donde cae el valor y la fecha de liquidación esté dentro del periodo
    EscalaGanancias findByDesdeLessThanEqualAndHastaGreaterThanEqualAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
        int desde, 
        int hasta, 
        Date fechaInicio, 
        Date fechaFin
    );
}
