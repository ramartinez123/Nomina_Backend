package com.nomina.backend.service;

import com.nomina.backend.dto.ConceptoSalarialDTO;
import com.nomina.backend.dto.ConceptoSalarialDTO2;
import com.nomina.backend.model.ConceptoSalarial;
import com.nomina.backend.repository.ConceptoSalarialRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConceptoSalarialService {

    @Autowired
    private ConceptoSalarialRepository conceptoSalarialRepository;

    // Método para listar todos los conceptos salariales (con DTO simplificado - DTO2)
    public List<ConceptoSalarialDTO2> listarConceptos() {
        List<ConceptoSalarial> conceptos = conceptoSalarialRepository.findAll();
        
        // Convertir las entidades a DTOs simplificados
        return conceptos.stream().map(concepto -> new ConceptoSalarialDTO2(
                concepto.getId(),
                concepto.getNombre(),
                concepto.getTipo(),
                concepto.getCuentaContable()
        )).collect(Collectors.toList());
    }


    // Método para crear un nuevo concepto salarial (con DTO completo - DTO)
    @Transactional
    public ConceptoSalarial crearConcepto(ConceptoSalarialDTO conceptoSalarialDTO) {
        ConceptoSalarial concepto = new ConceptoSalarial(
                conceptoSalarialDTO.getNombre(),
                conceptoSalarialDTO.getDescripcion(),
                conceptoSalarialDTO.getTipo(),
                conceptoSalarialDTO.getImpAportes(),
                conceptoSalarialDTO.getImpGanancias(),
                conceptoSalarialDTO.getImpIndemnizacion(),
                conceptoSalarialDTO.getImpSac(),
                conceptoSalarialDTO.getSueldoTotal(),
                conceptoSalarialDTO.getTipoValor(),
                conceptoSalarialDTO.getValor(),
                conceptoSalarialDTO.getCuentaContable(),
                conceptoSalarialDTO.getFormula(),
                conceptoSalarialDTO.getFechaInicio(),
                conceptoSalarialDTO.getFechaFin(),
                null, // DetalleLiquidaciones
                null, // SalarioExcedente
                null  // NovedadLiquidaciones
        );
        return conceptoSalarialRepository.save(concepto);
    }

    // Método para actualizar un concepto salarial existente (con DTO completo - DTO)
    @Transactional
    public ConceptoSalarial actualizarConcepto(Integer id, ConceptoSalarialDTO conceptoSalarialDTO) {
        // Verificar si el concepto salarial existe
        ConceptoSalarial concepto = conceptoSalarialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concepto salarial no encontrado"));

        // Actualizar solo los campos que no son nulos en el DTO
        if (conceptoSalarialDTO.getNombre() != null) {
            concepto.setNombre(conceptoSalarialDTO.getNombre());
        }
        if (conceptoSalarialDTO.getDescripcion() != null) {
            concepto.setDescripcion(conceptoSalarialDTO.getDescripcion());
        }
        if (conceptoSalarialDTO.getTipo() != null) {
            concepto.setTipo(conceptoSalarialDTO.getTipo());
        }
        if (conceptoSalarialDTO.getImpAportes() != null) {
            concepto.setImpAportes(conceptoSalarialDTO.getImpAportes());
        }
        if (conceptoSalarialDTO.getImpGanancias() != null) {
            concepto.setImpGanancias(conceptoSalarialDTO.getImpGanancias());
        }
        if (conceptoSalarialDTO.getImpIndemnizacion() != null) {
            concepto.setImpIndemnizacion(conceptoSalarialDTO.getImpIndemnizacion());
        }
        if (conceptoSalarialDTO.getImpSac() != null) {
            concepto.setImpSac(conceptoSalarialDTO.getImpSac());
        }
        if (conceptoSalarialDTO.getSueldoTotal() != null) {
            concepto.setSueldoTotal(conceptoSalarialDTO.getSueldoTotal());
        }
        if (conceptoSalarialDTO.getTipoValor() != null) {
            concepto.setTipoValor(conceptoSalarialDTO.getTipoValor());
        }
        if (conceptoSalarialDTO.getValor() != null) {
            concepto.setValor(conceptoSalarialDTO.getValor());
        }
        if (conceptoSalarialDTO.getCuentaContable() != null) {
            concepto.setCuentaContable(conceptoSalarialDTO.getCuentaContable());
        }
        if (conceptoSalarialDTO.getFormula() != null) {
            concepto.setFormula(conceptoSalarialDTO.getFormula());
        }
        if (conceptoSalarialDTO.getFechaInicio() != null) {
            concepto.setFechaInicio(conceptoSalarialDTO.getFechaInicio());
        }
        if (conceptoSalarialDTO.getFechaFin() != null) {
            concepto.setFechaFin(conceptoSalarialDTO.getFechaFin());
        }

        return conceptoSalarialRepository.save(concepto);
    }
}