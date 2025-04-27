package com.axel.notebook.domain.valueObjects;

import java.util.Map;

public class StudentClassroomNotes {
    //Attributes
    private String classCode;
    private String nameSubject;
    private Map<String,Double> notes;

    //Constructor
    public StudentClassroomNotes(String classCode, String nameSubject, Map<String,Double> notes) {
        this.classCode = classCode;
        this.nameSubject = nameSubject;
        this.notes = notes;
    }

    //getters
    public String getClassCode() {
        return classCode;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public Map<String,Double> getNotes() {
        return notes;
    }

    //Setters
    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public void setNotes(Map<String,Double> notes) {
        this.notes = notes;
    }
}
