package com.nomina.backend.model;

public enum EstadoEmpleado {
    ACTIVO("ACTIVO"),
    INACTIVO("INACTIVO"),
    SUSPENDIDO("SUSPENDIDO");

    private final String estado;

    EstadoEmpleado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
