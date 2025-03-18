package com.axel.notebook.domain.valueObjects;

public class ClassroomProfile {
    //Attributes
    private String classCode;
    private String nameYear;
    private String nameCourse;
    private String nameGroup;
    private String nameSubject;
    private String nameTable;

    //Constructor
    public ClassroomProfile(String classCode, String nameYear, String nameCourse, String nameGroup,
                                    String nameSubject, String nameTable) {
        this.classCode = classCode;
        this.nameYear = nameYear;
        this.nameCourse = nameCourse;
        this.nameGroup = nameGroup;
        this.nameSubject = nameSubject;
        this.nameTable = nameTable;
    }

    //Getters
    public String getClassCode() {
        return classCode;
    }

    public String getNameYear() {
        return nameYear;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public String getNameTable() {
        return nameTable;
    }

    //Setters
    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public void setNameYear(String nameYear) {
        this.nameYear = nameYear;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }
}
