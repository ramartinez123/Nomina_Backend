package com.nomina.backend.service;

import com.nomina.backend.dto.NovedadLiquidacionDTO;
import com.nomina.backend.model.NovedadLiquidacion;
import com.nomina.backend.model.ConceptoSalarial;
import com.nomina.backend.model.Empleado;
import com.nomina.backend.repository.NovedadLiquidacionRepository;
import com.nomina.backend.repository.ConceptoSalarialRepository;
import com.nomina.backend.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NovedadLiquidacionService {

    @Autowired
    private NovedadLiquidacionRepository novedadLiquidacionRepository;

    @Autowired
    private ConceptoSalarialRepository conceptoSalarialRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Obtener todas las novedades
    public List<NovedadLiquidacionDTO> getAllNovedades() {
        List<NovedadLiquidacion> novedades = novedadLiquidacionRepository.findAll();
        
        // Convertir las entidades a DTOs
        return novedades.stream().map(novedad -> new NovedadLiquidacionDTO(
            novedad.getId(),
            novedad.getConcepto().getId(),  // Obtener el ID de ConceptoSalarial
            novedad.getEmpleado().getId(),          // Obtener el ID de Empleado
            novedad.getFechaInicio(),
            novedad.getCantidad()
        )).collect(Collectors.toList());
    }

    // Obtener una novedad por su ID
    public NovedadLiquidacionDTO getNovedadById(Integer id) {
        Optional<NovedadLiquidacion> novedad = novedadLiquidacionRepository.findById(id);
        
        if (novedad.isPresent()) {
            NovedadLiquidacion n = novedad.get();
            return new NovedadLiquidacionDTO(
                n.getId(),
                n.getConcepto().getId(),
                n.getEmpleado().getId(),
                n.getFechaInicio(),
                n.getCantidad()
            );
        } else {
            throw new RuntimeException("Novedad no encontrada con ID " + id);
        }
    }

    // Crear una nueva novedad de liquidación

    public NovedadLiquidacionDTO createNovedad(NovedadLiquidacionDTO novedadDTO) {
        // Buscar las entidades relacionadas
        ConceptoSalarial conceptoSalarial = conceptoSalarialRepository.findById(novedadDTO.getIdConcepto())
                .orElseThrow(() -> new RuntimeException("Concepto salarial no encontrado"));
        Empleado empleado = empleadoRepository.findById(novedadDTO.getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // Crear la entidad NovedadLiquidacion
        NovedadLiquidacion novedad = new NovedadLiquidacion();
        novedad.setConcepto(conceptoSalarial);
        novedad.setEmpleado(empleado);
        novedad.setFechaInicio(novedadDTO.getFechaInicio());
        novedad.setCantidad(novedadDTO.getCantidad());

        // Guardar la novedad
        NovedadLiquidacion savedNovedad = novedadLiquidacionRepository.save(novedad);
        
        // Retornar el DTO con los datos de la novedad recién creada
        return new NovedadLiquidacionDTO(
            savedNovedad.getId(),
            savedNovedad.getConcepto().getId(),
            savedNovedad.getEmpleado().getId(),
            savedNovedad.getFechaInicio(),
            savedNovedad.getCantidad()
        );
    }

    // Actualizar una novedad de liquidación
  
    public NovedadLiquidacionDTO updateNovedad(Integer id, NovedadLiquidacionDTO novedadDTO) {
        Optional<NovedadLiquidacion> existingNovedad = novedadLiquidacionRepository.findById(id);

        if (existingNovedad.isPresent()) {
            NovedadLiquidacion novedad = existingNovedad.get();
            
            // Buscar las entidades relacionadas
            ConceptoSalarial conceptoSalarial = conceptoSalarialRepository.findById(novedadDTO.getIdConcepto())
                    .orElseThrow(() -> new RuntimeException("Concepto salarial no encontrado"));
            Empleado empleado = empleadoRepository.findById(novedadDTO.getIdEmpleado())
                   .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

            // Actualizar la entidad NovedadLiquidacion
            novedad.setConcepto(conceptoSalarial);
            novedad.setEmpleado(empleado);
            novedad.setFechaInicio(novedadDTO.getFechaInicio());
            novedad.setCantidad(novedadDTO.getCantidad());

            // Guardar la novedad actualizada
            NovedadLiquidacion updatedNovedad = novedadLiquidacionRepository.save(novedad);
            
            return new NovedadLiquidacionDTO(
                updatedNovedad.getId(),
                updatedNovedad.getConcepto().getId(),
                updatedNovedad.getEmpleado().getId(),
                updatedNovedad.getFechaInicio(),
                updatedNovedad.getCantidad()
            );
        } else {
            throw new RuntimeException("Novedad no encontrada con ID " + id);
        }
    }

    // Eliminar una novedad de liquidación

    
    public void deleteNovedad(Integer id) {
        Optional<NovedadLiquidacion> existingNovedad = novedadLiquidacionRepository.findById(id);
        
        if (existingNovedad.isPresent()) {
            novedadLiquidacionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Novedad no encontrada con ID " + id);
        }
    }
}