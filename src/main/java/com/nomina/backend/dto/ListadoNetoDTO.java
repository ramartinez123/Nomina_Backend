package com.nomina.backend.dto;

public class ListadoNetoDTO {
    private int id;
    private String apellido;
    private String nombre;
    private Integer montoConcepto491;

    // Constructor
    public ListadoNetoDTO(int id, String apellido, String nombre, Integer montoConcepto491) {
        this.id = id;
        this.apellido = apellido;
        this.nombre = nombre;
        this.montoConcepto491 = montoConcepto491;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMontoConcepto491() {
        return montoConcepto491;
    }

    public void setMontoConcepto491(Integer montoConcepto491) {
        this.montoConcepto491 = montoConcepto491;
    }
}