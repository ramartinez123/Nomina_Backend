package com.nomina.backend.Iservice;

import java.util.List;
import java.util.Optional;

import com.nomina.backend.model.Banco;

	public interface IbancoService {
		public List<Banco>listBanco();
		public Optional<Banco> listIdBanco(int id);
		public int saveBanco(Banco banco);
		public boolean deleteBanco(int id);
		public List<Banco> findByNombre(String name);
	}
