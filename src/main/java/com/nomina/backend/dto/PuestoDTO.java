package com.nomina.backend.dto;

public class PuestoDTO {

    private Integer idPuesto;
    private String nombre;
    private String descripcion;

    public PuestoDTO(Integer idPuesto, String nombre, String descripcion) {
        this.idPuesto = idPuesto;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Constructor vacío
    public PuestoDTO() {}

    // Getters y Setters
    public Integer getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(Integer idPuesto) {
        this.idPuesto = idPuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}