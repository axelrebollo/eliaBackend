package com.axel.user.domain.exceptions;

public class UserCreationException extends RuntimeException {
    String errorMessage;

    public UserCreationException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
