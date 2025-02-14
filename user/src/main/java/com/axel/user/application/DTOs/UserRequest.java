package com.axel.user.application.DTOs;

import com.axel.user.domain.valueObjects.Role;

public class UserRequest {

    //attributes
    private String email;
    private String password;
    private Role role;

    //getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    //setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
