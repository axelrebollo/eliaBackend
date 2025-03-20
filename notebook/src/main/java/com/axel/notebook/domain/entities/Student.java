package com.axel.notebook.domain.entities;

public class Student {
    //Attributes
    private int idProfile;
    private String name;
    private String surname1;
    private String surname2;

    //constructor
    public Student() {}

    public Student(String name, String surname1, String surname2) {
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
    }

    public Student(int id, String name, String surname1, String surname2) {
        this.idProfile = id;
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
    }

    //getters
    public int getIdProfile() {
        return idProfile;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }
}
