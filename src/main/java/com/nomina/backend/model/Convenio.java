package com.nomina.backend.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "convenios")
public class Convenio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_convenio")
    private Integer idConvenio;

    @Column(nullable = false, length = 60)
    private String nombre;

    @Column(length = 100)
    private String descripcion;

    @OneToMany(mappedBy = "convenio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Empleado> empleados;
    
    @OneToMany(mappedBy = "convenio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AdicionalPermanencia> adicionalPermanencia;

    public Convenio() {
    }

    // Constructor lleno
    public Convenio(String nombre, String descripcion, List<Empleado> empleados, List<AdicionalPermanencia> adicionalPermanencia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.empleados = empleados;
        this.adicionalPermanencia = adicionalPermanencia;
    }
         
    
    // Getters y Setters
    public Integer getIdConvenio() {
        return idConvenio;
    }

    public void setIdConvenio(Integer idConvenio) {
        this.idConvenio = idConvenio;
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

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
}