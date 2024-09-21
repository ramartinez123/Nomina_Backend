package com.nomina.backend.model;

public enum EstadoCivil {
    SOLTERO("soltero"),
    CASADO("casado"),
    DIVORCIADO("divorciado"),
    VIUDO("viudo");

    private final String estado;

    EstadoCivil(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
