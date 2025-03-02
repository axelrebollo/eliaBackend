package com.axel.user.application.repositories;

public interface IJWTRepository {
    //Generates JWT token from email and role
    public String generateToken(String email, String role);

    //Check if token is correct
    public boolean isTokenValid(String token, String email);

    //Obtains email from token
    public String getEmailFromToken(String token);
}
