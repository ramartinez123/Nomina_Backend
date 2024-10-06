package com.nomina.backend.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "concepto_salarial")
public class ConceptoSalarial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Identificador único del concepto

    @Column(name = "nombre", nullable = false, length = 60)
    private String nombre; // Nombre del concepto salarial

    @Column(name = "descripcion", nullable = false, length = 60)
    private String descripcion; // Descripción detallada del concepto

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoConcepto tipo; // Tipo de concepto (remunerativo, aportes, descuentos, no remunerativos)

    @Column(name = "imp_aportes", nullable = false)
    private Boolean impAportes; // Indica si es imponible a aportes

    @Column(name = "imp_ganancias", nullable = false)
    private Boolean impGanancias; // Indica si es imponible a ganancias

    @Column(name = "imp_indemnizacion", nullable = false)
    private Boolean impIndemnizacion; // Indica si impacta para indemnización

    @Column(name = "imp_sac", nullable = false)
    private Boolean impSac; // Indica si impacta en el cálculo del aguinaldo

    @Column(name = "sueldo_total", nullable = false)
    private Boolean sueldoTotal; // Indica si el concepto es acumulador

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_valor", nullable = false)
    private TipoValor tipoValor; // Tipo de valor (fijo, porcentaje, calculado)

    @Column(name = "valor")
    private Integer valor; // Valor asociado (monto fijo, porcentaje o NULL si es calculado)

    @Column(name = "formula", length = 60)
    private String formula; // Fórmula para el cálculo (si aplica)

    @Column(name = "fecha_inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio; // Fecha de inicio de vigencia del concepto

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin; // Fecha de fin de vigencia del concepto
    
    @OneToMany(mappedBy = "conceptoSalarial", fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
    @JsonIgnore
    private List<DetalleLiquidacion> detalleLiquidaciones;
    
    @OneToMany(mappedBy = "conceptoSalarial", fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
    @JsonIgnore
    private List<SalarioExcedente> salarioExcedente;
    
    @OneToMany(mappedBy = "conceptoSalarial", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<NovedadLiquidacion> novedadLiquidaciones;
    
 // Getters y Setters
    
    public ConceptoSalarial() {
    }

    public ConceptoSalarial(Integer id) {
		// TODO Auto-generated constructor stub
	}

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

   