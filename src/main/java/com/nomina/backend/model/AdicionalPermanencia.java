package com.nomina.backend.model;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class AdicionalPermanencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_convenio", referencedColumnName = "id_convenio")
    private Convenio convenio; // Cambiar de Integer a Convenio
    
    @Column(name = "anos_antiguedad")
    private Integer anosAntiguedad;
    
    @Column(name = "monto")
    private Integer monto;

    // Constructor vacío
    public AdicionalPermanencia() {}

    // Constructor con parámetros
    public AdicionalPermanencia(Integer id, Convenio convenio, Integer anosAntiguedad, Integer monto) {
        this.id = id;
        this.convenio = convenio;
        this.anosAntiguedad = anosAntiguedad;
        this.monto = monto;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public Integer getAnosAntiguedad() {
        return anosAntiguedad;
    }

    public void setAnosAntiguedad(Integer anosAntiguedad) {
        this.anosAntiguedad = anosAntiguedad;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }
}