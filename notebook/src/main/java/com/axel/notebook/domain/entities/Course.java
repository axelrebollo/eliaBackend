package com.axel.notebook.domain.entities;

public class Course {
    //Attributes
    private int idCourse;
    private String nameCourse;
    private int idProfile;
    private int idYear;

    //Constructor
    public Course() {}

    public Course(String nameCourse, int idProfile, int idYear){
        this.nameCourse = nameCourse;
        this.idProfile = idProfile;
        this.idYear = idYear;
    }

    public Course(int idCourse, String nameCourse, int idProfile, int idYear) {
        this.idCourse = idCourse;
        this.nameCourse = nameCourse;
        this.idProfile = idProfile;
        this.idYear = idYear;
    }

    //getters
    public int getIdCourse() {
        return idCourse;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public int getIdProfile() {
        return idProfile;
    }

    public int getIdYear() {
        return idYear;
    }

    //setters
    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public void setIdYear(int idYear) {
        this.idYear = idYear;
    }
}
