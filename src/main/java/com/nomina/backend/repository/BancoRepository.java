package com.nomina.backend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nomina.backend.model.Banco;

@Repository
public interface BancoRepository extends CrudRepository<Banco, Integer> {
	List<Banco> findByNombre(String name);
}
	