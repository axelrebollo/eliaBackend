package com.axel.user.application.DTOs;

public class UserApplication {
    //Attributes
    private int idUser;
    private String email;
    private String password;
    private String role;

    //constructors
    public UserApplication() {}

    public UserApplication(String email, String password, String role) {
        this.idUser = 0;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserApplication(int idUser, String email, String password, String role) {
        this.idUser = idUser;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    //getters
    public int getId() {
        return idUser;
    }

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
    public void setId(int id) {
        this.idUser = id;
    }

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
