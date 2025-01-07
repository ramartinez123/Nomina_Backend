package com.nomina.backend.Iservice;

import java.util.List;
import java.util.Optional;

import com.nomina.backend.dto.ObraSocialDTO;
import com.nomina.backend.model.ObraSocial;

public interface IobraSocialService {
	public List<ObraSocialDTO> listObraSocial();

	Optional<ObraSocialDTO> findById(Integer id);

	ObraSocialDTO createObraSocial(ObraSocialDTO obraSocialDTO);

	ObraSocialDTO updateObraSocial(int id, ObraSocialDTO obraSocialDTO);

	boolean deleteObraSocial(int id);

	List<ObraSocial> findByNombre(String name);
}
