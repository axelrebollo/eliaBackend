package com.axel.user.application.DTOs;

public class UserResponse {
    private String email;
    private String role;

    public UserResponse(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
