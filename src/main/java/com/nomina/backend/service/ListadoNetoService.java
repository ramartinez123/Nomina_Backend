package com.nomina.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomina.backend.dto.LiquidacionDetalleDTO;
import com.nomina.backend.repository.DetalleLiquidacionRepository;

@Service
public class ListadoNetoService {
	
	@Autowired
    private DetalleLiquidacionRepository detalleLiquidacionRepository;

    public List<LiquidacionDetalleDTO> obtenerLiquidacionesPorConcepto491(int mes, int anio) {
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
        }
        if (anio < 1900 || anio > 2100) {
            throw new IllegalArgumentException("El año debe estar en un rango razonable (1900-2100).");
        }
        return detalleLiquidacionRepository.findByConcepto491AndMesAndAnio(mes, anio);
    }
}

