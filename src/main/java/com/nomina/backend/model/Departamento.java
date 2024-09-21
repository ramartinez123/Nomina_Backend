package com.nomina.backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "departamentos")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_departamento")
    private Integer idDepartamento;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(length = 100)
    private String descripcion; // Descripción opcional del departamento

    @OneToMany(mappedBy = "departamento")
    private List<Empleado> empleados;

    // Getters y Setters
    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
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

    public void listEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
}
