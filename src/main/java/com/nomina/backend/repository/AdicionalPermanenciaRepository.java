package com.nomina.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nomina.backend.model.AdicionalPermanencia;



@Repository
public interface AdicionalPermanenciaRepository extends JpaRepository<AdicionalPermanencia, Integer> {

	//List<HistoricoValoresCategoria> findByCategoria(Integer idCategoria);

}
