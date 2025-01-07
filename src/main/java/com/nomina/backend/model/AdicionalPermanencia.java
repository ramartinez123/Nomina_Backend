package com.nomina.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class AdicionalPermanencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_convenio", referencedColumnName = "id_convenio")
    private Convenio convenio; 
    
  	@NotNull(message = "Los años de antigüedad no pueden ser nulos.")
    @Min(value = 0, message = "Los años de antigüedad no pueden ser negativos.")
    @Column(name = "anos_antiguedad")
    private Integer aniosAntiguedad;
    
    @NotNull(message = "El monto no puede ser nulo.")
    @Min(value = 0, message = "El monto no puede ser negativo.")
    @Column(name = "monto")
    private Integer monto;

    // Constructor vacío
    public AdicionalPermanencia() {}

    // Constructor con parámetros
    public AdicionalPermanencia(Integer id, Convenio convenio, Integer anosAntiguedad, Integer monto) {
        this.id = id;
        this.convenio = convenio;
        this.aniosAntiguedad = anosAntiguedad;
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
        return aniosAntiguedad;
    }

    public void setAnosAntiguedad(Integer anosAntiguedad) {
        this.aniosAntiguedad = anosAntiguedad;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }
}