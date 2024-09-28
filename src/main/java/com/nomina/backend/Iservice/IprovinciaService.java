package com.nomina.backend.Iservice;

import java.util.List;
import java.util.Optional;

import com.nomina.backend.model.Provincia;

public interface IprovinciaService {
	public List<Provincia>listProvincia();
	public Optional<Provincia> findById(Integer id);
	public int saveProvincia(Provincia provincia);
	public boolean deleteProvincia(int id);
	public List<Provincia> findByNombre(String name);
}
