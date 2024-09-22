package com.nomina.backend.Iservice;

import java.util.List;
import java.util.Optional;

import com.nomina.backend.model.Provincia;

public interface IprovinciaService {
	public List<Provincia>listProvincia();
	public Optional<Provincia> listIdProvincia(int id);
	public int saveProvincia(Provincia provincia);
	public boolean deleteProvincia(int id);
	public List<Provincia> findByProvinciaName(String name);
}
