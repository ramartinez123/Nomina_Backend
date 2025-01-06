package com.nomina.backend.dto;

public class EmpleadoAgrupadoDTO {
    private String banco;
    private String cbu;
    private String apellido;
    private String nombre;
    private Integer monto;

    public EmpleadoAgrupadoDTO(String banco, String cbu, String apellido, String nombre, Integer monto) {
        this.banco = banco;
        this.cbu = cbu;
        this.apellido = apellido;
        this.nombre = nombre;
        this.monto = monto;
    }

    // Getters y Setters
    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
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

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }
}