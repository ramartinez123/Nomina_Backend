package com.nomina.backend.model;

import jakarta.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "salario_excedente")
public class SalarioExcedente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "id_concepto", nullable = false)
    @JsonManagedReference
    private ConceptoSalarial conceptoSalarial;

    @Column(name = "valor", nullable = false)
    private int valor;

    @Column(name = "fecha_inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    // Constructor vacio
    public SalarioExcedente() {}

    public SalarioExcedente(Empleado empleado, ConceptoSalarial conceptoSalarial, int valor, Date fechaInicio, Date fechaFin) {
        this.empleado = empleado;
        this.conceptoSalarial = conceptoSalarial;
        this.valor = valor;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    
    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public ConceptoSalarial getConceptoSalarial() {
        return conceptoSalarial;
    }

    public void setConceptoSalarial(ConceptoSalarial conceptoSalarial) {
        this.conceptoSalarial = conceptoSalarial;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
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