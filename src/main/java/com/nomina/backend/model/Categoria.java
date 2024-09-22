package com.nomina.backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categorias_convenio")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    @ManyToOne
    @JoinColumn(name = "id_convenio", nullable = false)
    private Convenio convenio; 

    @Column(nullable = false, length = 60)
    private String nombre;

    @Column(length = 50)
    private String descripcion; 

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Empleado> empleados;

    // Getters y Setters
    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
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
