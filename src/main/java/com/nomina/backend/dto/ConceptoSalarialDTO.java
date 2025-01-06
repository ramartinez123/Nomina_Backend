package com.nomina.backend.dto;

import com.nomina.backend.model.TipoConcepto;
import com.nomina.backend.model.CuentaContable;
import com.nomina.backend.model.TipoValor;
import java.util.Date;

public class ConceptoSalarialDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private TipoConcepto tipo;
    private Boolean impAportes;
    private Boolean impGanancias;
    private Boolean impIndemnizacion;
    private Boolean impSac;
    private Boolean sueldoTotal;
    private TipoValor tipoValor;
    private Integer valor;
    private CuentaContable cuentaContable;
    private String formula;
    private Date fechaInicio;
    private Date fechaFin;

    // Constructor
    public ConceptoSalarialDTO(Integer id, String nombre, String descripcion, TipoConcepto tipo, Boolean impAportes,
                                Boolean impGanancias, Boolean impIndemnizacion, Boolean impSac, Boolean sueldoTotal,
                                TipoValor tipoValor, Integer valor, CuentaContable cuentaContable, String formula,
                                Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.impAportes = impAportes;
        this.impGanancias = impGanancias;
        this.impIndemnizacion = impIndemnizacion;
        this.impSac = impSac;
        this.sueldoTotal = sueldoTotal;
        this.tipoValor = tipoValor;
        this.valor = valor;
        this.cuentaContable = cuentaContable;
        this.formula = formula;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoConcepto getTipo() {
        return tipo;
    }

    public void setTipo(TipoConcepto tipo) {
        this.tipo = tipo;
    }

    public Boolean getImpAportes() {
        return impAportes;
    }

    public void setImpAportes(Boolean impAportes) {
        this.impAportes = impAportes;
    }

    public Boolean getImpGanancias() {
        return impGanancias;
    }

    public void setImpGanancias(Boolean impGanancias) {
        this.impGanancias = impGanancias;
    }

    public Boolean getImpIndemnizacion() {
        return impIndemnizacion;
    }

    public void setImpIndemnizacion(Boolean impIndemnizacion) {
        this.impIndemnizacion = impIndemnizacion;
    }

    public Boolean getImpSac() {
        return impSac;
    }

    public void setImpSac(Boolean impSac) {
        this.impSac = impSac;
    }

    public Boolean getSueldoTotal() {
        return sueldoTotal;
    }

    public void setSueldoTotal(Boolean sueldoTotal) {
        this.sueldoTotal = sueldoTotal;
    }

    public TipoValor getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(TipoValor tipoValor) {
        this.tipoValor = tipoValor;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public CuentaContable getCuentaContable() {
        return cuentaContable;
    }

    public void setCuentaContable(CuentaContable cuentaContable) {
        this.cuentaContable = cuentaContable;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
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