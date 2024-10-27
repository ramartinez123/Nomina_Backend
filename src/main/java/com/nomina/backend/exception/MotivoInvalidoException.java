package com.nomina.backend.exception;

public class MotivoInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MotivoInvalidoException(String message) {
        super(message);
    }
}