package com.nomina.backend.Iservice;

import java.util.List;
import java.util.Optional;

import com.nomina.backend.dto.BancoDTO;
import com.nomina.backend.model.Banco;

public interface IbancoService {
	public List<Banco> listBanco();

	public Optional<Banco> findById(Integer id);

	public List<Banco> findByNombre(String name);

	public BancoDTO updateBanco(int id, BancoDTO bancoDTO);

	public BancoDTO createBanco(BancoDTO bancoDTO);
}
