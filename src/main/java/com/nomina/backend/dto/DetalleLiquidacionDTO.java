package com.nomina.backend.dto;

public class DetalleLiquidacionDTO {
    private Integer empleadoId;
    private String apellido;
    private String nombre;
    private Double concepto1001;
    private Double concepto1002;
    private Double concepto1003;
    private Double concepto1004;
    private Double concepto1005;

    // Constructor con los parámetros que espera la consulta
    public DetalleLiquidacionDTO(Integer empleadoId, String apellido, String nombre,
                                 Double concepto1001, Double concepto1002,
                                 Double concepto1003, Double concepto1004, Double concepto1005) {
        this.empleadoId = empleadoId;
        this.apellido = apellido;
        this.nombre = nombre;
        this.concepto1001 = concepto1001;
        this.concepto1002 = concepto1002;
        this.concepto1003 = concepto1003;
        this.concepto1004 = concepto1004;
        this.concepto1005 = concepto1005;
    }

    // Getters y setters
    public Integer getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(Integer empleadoId) { this.empleadoId = empleadoId; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Double getConcepto1001() { return concepto1001; }
    public void setConcepto1001(Double concepto1001) { this.concepto1001 = concepto1001; }
    public Double getConcepto1002() { return concepto1002; }
    public void setConcepto1002(Double concepto1002) { this.concepto1002 = concepto1002; }
    public Double getConcepto1003() { return concepto1003; }
    public void setConcepto1003(Double concepto1003) { this.concepto1003 = concepto1003; }
    public Double getConcepto1004() { return concepto1004; }
    public void setConcepto1004(Double concepto1004) { this.concepto1004 = concepto1004; }
    public Double getConcepto1005() { return concepto1005; }
    public void setConcepto1005(Double concepto1005) { this.concepto1005 = concepto1005; }
}