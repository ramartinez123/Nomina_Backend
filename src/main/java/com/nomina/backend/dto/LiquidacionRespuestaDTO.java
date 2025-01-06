package com.nomina.backend.dto;

import java.util.List;

public class LiquidacionRespuestaDTO {
    private String periodo;
    private List<EmpleadoDetalleDTO> detalles;

    public LiquidacionRespuestaDTO(String periodo, List<EmpleadoDetalleDTO> detalles) {
        this.setPeriodo(periodo);
        this.setDetalles(detalles);
    }

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public List<EmpleadoDetalleDTO> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<EmpleadoDetalleDTO> detalles) {
		this.detalles = detalles;
	}

    // Getters y Setters
}