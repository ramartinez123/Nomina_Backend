package com.nomina.backend.model;

public enum TipoContrato {
    PERMANENTE("PERMANENTE"),
    TEMPORA("TEMPORA"),
    CONTRATO("CONTRATO"),
    PASANTE("PASANTE");

    private final String tipo;

    TipoContrato(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}