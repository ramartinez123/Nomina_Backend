package com.nomina.backend.dto;

public class BancoDTO {
    private Integer idBanco;
    private String nombre;
    private String descripcion;

    // Constructor vacío
    public BancoDTO() {}

    // Constructor con parámetros
    public BancoDTO(Integer idBanco, String nombre, String descripcion) {
        this.idBanco = idBanco;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
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

