package com.nomina.backend.model;

import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "escala_ganancias")
public class EscalaGanancias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @Column(name = "fechaInicio", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio; 

    @Temporal(TemporalType.DATE)
    @Column(name = "fechaFin", nullable = true)
    private Date fechaFin;

    @Column(name = "desde", nullable = false)
    private Integer desde; 

    @Column(name = "hasta", nullable = false)
    private Integer hasta; 

    @Column(name = "fijo", nullable = false)
    private Integer fijo; 

    @Column(name = "porcentaje", nullable = false)
    private Integer porcentaje; 

    @Column(name = "excedente", nullable = false)
    private Integer excedente; 

 // Constructor vacío
    public EscalaGanancias() {
    }

    // Constructor lleno
    public EscalaGanancias(Date fechaInicio, Date fechaFin, Integer desde, Integer hasta, Integer fijo, Integer porcentaje, Integer excedente) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.desde = desde;
        this.hasta = hasta;
        this.fijo = fijo;
        this.porcentaje = porcentaje;
        this.excedente = excedente;
    }

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

    public void setDesde(Integer desde) {
        this.desde = desde;
    }

    public int getHasta() {
        return hasta;
    }

    public void setHasta(Integer hasta) {
        this.hasta = hasta;
    }

    public int getFijo() {
        return fijo;
    }

    public void setFijo(Integer fijo) {
        this.fijo = fijo;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }

    public int getExcedente() {
        return excedente;
    }

    public void setExcedente(Integer excedente) {
        this.excedente = excedente;
    }
}
