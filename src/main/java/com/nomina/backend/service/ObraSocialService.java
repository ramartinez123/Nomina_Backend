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

 
    public List<ObraSocialDTO> listarObraSocial() {
        List<ObraSocial> bancos = (List<ObraSocial>) obraSocialRepository.findAll();
        return bancos.stream()
                     .map(this::convertToDTO)
                     .collect(Collectors.toList());
    }

    // Obtener un banco por ID
    public Optional<ObraSocialDTO> getObraSocialById(int id) {
        return obraSocialRepository.findById(id).map(this::convertToDTO);
    }
    
     public ObraSocialDTO createObraSocial(ObraSocialDTO obraSocialDTO) {
        ObraSocial obraSocial = new ObraSocial();
        obraSocial.setNombre(obraSocialDTO.getNombre());
        obraSocial.setDescripcion(obraSocialDTO.getDescripcion());

        ObraSocial savedObraSocial = obraSocialRepository.save(obraSocial);
        return convertToDTO(savedObraSocial);
    }

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
	public List<ObraSocial> listObraSocial() {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<ObraSocialDTO> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}