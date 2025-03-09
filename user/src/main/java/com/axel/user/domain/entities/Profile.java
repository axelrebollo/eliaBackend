package com.axel.user.domain.entities;

import com.axel.user.domain.exceptions.DomainException;

public class Profile {
    //Attributes
    private int idProfile;
    private int idUser;
    private String name;
    private String surname1;
    private String surname2;

    //constructors
    public Profile() {}

    public Profile(int idUser, String name, String surname1, String surname2) {
        this.idUser = idUser;
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
    }

    public Profile(int idProfile, int idUser, String name, String surname1, String surname2) {
        this.idProfile = idProfile;
        this.idUser = idUser;
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
    }

    //getters
    public int getIdProfile() {
        return idProfile;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public String getSurname1() {
        return surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    //setters
    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public Profile initializeNotNull(String name, String surname1, String surname2){
        if(name == null || name.isEmpty() ||
        surname1 == null || surname1.isEmpty() ||
        surname2 == null || surname2.isEmpty()){
            throw new DomainException("Valores insertados nulos o vacíos");
        }
        this.setName(name);
        this.setSurname1(surname1);
        this.setSurname2(surname2);
        return this;
    }
}
