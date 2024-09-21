package com.nomina.backend.model;

public enum Genero {
    MASCULINO("masculino"),
    FEMENINO("femenino"),
    OTRO("otro");

    private final String genero;

    Genero(String genero) {
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }
}
