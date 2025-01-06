package com.nomina.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "historico_valores_categoria")
public class HistoricoValoresCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; 
    
    @ManyToOne	
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria; 

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio; 

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_baja",nullable = true		)
    @Temporal(TemporalType.DATE)
    private Date fechaBaja; 

    @Column(name = "salario")
    private Integer salario; 

    @Column(name = "almuerzo", nullable = false)
    private Integer almuerzo; 

    // Constructor vacío
    public HistoricoValoresCategoria() {
    }

    // Constructor lleno
    public HistoricoValoresCategoria(Categoria categoria, Date fechaInicio, Date fechaBaja, Integer salario, Integer almuerzo) {
        this.categoria = categoria;
        this.fechaInicio = fechaInicio;
        this.fechaBaja = fechaBaja;
        this.salario = salario;
        this.almuerzo = almuerzo;
    }

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

    // Exponer solo el ID de la categoría en la respuesta JSON
    @JsonProperty("idCategoria")
    public Integer getCategoriaId() {
        return categoria != null ? categoria.getIdCategoria() : null;
    }

    // Métodos para obtener/establecer otros campos
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