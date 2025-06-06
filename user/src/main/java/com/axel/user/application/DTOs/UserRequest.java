package com.axel.user.application.DTOs;

public class UserRequest {
    //attributes
    private String email;
    private String password;
    private String role;

    //Constructor
    public UserRequest(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    //getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    //setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
