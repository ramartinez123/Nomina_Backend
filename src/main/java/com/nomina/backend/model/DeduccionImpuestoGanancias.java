package com.nomina.backend.model;

import java.util.Date;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "deduccion_impuesto_ganancias")
public class DeduccionImpuestoGanancias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_deduccion")
    private int idDeduccion;

    @Column(name = "id_tipo_deduccion", nullable = false, length =11)
    private Integer idTipoDeduccion;

    @Column(name = "nombre", nullable = false, length = 60)
    private String nombre;

    @Column(name = "fecha_inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @NotNull(message = "El monto no puede ser nulo.")
    @Min(value = 0, message = "El monto no puede ser negativo.")
    @Column(name = "valor", nullable = false)
    private int valor;
    
 // Constructor vacío
    public DeduccionImpuestoGanancias() {
    }

    // Constructor lleno
    public DeduccionImpuestoGanancias(int idDeduccion, Integer idTipoDeduccion, String nombre, Date fechaInicio, Date fechaFin, int valor) {
        this.idDeduccion = idDeduccion;
        this.idTipoDeduccion = idTipoDeduccion;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.valor = valor;
    }   

    // Getters y Setters
    public int getIdDeduccion() {
        return idDeduccion;
    }

    public void setIdDeduccion(int idDeduccion) {
        this.idDeduccion = idDeduccion;
    }

    public Integer getIdTipoDeduccion() {
        return idTipoDeduccion;
    }

    public void setIdTipoDeduccion(Integer idTipoDeduccion) {
        this.idTipoDeduccion = idTipoDeduccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}