package com.nomina.backend.service;

import com.nomina.backend.Iservice.IconvenioService;
import com.nomina.backend.dto.ConvenioDTO;
import com.nomina.backend.model.Convenio;
import com.nomina.backend.repository.ConvenioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConvenioService implements IconvenioService {

    @Autowired
    private ConvenioRepository convenioRepository;

    @Override
    public List<ConvenioDTO> listConvenios() {
        List<Convenio> convenios = convenioRepository.findAll();
        return convenios.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ConvenioDTO> getConvenioById(int id) {
        Optional<Convenio> convenio = convenioRepository.findById(id);
        return convenio.map(this::convertToDTO);
    }

    @Override
    public ConvenioDTO createConvenio(ConvenioDTO convenioDTO) {
        Convenio convenio = new Convenio();
        convenio.setNombre(convenioDTO.getNombre());
        convenio.setDescripcion(convenioDTO.getDescripcion());

        Convenio savedConvenio = convenioRepository.save(convenio);
        return convertToDTO(savedConvenio);
    }

    @Override
    public ConvenioDTO updateConvenio(int id, ConvenioDTO convenioDTO) {
        Optional<Convenio> existingConvenioOpt = convenioRepository.findById(id);
        if (existingConvenioOpt.isPresent()) {
            Convenio existingConvenio = existingConvenioOpt.get();
            existingConvenio.setNombre(convenioDTO.getNombre());
            existingConvenio.setDescripcion(convenioDTO.getDescripcion());

            Convenio updatedConvenio = convenioRepository.save(existingConvenio);
            return convertToDTO(updatedConvenio);
        }
        throw new RuntimeException("Convenio no encontrado para actualizar");
    }

    private ConvenioDTO convertToDTO(Convenio convenio) {
        return new ConvenioDTO(convenio.getIdConvenio(), convenio.getNombre(), convenio.getDescripcion());
    }

	@Override
	public int saveConvenio(Convenio convenio) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteConvenio(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}