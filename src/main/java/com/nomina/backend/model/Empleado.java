package com.nomina.backend.model;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 40)
    private String nombre;

    @Column(nullable = false, length = 40)
    private String apellido;

    @Column(nullable = false, unique = true, length = 20)
    private String dni;

    @Column(name = "fecha_nacimiento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(nullable = false, unique = true, length = 40)
    private String email;

    @Column(length = 15)
    private String telefono;

    @Column(length = 45)
    private String direccion;

    @Column(length = 20)
    private String ciudad;

    @ManyToOne
    @JoinColumn(name = "id_provincia")
    private Provincia provincia;

    @ManyToOne
    @JoinColumn(name = "id_departamento")
    private Departamento departamento;

    @ManyToOne
    @JoinColumn(name = "id_convenio")
    private Convenio convenio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_puesto")
    private Puesto puesto;

    @Column(name = "fecha_inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_empleado", nullable = false)
    private EstadoEmpleado estadoEmpleado;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "motivo", nullable = true)
    private Motivo motivo;

    @Column(name = "dias_vacaciones_pactadas")
    private Integer diasVacacionesPactadas;

    @Column(length = 20)
    private String nacionalidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_civil")
    private EstadoCivil estadoCivil;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 10) 
    private Genero genero;

    @ManyToOne
    @JoinColumn(name = "id_obra_social")
    private ObraSocial obraSocial;

    @Column(length = 22, unique = true)
    private String cbu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_banco")
    private Banco banco;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta_bancaria", nullable = false)
    private TipoCuentaBancaria tipoCuentaBancaria;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contrato", nullable = false)
    private TipoContrato tipoContrato;
    
    @OneToMany(mappedBy = "empleado", fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
    @JsonIgnore
    private List<DetalleLiquidacion> detalleLiquidaciones;
    
    @OneToMany(mappedBy = "empleado", fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
    @JsonIgnore
    private List<SalarioExcedente> salarioExcedente;
    
    @OneToMany(mappedBy = "empleado", fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
    @JsonIgnore
    private List<NovedadLiquidacion> novedadLiquidaciones;
    
    @OneToMany(mappedBy = "empleado", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Familiar> familiares; // Asegúrate de que este nombre coincida con el nombre de la propiedad en Familiares
    
    @Column(length = 22, unique = true)
    private String cuil;

 // Constructor lleno
    public Empleado(Integer id, String nombre, String apellido, String dni, Date fechaNacimiento, String email, String telefono, 
                    String direccion, String ciudad, Provincia provincia, Departamento departamento, Convenio convenio, 
                    Categoria categoria, Puesto puesto, Date fechaInicio, Date fechaFin, EstadoEmpleado estadoEmpleado, 
                    Motivo motivo, Integer diasVacacionesPactadas, String nacionalidad, EstadoCivil estadoCivil, 
                    Genero genero, ObraSocial obraSocial, String cbu, Banco banco, TipoCuentaBancaria tipoCuentaBancaria, 
                    TipoContrato tipoContrato, List<DetalleLiquidacion> detalleLiquidaciones, 
                    List<SalarioExcedente> salarioExcedente, List<NovedadLiquidacion> novedadLiquidaciones, 
                    List<Familiar> familiares, String cuil) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = (java.sql.Date) fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.departamento = departamento;
        this.convenio = convenio;
        this.categoria = categoria;
        this.puesto = puesto;
        this.fechaInicio = (java.sql.Date) fechaInicio;
        this.fechaFin = (java.sql.Date) fechaFin;
        this.estadoEmpleado = estadoEmpleado;
        this.motivo = motivo;
        this.diasVacacionesPactadas = diasVacacionesPactadas;
        this.nacionalidad = nacionalidad;
        this.estadoCivil = estadoCivil;
        this.genero = genero;
        this.obraSocial = obraSocial;
        this.cbu = cbu;
        this.banco = banco;
        this.tipoCuentaBancaria = tipoCuentaBancaria;
        this.tipoContrato = tipoContrato;
        this.detalleLiquidaciones = detalleLiquidaciones;
        this.salarioExcedente = salarioExcedente;
        this.novedadLiquidaciones = novedadLiquidaciones;
        this.familiares = familiares;
        this.cuil = cuil;
    }
    
    public Empleado() {
    }

    public Empleado(Integer id) {
		// TODO Auto-generated constructor stub
	}
    
 // Getters y Setters

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

    public void setFechaNacimiento(java.sql.Date fechaNacimiento) {
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

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(java.sql.Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(java.sql.Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getDiasVacacionesPactadas() {
        return diasVacacionesPactadas;
    }

    public void setDiasVacacionesPactadas(Integer diasVacacionesPactadas) {
        this.diasVacacionesPactadas = diasVacacionesPactadas;
    }

    public EstadoEmpleado getEstadoEmpleado() {
        return estadoEmpleado;
    }

    public void setEstadoEmpleado(EstadoEmpleado estadoEmpleado) {
        this.estadoEmpleado = estadoEmpleado;
    }
    
    public Motivo getMotivo() {
        return motivo;
    }

    public void setMotivo(Motivo motivo) {
        this.motivo = motivo;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public ObraSocial getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(ObraSocial obraSocial) {
        this.obraSocial = obraSocial;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public TipoCuentaBancaria getTipoCuentaBancaria() {
        return tipoCuentaBancaria;
    }

    public void setTipoCuentaBancaria(TipoCuentaBancaria tipoCuentaBancaria) {
        this.tipoCuentaBancaria = tipoCuentaBancaria;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

	public String getCuil() {
		return cuil;
	}
	
	public void setCuil(String cuil) {
	        this.cuil = cuil;
	    }
}
