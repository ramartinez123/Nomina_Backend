package com.nomina.backend.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "provincias")
public class Provincia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_provincia")
    private Integer idProvincia;

    @Column(nullable = false, length = 40)
    private String nombre;
    
    @OneToMany(mappedBy = "provincia", fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
    @JsonIgnore
    private List<Empleado> empleados;

    // Constructor vacío
    public Provincia() {
    }

    // Constructor lleno
    public Provincia(String nombre) {
        this.nombre = nombre;
    }
         
    // Getters y Setters
    public Integer getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
