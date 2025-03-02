package com.axel.user.application.DTOs;

public class UserResponseToken {
    //Attributes
    private String token;

    //Constructor
    public UserResponseToken(String token) {
        this.token = token;
    }

    //Getters
    public String getToken() {
        return token;
    }
}
