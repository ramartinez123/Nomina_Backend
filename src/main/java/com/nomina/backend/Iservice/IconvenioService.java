package com.nomina.backend.Iservice;

import java.util.List;
import java.util.Optional;

import com.nomina.backend.model.Convenio;

public interface IconvenioService {
	public List<Convenio>listConvenio();
	public Optional<Convenio> listIdConvenio(int id);
	public int saveConvenio(Convenio convenio);
	public boolean deleteConvenio(int id);
	public List<Convenio> findByNombre(String name);
	public Optional<Convenio> findById(Integer idConvenio);
}
