package com.nomina.backend.service;

import com.nomina.backend.Iservice.IprovinciaService;
import com.nomina.backend.dto.ProvinciaDTO;
import com.nomina.backend.model.Provincia;
import com.nomina.backend.repository.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProvinciaService implements IprovinciaService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    // Listar todas las provincias
    public List<ProvinciaDTO> listarProvincias() {
        List<Provincia> provincias = (List<Provincia>) provinciaRepository.findAll();
        return provincias.stream()
                         .map(this::convertToDTO)
                         .collect(Collectors.toList());
    }

    // Obtener una provincia por ID
    public Optional<ProvinciaDTO> getProvinciaById(int id) {
        return provinciaRepository.findById(id).map(this::convertToDTO);
    }

    // Crear una nueva provincia
    public ProvinciaDTO createProvincia(ProvinciaDTO provinciaDTO) {
        Provincia provincia = new Provincia();
        provincia.setNombre(provinciaDTO.getNombre());

        Provincia provinciaGuardada = provinciaRepository.save(provincia);
        return convertToDTO(provinciaGuardada);
    }

    // Actualizar una provincia
    public ProvinciaDTO updateProvincia(int id, ProvinciaDTO provinciaDTO) {
        Optional<Provincia> provinciaExistente = provinciaRepository.findById(id);
        if (provinciaExistente.isPresent()) {
            Provincia provincia = provinciaExistente.get();
            provincia.setNombre(provinciaDTO.getNombre());

            Provincia provinciaActualizada = provinciaRepository.save(provincia);
            return convertToDTO(provinciaActualizada);
        } else {
            throw new RuntimeException("Provincia no encontrada");
        }
    }

    // Convertir de Provincia a ProvinciaDTO
    private ProvinciaDTO convertToDTO(Provincia provincia) {
        return new ProvinciaDTO(provincia.getIdProvincia(), provincia.getNombre());
    }


}