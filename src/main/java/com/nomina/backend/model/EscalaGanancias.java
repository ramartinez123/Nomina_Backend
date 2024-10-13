package com.nomina.backend.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "escala_ganancias")
public class EscalaGanancias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // ID autogenerado

    @Column(name = "fechaInicio", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio; // Fecha de inicio

    @Temporal(TemporalType.DATE)
    @Column(name = "fechaFin", nullable = true)
    private Date fechaFin; // Fecha de fin

    @Column(name = "desde", nullable = false)
    private int desde; // Rango desde

    @Column(name = "hasta", nullable = false)
    private int hasta; // Rango hasta

    @Column(name = "fijo", nullable = false)
    private int fijo; // Valor fijo

    @Column(name = "porcentaje", nullable = false)
    private int porcentaje; // Porcentaje

    @Column(name = "excedente", nullable = false)
    private int excedente; // Excedente

    // Constructor vacío
    public EscalaGanancias() {}

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getDesde() {
        return desde;
    }

    public void setDesde(int desde) {
        this.desde = desde;
    }

    public int getHasta() {
        return hasta;
    }

    public void setHasta(int hasta) {
        this.hasta = hasta;
    }

    public int getFijo() {
        return fijo;
    }

    public void setFijo(int fijo) {
        this.fijo = fijo;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public int getExcedente() {
        return excedente;
    }

    public void setExcedente(int excedente) {
        this.excedente = excedente;
    }
}
