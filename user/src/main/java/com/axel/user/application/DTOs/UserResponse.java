package com.axel.user.application.DTOs;

public class UserResponse {
    //Attributes
    private String email;
    private String role;

    //Constructor
    public UserResponse(String email, String role) {
        this.email = email;
        this.role = role;
    }

    //Getters
    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
