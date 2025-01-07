package com.nomina.backend.dto;

import java.util.Date;

public class NovedadLiquidacionDTO {

    private Integer id;
    private Integer idConcepto;
    private Integer idEmpleado;
    private Date fechaInicio;
    private Integer cantidad;

    // Constructor
    public NovedadLiquidacionDTO(Integer id, Integer idConcepto, Integer idEmpleado, Date fechaInicio, Integer cantidad) {
        this.id = id;
        this.idConcepto = idConcepto;
        this.idEmpleado = idEmpleado;
        this.fechaInicio = fechaInicio;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}