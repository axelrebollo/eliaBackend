package com.axel.notebook.application.DTOs;

public class DeleteResponse {
    //Attributes
    private boolean success;
    private String message;

    //Constructor
    public DeleteResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    //getters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    //setters
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
