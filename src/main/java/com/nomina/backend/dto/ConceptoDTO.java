package com.nomina.backend.dto;

public class ConceptoDTO {
    private int conceptoId;
    private String nombre;
    private int monto;

    public ConceptoDTO(int conceptoId, String nombre, int monto) {
        this.setConceptoId(conceptoId);
        this.setNombre(nombre);
        this.setMonto(monto);
    }

	public int getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(int conceptoId) {
		this.conceptoId = conceptoId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getMonto() {
		return monto;
	}

	public void setMonto(int monto) {
		this.monto = monto;
	}

  }