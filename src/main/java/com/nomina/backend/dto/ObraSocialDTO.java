package com.nomina.backend.dto;

public class ObraSocialDTO {

    private Integer idObraSocial;
    private String nombre;
    private String descripcion;
    
    public ObraSocialDTO(Integer idObraSocial, String nombre, String descripcion) {
        this.idObraSocial = idObraSocial;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Integer getIdObraSocial() {
        return idObraSocial;
    }

    public void setIdObraSocial(Integer idObraSocial) {
        this.idObraSocial = idObraSocial;
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