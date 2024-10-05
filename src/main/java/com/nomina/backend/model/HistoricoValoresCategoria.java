package com.nomina.backend.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "historico_valores_categoria")
public class HistoricoValoresCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; // Identificador único

    @ManyToOne // Relación con Categoria
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria; // Referencia a la categoría

    @Column(name = "fecha_inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio; // Fecha de inicio

    @Column(name = "fecha_baja")
    @Temporal(TemporalType.DATE)
    private Date fechaBaja; // Fecha de baja

    @Column(name = "salario")
    private Integer salario; // Salario correspondiente

    @Column(name = "almuerzo", nullable = false)
    private Integer almuerzo; // Valor del almuerzo

    // Constructor por defecto
    public HistoricoValoresCategoria() {}

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
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