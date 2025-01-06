package com.nomina.backend.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "detalle_liquidacion")
public class DetalleLiquidacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    @Column(name = "periodo", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date periodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_concepto", nullable = false)
    private ConceptoSalarial conceptoSalarial;

    @Column(name = "monto", nullable = false)
    private int monto;

    @Column(name = "fecha_liquidacion", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaLiquidacion;

 // Constructor vacío
    public DetalleLiquidacion() {
    }

    // Constructor lleno
    public DetalleLiquidacion(int id, Empleado empleado, Date periodo, ConceptoSalarial conceptoSalarial, int monto, Date fechaLiquidacion) {
        this.id = id;
        this.empleado = empleado;
        this.periodo = periodo;
        this.conceptoSalarial = conceptoSalarial;
        this.monto = monto;
        this.fechaLiquidacion = fechaLiquidacion;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Date getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Date periodo) {
        this.periodo = periodo;
    }

    public ConceptoSalarial getConceptoSalarial() {
        return conceptoSalarial;
    }

    public void setConceptoSalarial(ConceptoSalarial conceptoSalarial) {
        this.conceptoSalarial = conceptoSalarial;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

	public void setConceptoSalarial(Integer conceptoId) {
		// TODO Auto-generated method stub
		
	}

	public void setEmpleado(int empleadoId) {
		// TODO Auto-generated method stub
		
	}
}