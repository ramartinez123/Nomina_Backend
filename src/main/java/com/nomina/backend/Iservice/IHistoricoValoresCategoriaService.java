package com.nomina.backend.Iservice;

import java.util.List;
import java.util.Optional;
import com.nomina.backend.model.HistoricoValoresCategoria;

public interface IHistoricoValoresCategoriaService {
	//public List<HistoricoValoresCategoria>listHistoricoValoresCategoria();
	public Optional<HistoricoValoresCategoria> findById(Integer id);
	//public int saveHistoricoValoresCategoria(HistoricoValoresCategoria historicoValoresCategoria);
	//public boolean deleteHistoricoValoresCategoria(int id);
}

