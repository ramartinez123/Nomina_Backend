package com.nomina.backend.Iservice;

import java.util.List;
import java.util.Optional;

import com.nomina.backend.dto.DepartamentoDTO;


public interface IdepartamentoService {
	public List<DepartamentoDTO> listDepartamento();
	public boolean deleteDepartamento(int id);
	public List<DepartamentoDTO> findByNombre(String name);
	Optional<DepartamentoDTO> getDepartamentoById(int id);
	DepartamentoDTO createDepartamento(DepartamentoDTO departamentoDTO);
	DepartamentoDTO updateDepartamento(int id, DepartamentoDTO departamentoDTO);
}
