package com.nomina.backend.service;

import com.nomina.backend.Iservice.IdepartamentoService;
import com.nomina.backend.dto.DepartamentoDTO;
import com.nomina.backend.model.Departamento;
import com.nomina.backend.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartamentoService implements IdepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Override
    public List<DepartamentoDTO> listDepartamento() {
        List<Departamento> departamentos = departamentoRepository.findAll();
        return departamentos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DepartamentoDTO> getDepartamentoById(int id) {
        Optional<Departamento> departamento = departamentoRepository.findById(id);
        return departamento.map(this::convertToDTO);
    }

    @Override
    public DepartamentoDTO createDepartamento(DepartamentoDTO departamentoDTO) {
        Departamento departamento = new Departamento();
        departamento.setNombre(departamentoDTO.getNombre());
        departamento.setDescripcion(departamentoDTO.getDescripcion());

        Departamento savedDepartamento = departamentoRepository.save(departamento);
        return convertToDTO(savedDepartamento);
    }

    @Override
    public DepartamentoDTO updateDepartamento(int id, DepartamentoDTO departamentoDTO) {
        Optional<Departamento> existingDepartamentoOpt = departamentoRepository.findById(id);
        if (existingDepartamentoOpt.isPresent()) {
            Departamento existingDepartamento = existingDepartamentoOpt.get();
            existingDepartamento.setNombre(departamentoDTO.getNombre());
            existingDepartamento.setDescripcion(departamentoDTO.getDescripcion());

            Departamento updatedDepartamento = departamentoRepository.save(existingDepartamento);
            return convertToDTO(updatedDepartamento);
        }
        throw new RuntimeException("Departamento no encontrado para actualizar");
    }

    private DepartamentoDTO convertToDTO(Departamento departamento) {
        return new DepartamentoDTO(departamento.getIdDepartamento(), departamento.getNombre(), departamento.getDescripcion());
    }

	@Override
	public boolean deleteDepartamento(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<DepartamentoDTO> findByNombre(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}