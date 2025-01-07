package com.nomina.backend.dto;

import java.util.Date;

import com.nomina.backend.model.SalarioExcedente;

public class SalarioExcedenteDTO {

    private Integer id;          // Si es necesario para la actualización
    private Integer idEmpleado;  // ID del empleado
    private Integer idConcepto;  // ID del concepto salarial
    private Integer valor;       // El valor del salario excedente
    private Date fechaInicio;  // Fecha de inicio
    private Date fechaFin;     // Fecha de fin (puede ser null)

    // Constructor vacío
    //public SalarioExcedenteDTO(int i, Integer integer, Integer integer2, int j, Date date, Date date2) {}

    // Constructor con los datos
    public SalarioExcedenteDTO(Integer id, Integer idEmpleado, Integer idConcepto, Integer valor, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.idEmpleado = idEmpleado;
        this.idConcepto = idConcepto;
        this.valor = valor;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Constructor que convierte un objeto SalarioExcedente en DTO
    public SalarioExcedenteDTO(SalarioExcedente salarioExcedente) {
        this.id = salarioExcedente.getId();
        this.idEmpleado = salarioExcedente.getEmpleado().getId();
        this.idConcepto = salarioExcedente.getConceptoSalarial().getId();
        this.valor = salarioExcedente.getValor();
        this.fechaInicio = salarioExcedente.getFechaInicio() != null ? salarioExcedente.getFechaInicio() : null;
        this.fechaFin = salarioExcedente.getFechaFin() != null ? salarioExcedente.getFechaFin(): null;
    }

    public SalarioExcedenteDTO(int id2, Integer id3, Integer id4, int valor2, String string, Object object) {
		// TODO Auto-generated constructor stub
	}

	// Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
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
}