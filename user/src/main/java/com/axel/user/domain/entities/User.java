package com.axel.user.domain.entities;

import com.axel.user.domain.valueObjects.Role;

public class User {

    private long idUser;
    private String email;
    private String password;
    private Role role;

    //constructor
    public User() {}

    public User(String email, String password, Role role) {
        this.idUser = 0;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(long idUser, String email, String password, Role role) {
        this.idUser = idUser;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    //getters
    public Long getId() {
        return idUser;
    }

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
    public void setId(Long id) {
        this.idUser = id;
    }

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