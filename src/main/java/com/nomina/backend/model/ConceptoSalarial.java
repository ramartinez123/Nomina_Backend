package com.nomina.backend.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "concepto_salarial")
public class ConceptoSalarial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @Column(name = "nombre", nullable = false, length = 60)
    private String nombre; 

    @Column(name = "descripcion", nullable = true, length = 60)
    private String descripcion; 

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoConcepto tipo; 

    @Column(name = "imp_aportes", nullable = true)
    private Boolean impAportes; 

    @Column(name = "imp_ganancias", nullable = true)
    private Boolean impGanancias;
    
    @Column(name = "imp_indemnizacion", nullable = true)
    private Boolean impIndemnizacion; 

    @Column(name = "imp_sac", nullable = true)
    private Boolean impSac; 

    @Column(name = "sueldo_total", nullable = true)
    private Boolean sueldoTotal; 
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_valor", nullable = true)
    private TipoValor tipoValor; 

    @Column(name = "valor", nullable = true)
    private Integer valor; 

    @Enumerated(EnumType.STRING)
    @Column(name = "cuenta_contable",nullable = true)
    private CuentaContable cuentaContable; 
    
    @Column(name = "formula", length = 60, nullable = true)
    private String formula; 

    @Column(name = "fecha_inicio", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio; 

    @Column(name = "fecha_fin",nullable = true)
    @Temporal(TemporalType.DATE)
    private Date fechaFin; 
    
    @OneToMany(mappedBy = "conceptoSalarial", fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
    @JsonIgnore
    private List<DetalleLiquidacion> detalleLiquidaciones;
    
    @OneToMany(mappedBy = "conceptoSalarial", fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
    @JsonManagedReference
    private List<SalarioExcedente> salarioExcedente;
    
    @OneToMany(mappedBy = "conceptoSalarial", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<NovedadLiquidacion> novedadLiquidaciones;
    
    public ConceptoSalarial() {
    }

    // Constructor lleno
    public ConceptoSalarial(String nombre, String descripcion, TipoConcepto tipo, Boolean impAportes,
                             Boolean impGanancias, Boolean impIndemnizacion, Boolean impSac,
                             Boolean sueldoTotal, TipoValor tipoValor, Integer valor,
                             CuentaContable cuentaContable, String formula, Date fechaInicio,
                             Date fechaFin, List<DetalleLiquidacion> detalleLiquidaciones,
                             List<SalarioExcedente> salarioExcedente, List<NovedadLiquidacion> novedadLiquidaciones) {
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
        this.detalleLiquidaciones = detalleLiquidaciones;
        this.salarioExcedente = salarioExcedente;
        this.novedadLiquidaciones = novedadLiquidaciones;
    }

    public ConceptoSalarial(Integer id) {
		// TODO Auto-generated constructor stub
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

    public List<DetalleLiquidacion> getDetalleLiquidaciones() {
        return detalleLiquidaciones;
    }

    public void setDetalleLiquidaciones(List<DetalleLiquidacion> detalleLiquidaciones) {
        this.detalleLiquidaciones = detalleLiquidaciones;
    }

    public List<SalarioExcedente> getSalarioExcedente() {
        return salarioExcedente;
    }
    
}

   