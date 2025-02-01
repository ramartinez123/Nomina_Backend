package com.nomina.backend.service;

import com.nomina.backend.dto.FamiliarDTO;
import com.nomina.backend.model.Empleado;
import com.nomina.backend.model.Familiar;
import com.nomina.backend.repository.EmpleadoRepository;
import com.nomina.backend.repository.FamiliarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FamiliarService {

    @Autowired
    private FamiliarRepository familiarRepository;
   
    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<FamiliarDTO> getAllFamiliares() {
        List<Familiar> familiares = familiarRepository.findAll();
        return familiares.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public FamiliarDTO getFamiliarById(int id) {
        Familiar familiar = familiarRepository.findById(id).orElseThrow(() -> new RuntimeException("Familiar not found"));
        return convertToDTO(familiar);
    }

    @Transactional
    public FamiliarDTO createFamiliar(FamiliarDTO familiarDTO) {
        Familiar familiar = new Familiar();
        // Asumimos que los valores ya vienen del DTO, si fuera necesario se podría añadir validación
        familiar.setIdParentesco(familiarDTO.getIdParentesco());
        Empleado empleado = empleadoRepository.findById(familiarDTO.getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));  // Maneja la excepción si no se encuentra el emplead*/o
        familiar.setEmpleado(empleado);
        familiar.setApellidoNombre(familiarDTO.getApellidoNombre());
        familiar.setFechaNacimiento(familiarDTO.getFechaNacimiento());
        familiar.setFechaInicio(familiarDTO.getFechaInicio());
        familiar.setFechaFin(familiarDTO.getFechaFin());
        familiar.setACargo(familiarDTO.isACargo());
        familiar.setACargoOSocial(familiarDTO.isACargoOSocial());
        familiar.setTieneDiscapacidad(familiarDTO.isTieneDiscapacidad());

        // Guardamos el nuevo familiar
        familiarRepository.save(familiar);

        return convertToDTO(familiar);
    }

    @Transactional
    public FamiliarDTO updateFamiliar(int id, FamiliarDTO familiarDTO) {
        Familiar familiar = familiarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Familiar not found"));
        
        // Actualizamos los valores
        familiar.setIdParentesco(familiarDTO.getIdParentesco());
        familiar.setApellidoNombre(familiarDTO.getApellidoNombre());
        familiar.setFechaNacimiento(familiarDTO.getFechaNacimiento());
        familiar.setFechaInicio(familiarDTO.getFechaInicio());
        familiar.setFechaFin(familiarDTO.getFechaFin());
        familiar.setACargo(familiarDTO.isACargo());
        familiar.setACargoOSocial(familiarDTO.isACargoOSocial());
        familiar.setTieneDiscapacidad(familiarDTO.isTieneDiscapacidad());

        // Guardamos los cambios
        familiarRepository.save(familiar);

        return convertToDTO(familiar);
    }

    private FamiliarDTO convertToDTO(Familiar familiar) {
        FamiliarDTO familiarDTO = new FamiliarDTO();
        familiarDTO.setId(familiar.getId());
        familiarDTO.setIdParentesco(familiar.getIdParentesco());
        
        // Verificamos si el empleado no es nulo antes de acceder a sus propiedades
        if (familiar.getEmpleado() != null) {
            familiarDTO.setIdEmpleado(familiar.getEmpleado().getId());  // Asegúrate de obtener el ID del empleado
        } else {
            familiarDTO.setIdEmpleado(null);  // En caso de que no haya empleado asociado
        }
        
        familiarDTO.setApellidoEm(familiar.getEmpleado().getApellido()	);
        familiarDTO.setApellidoNombre(familiar.getApellidoNombre());
        familiarDTO.setFechaNacimiento(familiar.getFechaNacimiento());
        familiarDTO.setFechaInicio(familiar.getFechaInicio());
        familiarDTO.setFechaFin(familiar.getFechaFin());
        familiarDTO.setACargo(familiar.getACargo());
        familiarDTO.setACargoOSocial(familiar.getACargoOSocial());
        familiarDTO.setTieneDiscapacidad(familiar.isTieneDiscapacidad());
        
        return familiarDTO;
}}