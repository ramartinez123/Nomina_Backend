package com.nomina.backend.exception;

public class EmpleadoNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L; 
	
    public EmpleadoNotFoundException(String message) {
        super(message);
    }
}
