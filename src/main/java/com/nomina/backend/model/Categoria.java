package com.nomina.backend.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "categorias_convenio")
@JsonIgnoreProperties({	"descripcion", "empleados","convenio"})
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

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Empleado> empleados;
    
 // Constructor vacío
    public Categoria() {
    }

    // Constructor lleno
    public Categoria(Convenio convenio, String nombre, String descripcion, List<Empleado> empleados, List<HistoricoValoresCategoria> historicoValores) {
        this.convenio = convenio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.empleados = empleados;
        //this.historicoValores = historicoValores;
    }
    

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
