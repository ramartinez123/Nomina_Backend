package com.nomina.backend.dto;


public class ConvenioDTO {
    private Integer idConvenio;
    private String nombre;
    private String descripcion;

    // Constructor vacío
    public ConvenioDTO() {}

    // Constructor con parámetros
    public ConvenioDTO(Integer idConvenio, String nombre, String descripcion) {
        this.idConvenio = idConvenio;
        this.nombre = nombre;
        this.descripcion = descripcion;
     }

    // Getters y Setters
    public Integer getIdConvenio() {
        return idConvenio;
    }

    public void setIdConvenio(Integer idConvenio) {
        this.idConvenio = idConvenio;
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