package com.nomina.backend.model;



import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "novedades_liquidacion")
public class NovedadLiquidacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idConcepto", nullable = false)
    private ConceptoSalarial conceptoSalarial; 
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEmpleado", nullable = false) 
    private Empleado empleado; 

    @Column(name = "fechaInicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad; 
    
    // Constructor vacío
    public NovedadLiquidacion() {
    }

    // Constructor lleno
    public NovedadLiquidacion(ConceptoSalarial conceptoSalarial, Empleado empleado, Date fechaInicio, Integer cantidad) {
        this.conceptoSalarial = conceptoSalarial;
        this.empleado = empleado;
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

    public ConceptoSalarial getConcepto() {
        return conceptoSalarial;
    }

    public void setConcepto(ConceptoSalarial conceptoSalarial) {
        this.conceptoSalarial = conceptoSalarial;
    }
    
    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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
