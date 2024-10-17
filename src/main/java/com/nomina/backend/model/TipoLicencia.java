package com.nomina.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "licencia")
public class TipoLicencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_licencia")
    private int idLicencia;

    @Column(name = "descripcion", nullable = false, length = 50)
    private String descripcion;

    @Column(name = "dias_maximos")
    private Integer diasMaximos;

    // Constructor vacio
    public TipoLicencia() {
    }

    // Constructor lleno
    public TipoLicencia(String descripcion, Integer diasMaximos) {
        this.descripcion = descripcion;
        this.diasMaximos = diasMaximos;
    }

    // Getters y Setters
    public int getIdLicencia() {
        return idLicencia;
    }

    public void setIdLicencia(int idLicencia) {
        this.idLicencia = idLicencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDiasMaximos() {
        return diasMaximos;
    }

    public void setDiasMaximos(Integer diasMaximos) {
        this.diasMaximos = diasMaximos;
    }
}
