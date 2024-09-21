package com.nomina.backend.model;

public enum TipoContrato {
    PERMANENTE("permanente"),
    TEMPORA("tempora"),
    CONTRATO("contrato"),
    PASANTE("pasante");

    private final String tipo;

    TipoContrato(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}