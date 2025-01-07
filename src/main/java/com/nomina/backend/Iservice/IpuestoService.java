package com.nomina.backend.Iservice;

import java.util.List;
import java.util.Optional;

import com.nomina.backend.dto.PuestoDTO;
import com.nomina.backend.model.Puesto;

public interface IpuestoService {
	public List<Puesto>listPuesto();

	List<PuestoDTO> listPuestos();

	Optional<PuestoDTO> getPuestoById(int id);

	PuestoDTO createPuesto(PuestoDTO puestoDTO);

	PuestoDTO updatePuesto(int id, PuestoDTO puestoDTO);

}
