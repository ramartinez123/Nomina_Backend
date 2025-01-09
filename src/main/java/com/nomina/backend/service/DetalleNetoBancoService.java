package com.nomina.backend.service;

import com.nomina.backend.dto.EmpleadoAgrupadoDTO;
import com.nomina.backend.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DetalleNetoBancoService {

	@Autowired
    private EmpleadoRepository empleadoRepository;

    public List<EmpleadoAgrupadoDTO> obtenerAgrupadosPorBanco(int mes, int anio) {
        // Validaciones para mes y año
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
        }
        if (anio < 1900 || anio > 2100) {
            throw new IllegalArgumentException("El año debe estar en un rango razonable (1900-2100).");
        }

        return empleadoRepository.agrupadosPorBanco(mes, anio);
    }
}

