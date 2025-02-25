package com.axel.user.application.repositories;

public interface IJWTRepository {
    public String generateToken(String email, String role);
    public boolean isTokenValid(String token, String email);
    public String getEmailFromToken(String token);
}
