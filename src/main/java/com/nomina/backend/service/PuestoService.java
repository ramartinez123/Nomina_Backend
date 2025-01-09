package com.nomina.backend.service;

import com.nomina.backend.Iservice.IpuestoService;
import com.nomina.backend.dto.PuestoDTO;
import com.nomina.backend.model.Puesto;
import com.nomina.backend.repository.PuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PuestoService implements IpuestoService {

    @Autowired
    private PuestoRepository puestoRepository;

    @Override
    public List<PuestoDTO> listPuestos() {
        List<Puesto> puestos = puestoRepository.findAll();
        return puestos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PuestoDTO> getPuestoById(int id) {
        Optional<Puesto> puesto = puestoRepository.findById(id);
        return puesto.map(this::convertToDTO);
    }

    @Override
    public PuestoDTO createPuesto(PuestoDTO puestoDTO) {
        Puesto puesto = new Puesto();
        puesto.setNombre(puestoDTO.getNombre());
        puesto.setDescripcion(puestoDTO.getDescripcion());

        Puesto savedPuesto = puestoRepository.save(puesto);
        return convertToDTO(savedPuesto);
    }

    @Override
    public PuestoDTO updatePuesto(int id, PuestoDTO puestoDTO) {
        Optional<Puesto> existingPuestoOpt = puestoRepository.findById(id);
        if (existingPuestoOpt.isPresent()) {
            Puesto existingPuesto = existingPuestoOpt.get();
            existingPuesto.setNombre(puestoDTO.getNombre());
            existingPuesto.setDescripcion(puestoDTO.getDescripcion());

            Puesto updatedPuesto = puestoRepository.save(existingPuesto);
            return convertToDTO(updatedPuesto);
        }
        throw new RuntimeException("Puesto no encontrado para actualizar");
    }

    private PuestoDTO convertToDTO(Puesto puesto) {
        return new PuestoDTO(puesto.getIdPuesto(), puesto.getNombre(), puesto.getDescripcion());
    }

	@Override
	public List<Puesto> listPuesto() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Puesto> findById(Integer idPuesto) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
}