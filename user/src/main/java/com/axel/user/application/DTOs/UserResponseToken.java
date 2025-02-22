package com.axel.user.application.DTOs;

public class UserResponseToken {
    private String token;

    public UserResponseToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
