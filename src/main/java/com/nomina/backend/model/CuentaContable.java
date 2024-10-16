package com.nomina.backend.model;

public enum CuentaContable {
    SUELDOS("SUELDOS"),
    APORTES("APORTES"),
    LICENCIAS("LICENCIAS"),
    IMPGANANCIAS("IMPGANANCIAS"),
    RETENCIONES("RETENCIONES"),
    NO("NO"),
    SUELDONETO("SUELDONETO");

    private final String nombre;

    CuentaContable(String nombre) {
        this.nombre = nombre;
        
    }

    public String getNombre() {
        return nombre;
    }
}
