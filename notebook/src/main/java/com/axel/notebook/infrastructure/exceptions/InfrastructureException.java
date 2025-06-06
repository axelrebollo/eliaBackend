package com.axel.notebook.infrastructure.exceptions;

public class InfrastructureException extends RuntimeException {
    public InfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }

    public InfrastructureException(String message) {
        super(message);
    }
}
