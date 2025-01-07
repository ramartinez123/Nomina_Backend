package com.nomina.backend.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private String descripcion; 

    @OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Empleado> empleados;
    
    // Constructor vacío
    public Departamento() {
    }

    // Constructor lleno
    public Departamento(Integer idDepartamento, String nombre, String descripcion, List<Empleado> empleados) {
        this.idDepartamento = idDepartamento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.empleados = empleados;
    }
      
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

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

	public String getNombreDepartamento() {
		return nombre;
	}
}
