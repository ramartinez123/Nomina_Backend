package com.nomina.backend.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nomina.backend.model.ConceptoSalarial;

public interface ConceptoSalarialRepository extends JpaRepository<ConceptoSalarial, Integer> {
	Optional<ConceptoSalarial> findById(Integer Id);
}