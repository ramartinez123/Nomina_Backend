package com.nomina.backend.model;

public enum EstadoEmpleado {
    ACTIVO("activo"),
    INACTIVO("inactivo"),
    SUSPENDIDO("suspendido");

    private final String estado;

    EstadoEmpleado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
