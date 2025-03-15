package com.axel.notebook.domain.entities;

public class Group {
    //Attributes
    private int idGroup;
    private String nameGroup;
    private int idCourse;
    private int idSubject;

    //Constructor
    public Group() {}

    public Group(String nameGroup, int idCourse, int idSubject) {
        this.nameGroup = nameGroup;
        this.idCourse = idCourse;
        this.idSubject = idSubject;
    }

    public Group(int idGroup, String nameGroup, int idCourse, int idSubject) {
        this.idGroup = idGroup;
        this.nameGroup = nameGroup;
        this.idCourse = idCourse;
        this.idSubject = idSubject;
    }

    //Getters
    public int getIdGroup() {
        return idGroup;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public int getIdCourse() {
        return idCourse;
    }

    public int getIdSubject() {
        return idSubject;
    }

    //Setters
    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }
}
