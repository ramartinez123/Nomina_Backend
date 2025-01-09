package com.nomina.backend.dto;



import java.util.Date;

import com.fasterxml.jackson.annotation.JsonView;
import com.nomina.backend.model.EstadoCivil;
import com.nomina.backend.model.EstadoEmpleado;
import com.nomina.backend.model.Genero;
import com.nomina.backend.model.TipoContrato;
import com.nomina.backend.model.TipoCuentaBancaria;


public class EmpleadoDTO {

	@JsonView(EmpleadoViews.Basica.class)
    private Integer id;
	
	@JsonView(EmpleadoViews.Basica.class)
    private String nombre;
	
	@JsonView(EmpleadoViews.Basica.class)
    private String apellido;
	
    @JsonView(EmpleadoViews.Basica.class)
    private String dni;
    private Date fechaNacimiento;
    
    @JsonView(EmpleadoViews.Basica.class)
    private String email;
    private String telefono;
    
    @JsonView(EmpleadoViews.Basica.class)
    private String direccion;
    
    @JsonView(EmpleadoViews.Basica.class)
    private String ciudad;
    
    @JsonView(EmpleadoViews.Basica.class)
    private Integer idProvincia; // Solo el ID, si es necesario
    
    @JsonView(EmpleadoViews.Basica.class)
    private Integer idDepartamento; // Solo el ID, si es necesario
    private Integer idConvenio; // Solo el ID, si es necesario
    private Integer idCategoria; // Solo el ID, si es necesario
    
    @JsonView(EmpleadoViews.Basica.class)
    private Integer idPuesto; 
    
    @JsonView(EmpleadoViews.Basica.class)
    private Date fechaInicio;
    private Date fechaFin;
    private Integer diasVacacionesPactadas;
    private String estadoEmpleado;
    private String nacionalidad;
    private String estadoCivil; 
    private String genero; 
    private Integer idObraSocial; 
    private String cbu;
    private Integer idBanco; 
    private String tipoCuentaBancaria; 
    private String tipoContrato; 
    
    @JsonView(EmpleadoViews.Basica.class)
    private String cuil;

    @JsonView(EmpleadoViews.Basica.class)
	private String departamento;
    
 // Constructor vacío
    public EmpleadoDTO() {}

    // Constructor completo
    
   /* public EmpleadoDTO(Integer id, String nombre, String apellido, String dni, Date fechaNacimiento, String email,
            String telefono, String direccion, String ciudad, Integer idProvincia, Integer idDepartamento,
            Integer idConvenio, Integer idCategoria, Integer idPuesto, Date fechaInicio, Date fechaFin,
            Integer diasVacacionesPactadas, EstadoEmpleado estadoEmpleado, String nacionalidad, EstadoCivil estadoCivil,
            Genero genero, Integer idObraSocial, String cbu, Integer idBanco, TipoCuentaBancaria tipoCuentaBancaria,
            TipoContrato tipoContrato, String cuil) {*/
// Asignar valores

      public EmpleadoDTO(Integer id, String nombre, String apellido, String dni, Date fechaNacimiento, String email,
                       String telefono, String direccion, String ciudad, Integer idProvincia,
                       Integer idDepartamento, Integer idConvenio, Integer idCategoria, Integer idPuesto,
                       Date fechaInicio, Date fechaFin, Integer diasVacacionesPactadas, String estadoEmpleado,
                       String nacionalidad, String estadoCivil, String genero, Integer idObraSocial, String cbu,
                       Integer idBanco, String tipoCuentaBancaria, String tipoContrato, String cuil) {
       
    	
    	
    	
    	
    	this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.idProvincia = idProvincia;
        this.idDepartamento = idDepartamento;
        this.idConvenio = idConvenio;
        this.idCategoria = idCategoria;
        this.idPuesto = idPuesto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.diasVacacionesPactadas = diasVacacionesPactadas;
        this.estadoEmpleado = estadoEmpleado;
        this.nacionalidad = nacionalidad;
        this.estadoCivil = estadoCivil;
        this.genero = genero;
        this.idObraSocial = idObraSocial;
        this.cbu = cbu;
        this.idBanco = idBanco;
        this.tipoCuentaBancaria = tipoCuentaBancaria;
        this.tipoContrato = tipoContrato;
        this.cuil = cuil;
    }
    
    public EmpleadoDTO(Integer id, String nombre, String apellido, Integer idDepartamento, Integer idPuesto, String cuil) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.idDepartamento = idDepartamento;
        this.idPuesto = idPuesto;
        this.cuil = cuil;
    }
    // Getters y Setters

    public EmpleadoDTO(Integer id, String nombre, String apellido,String direccion,String ciudad,Integer idProvincia,String dni,String email, Date date, String departamento, Integer idPuesto, String cuil) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.idProvincia = idProvincia;
        this.dni = dni;
        this.email = email;
        this.fechaInicio = date;
        this.departamento = departamento;
        this.idPuesto = idPuesto;
        this.cuil = cuil;
    }
    
    
    

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Integer getIdConvenio() {
        return idConvenio;
    }

    public void setIdConvenio(Integer idConvenio) {
        this.idConvenio = idConvenio;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(Integer idPuesto) {
        this.idPuesto = idPuesto;
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

    public Integer getDiasVacacionesPactadas() {
        return diasVacacionesPactadas;
    }

    public void setDiasVacacionesPactadas(Integer diasVacacionesPactadas) {
        this.diasVacacionesPactadas = diasVacacionesPactadas;
    }

    public String getEstadoEmpleado() {
        return estadoEmpleado;
    }

    public void setEstadoEmpleado(String estadoEmpleado) {
        this.estadoEmpleado = estadoEmpleado;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getIdObraSocial() {
        return idObraSocial;
    }

    public void setIdObraSocial(Integer idObraSocial) {
        this.idObraSocial = idObraSocial;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public String getTipoCuentaBancaria() {
        return tipoCuentaBancaria;
    }

    public void setTipoCuentaBancaria(String tipoCuentaBancaria) {
        this.tipoCuentaBancaria = tipoCuentaBancaria;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }
    
    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }
}
