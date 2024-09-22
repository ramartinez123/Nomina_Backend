package com.nomina.backend.Iservice;

import java.util.List;
import java.util.Optional;

import com.nomina.backend.model.Puesto;

public interface IpuestoService {
	public List<Puesto>listPuesto();
	public Optional<Puesto> listIdPuesto(int id);
	public int savePuesto(Puesto puesto);
	public boolean deletePuesto(int id);
	public List<Puesto> findByPuestoName(String name);
}
