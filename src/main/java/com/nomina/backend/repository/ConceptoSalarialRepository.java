package com.nomina.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nomina.backend.model.ConceptoSalarial;

public interface ConceptoSalarialRepository extends JpaRepository<ConceptoSalarial, Integer> {
    // Puedes agregar métodos adicionales aquí si lo necesitas
}