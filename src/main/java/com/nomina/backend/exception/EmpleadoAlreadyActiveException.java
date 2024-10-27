package com.nomina.backend.exception;

public class EmpleadoAlreadyActiveException extends RuntimeException {
    private static final long serialVersionUID = 1L; 

    public EmpleadoAlreadyActiveException(String message) {
        super(message);
    }
}