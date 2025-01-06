package com.nomina.backend.dto;

public class EmpleadoDTO2 {
    private int id;
    private String nombre;

    public EmpleadoDTO2(int id, String nombre) {
        this.setId(id);
        this.setNombre(nombre);
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

    // Getters y Setters
}