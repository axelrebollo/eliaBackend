package com.axel.notebook.domain.entities;

public class Year {
    //Attributes
    private int idYear;
    private String nameYear;
    private int idProfile;

    //Constructors
    public Year() {}

    public Year(String nameYear, int idProfile) {
        this.nameYear = nameYear;
        this.idProfile = idProfile;
    }

    public Year(int idYear, String nameYear, int idProfile) {
        this.idYear = idYear;
        this.nameYear = nameYear;
        this.idProfile = idProfile;
    }

    //Getters
    public int getIdYear() {
        return idYear;
    }

    public String getNameYear() {
        return nameYear;
    }

    public int getIdProfile() {
        return idProfile;
    }

    //Setters
    public void setIdYear(int idYear) {
        this.idYear = idYear;
    }

    public void setNameYear(String nameYear) {
        this.nameYear = nameYear;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }
}
