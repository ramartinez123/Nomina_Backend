package com.nomina.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nomina.backend.dto.HistoricoValoresCategoriaDTO;
import com.nomina.backend.model.Categoria;
import com.nomina.backend.model.HistoricoValoresCategoria;
import com.nomina.backend.repository.CategoriaRepository;
import com.nomina.backend.repository.HistoricoValoresCategoriaRepository;

@Service
public class HistoricoValoresCategoriaService {
    private final HistoricoValoresCategoriaRepository historicoRepository;
    private final CategoriaRepository categoriaRepository;

    public HistoricoValoresCategoriaService(HistoricoValoresCategoriaRepository historicoRepository,
                                            CategoriaRepository categoriaRepository) {
        this.historicoRepository = historicoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // Crear o Actualizar
    public HistoricoValoresCategoriaDTO save(HistoricoValoresCategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        HistoricoValoresCategoria historico = dto.getId() != null
                ? historicoRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Histórico no encontrado"))
                : new HistoricoValoresCategoria();

        historico.setCategoria(categoria);
        historico.setFechaInicio(dto.getFechaInicio());
        historico.setFechaBaja(dto.getFechaBaja() != null ? dto.getFechaBaja() : null);
        historico.setSalario(dto.getSalario());
        historico.setAlmuerzo(dto.getAlmuerzo());

        HistoricoValoresCategoria savedHistorico = historicoRepository.save(historico);
        return mapToDTO(savedHistorico);
    }

    // Obtener por ID
    public HistoricoValoresCategoriaDTO getById(Integer id) {
        HistoricoValoresCategoria historico = historicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Histórico no encontrado"));
        return mapToDTO(historico);
    }

    // Eliminar
    public void deleteById(Integer id) {
        if (!historicoRepository.existsById(id)) {
            throw new RuntimeException("Histórico no encontrado");
        }
        historicoRepository.deleteById(id);
    }

    // Listar todos
    public List<HistoricoValoresCategoriaDTO> getAll() {
        return historicoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Mapear a DTO
    private HistoricoValoresCategoriaDTO mapToDTO(HistoricoValoresCategoria historico) {
        HistoricoValoresCategoriaDTO dto = new HistoricoValoresCategoriaDTO();
        dto.setId(historico.getId());
        dto.setIdCategoria(historico.getCategoriaId());
        dto.setFechaInicio(historico.getFechaInicio());  // Directo sin conversión
        dto.setFechaBaja(historico.getFechaBaja());  // Directo sin conversión
        dto.setSalario(historico.getSalario());
        dto.setAlmuerzo(historico.getAlmuerzo());
        return dto;
    }
}