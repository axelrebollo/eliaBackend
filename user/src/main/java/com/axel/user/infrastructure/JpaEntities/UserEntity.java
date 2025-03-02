package com.axel.user.infrastructure.JpaEntities;

import jakarta.persistence.*;

@Entity
@Table(name= "userEntity")
public class UserEntity {

    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "idUser")
    private int idUser;

    @Column(name="email", nullable=false, unique=true)
    private String email;

    @Column(name="password", nullable=false)
    private String password;

    @Column(name="role", nullable=false)
    private String role;

    //constructor
    public UserEntity() {}

    public UserEntity(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserEntity(int idUser, String email, String password, String role) {
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
