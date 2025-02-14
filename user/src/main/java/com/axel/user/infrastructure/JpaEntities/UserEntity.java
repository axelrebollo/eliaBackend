package com.axel.user.infrastructure.JpaEntities;

import com.axel.user.domain.valueObjects.Role;
import jakarta.persistence.*;

@Entity
@Table(name= "user")
public class UserEntity {
    //Table from database

    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    private String email;
    private String password;
    private Role role;

    //constructor
    public UserEntity() {}

    public UserEntity(String email, String password, Role role) {
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
