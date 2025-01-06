package com.nomina.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomina.backend.repository.DetalleLiquidacionRepository;
import com.nomina.backend.dto.DetalleLiquidacionDTO;

@Service
public class DetalleLiquidacionCargasSociaelsService {

    @Autowired
    private DetalleLiquidacionRepository repository;	

    public List<DetalleLiquidacionDTO> obtenerLiquidaciones() {
        return repository.obtenerLiquidaciones();
    }
}
