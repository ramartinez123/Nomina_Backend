package com.nomina.backend.dto;

public class ProvinciaDTO {

    private Integer idProvincia;
    private String nombre;
    
    public ProvinciaDTO(Integer idProvincia, String nombre) {
        this.idProvincia = idProvincia;
        this.nombre = nombre;
    }

    // Constructor vacío
    public ProvinciaDTO() {}

    // Getters y Setters
    public Integer getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}