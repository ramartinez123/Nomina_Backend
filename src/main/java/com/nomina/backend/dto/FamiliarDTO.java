package com.nomina.backend.dto;

import java.util.Date;

public class FamiliarDTO {

    private int id;
    private String idParentesco;
    private Integer idEmpleado;
    private String apellidoEm;
    private String apellidoNombre;
    private Date fechaNacimiento;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean aCargo;
    private boolean aCargoOSocial;
    private boolean tieneDiscapacidad;

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdParentesco() {
        return idParentesco;
    }

    public void setIdParentesco(String idParentesco) {
        this.idParentesco = idParentesco;
    }

    public String getApellidoNombre() {
        return apellidoNombre;
    }

    public void setApellidoNombre(String apellidoNombre) {
        this.apellidoNombre = apellidoNombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isACargo() {
        return aCargo;
    }

    public void setACargo(boolean aCargo) {
        this.aCargo = aCargo;
    }

    public boolean isACargoOSocial() {
        return aCargoOSocial;
    }

    public void setACargoOSocial(boolean aCargoOSocial) {
        this.aCargoOSocial = aCargoOSocial;
    }

    public boolean isTieneDiscapacidad() {
        return tieneDiscapacidad;
    }

    public void setTieneDiscapacidad(boolean tieneDiscapacidad) {
        this.tieneDiscapacidad = tieneDiscapacidad;
    }

	public Integer getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Integer idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getApellidoEm() {
		return apellidoEm;
	}

	public void setApellidoEm(String apellidoEm) {
		this.apellidoEm = apellidoEm;
	}
}