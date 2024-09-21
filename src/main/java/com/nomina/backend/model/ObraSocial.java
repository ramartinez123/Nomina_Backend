package com.nomina.backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "obras_sociales")
public class ObraSocial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_obra_social")
    private Integer idObraSocial;

    @Column(nullable = false, length = 40)
    private String nombre;

    @Column(length = 100)
    private String descripcion; // Descripción opcional de la obra social

    @OneToMany(mappedBy = "obraSocial", cascade = CascadeType.ALL) // Relación con Empleado
    private List<Empleado> empleados;

    // Getters y Setters
    public Integer getIdObraSocial() {
        return idObraSocial;
    }

    public void setIdObraSocial(Integer idObraSocial) {
        this.idObraSocial = idObraSocial;
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