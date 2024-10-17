package com.nomina.backend.model;

import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "familiares")
public class Familiar {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_parentesco", nullable = false, length = 40)
    private String idParentesco; 
    
    @ManyToOne
    @JoinColumn(name = "id_empleado") 
    private Empleado empleado;

    @Column(name = "apellido_nombre", nullable = false, length = 60)
    private String apellidoNombre; 

    @Column(name = "fecha_nacimiento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento; 

    @Column(name = "fecha_inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio; 

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin; 
    
    @Column(name = "a_cargo", nullable = false)
    private boolean aCargo; 
    
    @Column(name = "a_cargo_osocial", nullable = false)
    private boolean aCargoOSocial; 

    @Column(name = "tiene_discapacidad", nullable = false)
    private boolean tieneDiscapacidad; 
    
    
    
    
    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdParentesco() {
        return idParentesco;
    }

    public void setIdParentesco(String idParentesco) {
        this.idParentesco = idParentesco;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }


    public String getApellidoNombre() {
        return apellidoNombre;
    }

    public void setApellidoNombre(String apellidoNombre) {
        this.apellidoNombre = apellidoNombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
    
 // Getter y Setter para aCargoOSocial
    public boolean getACargo() {
        return aCargo;
    }

    public void setACargo(boolean aCargo) {
        this.aCargo = aCargo;
    }
    
    
 // Getter y Setter para aCargoOSocial
    public boolean getACargoOSocial() {
        return aCargoOSocial;
    }

    public void setACargoOSocial(boolean aCargoOSocial) {
        this.aCargoOSocial = aCargoOSocial;
    }

    

    public boolean isTieneDiscapacidad() {
        return tieneDiscapacidad;
    }

    public void setTieneDiscapacidad(boolean tieneDiscapacidad) {
        this.tieneDiscapacidad = tieneDiscapacidad;
    }	
}