package com.nomina.backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "puestos")
public class Puesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_puesto")
    private Integer idPuesto;

    @Column(nullable = false, length = 60)
    private String nombre;

    @Column(length = 100)
    private String descripcion; // Descripción opcional del puesto

    @OneToMany(mappedBy = "puesto", cascade = CascadeType.ALL) // Relación con Empleado
    private List<Empleado> empleados;

    // Getters y Setters
    public Integer getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(Integer idPuesto) {
        this.idPuesto = idPuesto;
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