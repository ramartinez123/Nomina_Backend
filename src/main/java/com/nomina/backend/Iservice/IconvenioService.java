package com.nomina.backend.Iservice;

import java.util.List;
import java.util.Optional;

import com.nomina.backend.dto.ConvenioDTO;
import com.nomina.backend.model.Convenio;

public interface IconvenioService {
	public List<ConvenioDTO>listConvenios();
	public Optional<ConvenioDTO> getConvenioById(int id);
	public int saveConvenio(Convenio convenio);
	public boolean deleteConvenio(int id);
	//public List<Convenio> findByNombre(String name);
	//List<ConvenioDTO> listConvenios();
	//Optional<ConvenioDTO> getConvenioById(int id);
	ConvenioDTO createConvenio(ConvenioDTO convenioDTO);
	ConvenioDTO updateConvenio(int id, ConvenioDTO convenioDTO);
}
