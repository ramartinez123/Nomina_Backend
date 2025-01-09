package com.nomina.backend.service;

import com.nomina.backend.dto.SalarioExcedenteDTO;
import com.nomina.backend.model.SalarioExcedente;
import com.nomina.backend.model.Empleado;
import com.nomina.backend.model.ConceptoSalarial;
import com.nomina.backend.repository.SalarioExcedenteRepository;
import com.nomina.backend.repository.EmpleadoRepository;
import com.nomina.backend.repository.ConceptoSalarialRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalarioExcedenteService {

    private final SalarioExcedenteRepository salarioExcedenteRepository;
    private final EmpleadoRepository empleadoRepository;
    private final ConceptoSalarialRepository conceptoSalarialRepository;

    public SalarioExcedenteService(SalarioExcedenteRepository salarioExcedenteRepository,
                                   EmpleadoRepository empleadoRepository,
                                   ConceptoSalarialRepository conceptoSalarialRepository) {
        this.salarioExcedenteRepository = salarioExcedenteRepository;
        this.empleadoRepository = empleadoRepository;
        this.conceptoSalarialRepository = conceptoSalarialRepository;
    }

    // Crear un Salario Excedente
    public SalarioExcedenteDTO createSalarioExcedente(SalarioExcedenteDTO salarioExcedenteDTO) {
        // Buscar al empleado y concepto en la base de datos
        Optional<Empleado> empleadoOpt = empleadoRepository.findById(salarioExcedenteDTO.getIdEmpleado());
        Optional<ConceptoSalarial> conceptoOpt = conceptoSalarialRepository.findById(salarioExcedenteDTO.getIdConcepto());

        if (empleadoOpt.isPresent() && conceptoOpt.isPresent()) {
            // Si existen, crear el Salario Excedente
            SalarioExcedente salarioExcedente = new SalarioExcedente(
                    empleadoOpt.get(),
                    conceptoOpt.get(),
                    salarioExcedenteDTO.getValor(),
                    salarioExcedenteDTO.getFechaInicio(),
                    salarioExcedenteDTO.getFechaFin() != null ? salarioExcedenteDTO.getFechaFin() : null
            );

            salarioExcedente = salarioExcedenteRepository.save(salarioExcedente);
            return new SalarioExcedenteDTO(
                    salarioExcedente.getId(),
                    salarioExcedente.getEmpleado().getId(),
                    salarioExcedente.getConceptoSalarial().getId(),
                    salarioExcedente.getValor(),
                    salarioExcedente.getFechaInicio(),
                    salarioExcedente.getFechaFin() != null ? salarioExcedente.getFechaFin() : null
            );
        } else {
            throw new RuntimeException("Empleado o concepto salarial no encontrado.");
        }
    }

    // Actualizar un Salario Excedente
    public SalarioExcedenteDTO updateSalarioExcedente(int id, SalarioExcedenteDTO salarioExcedenteDTO) {
        Optional<SalarioExcedente> salarioExcedenteOpt = salarioExcedenteRepository.findById(id);

        if (salarioExcedenteOpt.isPresent()) {
            SalarioExcedente salarioExcedente = salarioExcedenteOpt.get();
            // Actualizar el salario excedente con los nuevos valores
            salarioExcedente.setValor(salarioExcedenteDTO.getValor());
            salarioExcedente.setFechaInicio(salarioExcedenteDTO.getFechaInicio());
            salarioExcedente.setFechaFin(salarioExcedenteDTO.getFechaFin() != null ? salarioExcedenteDTO.getFechaFin() : null);

            Optional<Empleado> empleadoOpt = empleadoRepository.findById(salarioExcedenteDTO.getIdEmpleado());
            Optional<ConceptoSalarial> conceptoOpt = conceptoSalarialRepository.findById(salarioExcedenteDTO.getIdConcepto());

            if (empleadoOpt.isPresent() && conceptoOpt.isPresent()) {
                salarioExcedente.setEmpleado(empleadoOpt.get());
                salarioExcedente.setConceptoSalarial(conceptoOpt.get());
                salarioExcedente = salarioExcedenteRepository.save(salarioExcedente);

                return new SalarioExcedenteDTO(
                        salarioExcedente.getId(),
                        salarioExcedente.getEmpleado().getId(),
                        salarioExcedente.getConceptoSalarial().getId(),
                        salarioExcedente.getValor(),
                        salarioExcedente.getFechaInicio().toString(),
                        salarioExcedente.getFechaFin() != null ? salarioExcedente.getFechaFin().toString() : null
                );
            } else {
                throw new RuntimeException("Empleado o concepto salarial no encontrado.");
            }
        } else {
            throw new RuntimeException("Salario Excedente no encontrado.");
        }
    }

    // Listar todos los Salarios Excedentes
    public List<SalarioExcedenteDTO> getAllSalariosExcedentes() {
        List<SalarioExcedente> salariosExcedentes = salarioExcedenteRepository.findAll();
        return salariosExcedentes.stream()
                .map(salarioExcedente -> new SalarioExcedenteDTO(
                        salarioExcedente.getId(),
                        salarioExcedente.getEmpleado().getId(),
                        salarioExcedente.getConceptoSalarial().getId(),
                        salarioExcedente.getValor(),
                        salarioExcedente.getFechaInicio(),
                        salarioExcedente.getFechaFin() != null ? salarioExcedente.getFechaFin() : null
                ))
                .collect(Collectors.toList());
    }

	public SalarioExcedenteDTO getSalarioExcedenteById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}