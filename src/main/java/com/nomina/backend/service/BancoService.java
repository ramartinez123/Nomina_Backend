package com.nomina.backend.service;

import com.nomina.backend.Iservice.IbancoService;
import com.nomina.backend.dto.BancoDTO;
import com.nomina.backend.model.Banco;
import com.nomina.backend.repository.BancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BancoService implements IbancoService { 

    @Autowired
    private BancoRepository bancoRepository;

    // Listar todos los bancos
    public List<BancoDTO> listarBancos() {
        List<Banco> bancos = (List<Banco>) bancoRepository.findAll();
        return bancos.stream()
                     .map(this::convertToDTO)
                     .collect(Collectors.toList());
    }

    // Obtener un banco por ID
    public Optional<BancoDTO> getBancoById(int id) {
        return bancoRepository.findById(id).map(this::convertToDTO);
    }

    // Crear un nuevo banco
    public BancoDTO createBanco(BancoDTO bancoDTO) {
        Banco banco = new Banco();
        banco.setNombre(bancoDTO.getNombre());
        banco.setDescripcion(bancoDTO.getDescripcion());

        Banco bancoGuardado = bancoRepository.save(banco);
        return convertToDTO(bancoGuardado);
    }

    // Actualizar un banco
    public BancoDTO updateBanco(int id, BancoDTO bancoDTO) {
        Optional<Banco> bancoExistente = bancoRepository.findById(id);
        if (bancoExistente.isPresent()) {
            Banco banco = bancoExistente.get();
            banco.setNombre(bancoDTO.getNombre());
            banco.setDescripcion(bancoDTO.getDescripcion());

            Banco bancoActualizado = bancoRepository.save(banco);
            return convertToDTO(bancoActualizado);
        } else {
            throw new RuntimeException("Banco no encontrado");
        }
    }

    // Convertir de Banco a BancoDTO
    private BancoDTO convertToDTO(Banco banco) {
        return new BancoDTO(banco.getIdBanco(), banco.getNombre(), banco.getDescripcion());
    }

	@Override
	public List<Banco> listBanco() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Banco> findById(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Banco> findByNombre(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}