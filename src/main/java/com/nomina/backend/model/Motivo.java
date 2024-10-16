package com.nomina.backend.model;

public enum Motivo {
    RENUNCIA("RENUNCIA"),
    DESPIDO("DESPIDO"),
    OTRO("OTRO");

    private final String motivo;

    Motivo(String motivo) {
        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }
}
