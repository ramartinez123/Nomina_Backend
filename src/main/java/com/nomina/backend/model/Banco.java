package com.nomina.backend.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bancos")
public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_banco")
    private Integer idBanco;

    @Column(nullable = false, length = 30)
    private String nombre;

    @Column(length = 50)
    private String descripcion; 

    @OneToMany(mappedBy = "banco", fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
    @JsonIgnore
    private List<Empleado> empleados;
    
    // Constructor vacío
    public Banco() {}

    // Constructor con parámetros
    public Banco(Integer idBanco, String nombre, String descripcion) {
        this.idBanco = idBanco;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
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