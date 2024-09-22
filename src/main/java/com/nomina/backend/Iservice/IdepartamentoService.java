package com.nomina.backend.Iservice;

import java.util.List;
import java.util.Optional;

import com.nomina.backend.model.Departamento;

public interface IdepartamentoService {
	public List<Departamento>listDepartamento();
	public Optional<Departamento> listIdDepartamento(int id);
	public int saveDepartamento(Departamento departamento);
	public boolean deleteDepartamento(int id);
	public List<Departamento> findByDepartamentoName(String name);
}
