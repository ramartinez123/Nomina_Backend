package com.nomina.backend.model;

import java.sql.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "novedades_liquidacion")
public class NovedadLiquidacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Identificador único

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idConcepto", nullable = false)
    private ConceptoSalarial conceptoSalarial; // Referencia al concepto salarial
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEmpleado", nullable = false) // Nueva referencia a Empleado
    private Empleado empleado; // Referencia al empleado


    @Column(name = "fechaInicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private java.sql.Date fechaInicio;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad; // Cantidad asociada a la novedad

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
