package com.axel.user.infrastructure.JpaEntities;

import jakarta.persistence.*;

@Entity
@Table(name="profileEntity")
public class ProfileEntity {

    //attrubites
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "idProfile")
    private int idProfile;

    @OneToOne
    @JoinColumn(name= "idUser", nullable = false, unique = true)
    private UserEntity user;

    @Column(name= "name")
    private String name;

    @Column(name= "surname1")
    private String surname1;

    @Column(name= "surname2")
    private String surname2;

    //constructors
    public ProfileEntity() {}

    public ProfileEntity(UserEntity user, String name, String surname1, String surname2) {
        this.user = user;
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
    }

    public ProfileEntity(int idProfile, UserEntity user, String name, String surname1, String surname2) {
        this.idProfile = idProfile;
        this.user = user;
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
    }

    //getters
    public int getId() {
        return idProfile;
    }

    public String getName(){
        return name;
    }

    public String getSurname1(){
        return surname1;
    }

    public String getSurname2(){
        return surname2;
    }

    public UserEntity getUser(){
        return user;
    }

    //setters
    public void setId(int id) {
        this.idProfile = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSurname1(String surname1){
        this.surname1 = surname1;
    }

    public void setSurname2(String surname2){
        this.surname2 = surname2;
    }

    public void setUser(UserEntity user){
        this.user = user;
    }
}
