package com.nomina.backend.Iservice;

import java.util.List;
import java.util.Optional;
import com.nomina.backend.model.Categoria;

public interface IcategoriaService {
	public List<Categoria>listCategoria();
	public Optional<Categoria> findById(Integer id);
	public int saveCategoria(Categoria categoria);
	public boolean deleteCategoria(int id);
	public List<Categoria> findByNombre(String name);
}
