package com.nomina.backend.model;

public enum TipoCuentaBancaria {
    CUENTA_CORRIENTE("CUENTA_CORRIENTE"),
    CAJA_AHORRO("CAJA_AHORRO"),
    OTRA("OTRA");

    private final String tipo;

    TipoCuentaBancaria(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}