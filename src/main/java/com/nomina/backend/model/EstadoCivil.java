package com.nomina.backend.model;

public enum EstadoCivil {
    SOLTERO("SOLTERO"),
    CASADO("CASADO"),
    DIVORCIADO("DIVORCIADO"),
    VIUDO("VIUDO");

    private final String estado;

    EstadoCivil(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
