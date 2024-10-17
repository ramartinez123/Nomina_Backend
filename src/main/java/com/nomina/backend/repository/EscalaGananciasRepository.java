package com.nomina.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nomina.backend.model.EscalaGanancias;

import java.util.Date;

@Repository
public interface EscalaGananciasRepository extends JpaRepository<EscalaGanancias, Integer> {
    EscalaGanancias findByDesdeLessThanEqualAndHastaGreaterThanEqualAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
        int desde, 
        int hasta, 
        Date fechaInicio, 
        Date fechaFin
    );
}
