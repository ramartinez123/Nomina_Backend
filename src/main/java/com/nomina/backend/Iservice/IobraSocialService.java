package com.nomina.backend.Iservice;

import java.util.List;
import java.util.Optional;

import com.nomina.backend.model.ObraSocial;

public interface IobraSocialService {
	public List<ObraSocial>listObraSocial();
	public Optional<ObraSocial> listIdObraSocial(int id);
	public int saveObraSocial(ObraSocial obraSocial);
	public boolean deleteObraSocial(int id);
	public List<ObraSocial> findByObraSocialName(String name);
}
