package com.nomina.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nomina.backend.model.HistoricoValoresCategoria;


@Repository
public interface HistoricoValoresCategoriaRepository extends JpaRepository<HistoricoValoresCategoria, Integer> {

}
