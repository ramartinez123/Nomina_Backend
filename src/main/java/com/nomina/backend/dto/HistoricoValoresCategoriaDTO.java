package com.nomina.backend.dto;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;

public class HistoricoValoresCategoriaDTO {
    private Integer id; // Opcional para creación, obligatorio para actualización
    private Integer idCategoria;
    
    @Column(name = "fecha_inicio", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")  
    private String fechaInicio;
    
    private String fechaBaja;
    private Integer salario;
    private Integer almuerzo;

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(String fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Integer getSalario() {
        return salario;
    }

    public void setSalario(Integer salario) {
        this.salario = salario;
    }

    public Integer getAlmuerzo() {
        return almuerzo;
    }

    public void setAlmuerzo(Integer almuerzo) {
        this.almuerzo = almuerzo;
    }
}
