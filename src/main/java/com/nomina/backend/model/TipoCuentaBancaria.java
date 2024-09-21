package com.nomina.backend.model;

public enum TipoCuentaBancaria {
    CUENTA_CORRIENTE("cuenta_corriente"),
    CAJA_AHORRO("caja_ahorro"),
    OTRA("otra");

    private final String tipo;

    TipoCuentaBancaria(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}