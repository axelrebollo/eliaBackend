package com.axel.notebook.domain.entities;

public class Subject {
    //Attributes
    private int idSubject;
    private String nameSubject;
    private int idProfile;

    //Constructors
    public Subject() {}

    public Subject(String nameSubject, int idProfile) {
        this.nameSubject = nameSubject;
        this.idProfile = idProfile;
    }

    public Subject(int idSubject, String nameSubject, int idProfile) {
        this.idSubject = idSubject;
        this.nameSubject = nameSubject;
        this.idProfile = idProfile;
    }

    //Getters
    public int getIdSubject() {
        return idSubject;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public int getIdProfile() {
        return idProfile;
    }

    //Setters
    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }
}
