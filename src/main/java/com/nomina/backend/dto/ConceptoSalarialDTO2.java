package com.nomina.backend.dto;

import com.nomina.backend.model.TipoConcepto;
import com.nomina.backend.model.CuentaContable;

public class ConceptoSalarialDTO2 {

    private Integer id; 
    private String nombre; 
    private TipoConcepto tipo; 
    private CuentaContable cuentaContable;

    // Constructor
    public ConceptoSalarialDTO2(Integer id, String nombre, TipoConcepto tipo, CuentaContable cuentaContable) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.cuentaContable = cuentaContable;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoConcepto getTipo() {
        return tipo;
    }

    public void setTipo(TipoConcepto tipo) {
        this.tipo = tipo;
    }

    public CuentaContable getCuentaContable() {
        return cuentaContable;
    }

    public void setCuentaContable(CuentaContable cuentaContable) {
        this.cuentaContable = cuentaContable;
    }
}