package com.nomina.backend.service;

import com.nomina.backend.Iservice.IobraSocialService;
import com.nomina.backend.dto.ObraSocialDTO;
import com.nomina.backend.model.ObraSocial;
import com.nomina.backend.repository.ObraSocialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ObraSocialService implements IobraSocialService {

    @Autowired
    private ObraSocialRepository obraSocialRepository;

    @Override
    public List<ObraSocialDTO> listObraSocial() {
        List<ObraSocial> obrasSociales = obraSocialRepository.findAll();
        return obrasSociales.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ObraSocialDTO> findById(Integer id) {
        Optional<ObraSocial> obraSocial = obraSocialRepository.findById(id);
        return obraSocial.map(this::convertToDTO);
    }

    @Override
    public ObraSocialDTO createObraSocial(ObraSocialDTO obraSocialDTO) {
        ObraSocial obraSocial = new ObraSocial();
        obraSocial.setNombre(obraSocialDTO.getNombre());
        obraSocial.setDescripcion(obraSocialDTO.getDescripcion());

        ObraSocial savedObraSocial = obraSocialRepository.save(obraSocial);
        return convertToDTO(savedObraSocial);
    }

    @Override
    public ObraSocialDTO updateObraSocial(int id, ObraSocialDTO obraSocialDTO) {
        Optional<ObraSocial> existingObraSocialOpt = obraSocialRepository.findById(id);
        if (existingObraSocialOpt.isPresent()) {
            ObraSocial existingObraSocial = existingObraSocialOpt.get();
            existingObraSocial.setNombre(obraSocialDTO.getNombre());
            existingObraSocial.setDescripcion(obraSocialDTO.getDescripcion());

            ObraSocial updatedObraSocial = obraSocialRepository.save(existingObraSocial);
            return convertToDTO(updatedObraSocial);
        }
        throw new RuntimeException("Obra Social no encontrada para actualizar");
    }

    @Override
    public boolean deleteObraSocial(int id) {
        if (obraSocialRepository.existsById(id)) {
            obraSocialRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ObraSocialDTO convertToDTO(ObraSocial obraSocial) {
        return new ObraSocialDTO(
                obraSocial.getIdObraSocial(),
                obraSocial.getNombre(),
                obraSocial.getDescripcion()
        );
    }

	@Override
	public List<ObraSocial> findByNombre(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}