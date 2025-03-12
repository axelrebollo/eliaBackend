package com.axel.notebook.application.DTOs;

import java.util.List;

public class SubjectResponse {
    //Attributes
    private List<String> subjects;

    //Constructor
    public SubjectResponse(List<String> subjects) {
        this.subjects = subjects;
    }

    //Getters
    public List<String> getSubjects() {
        return subjects;
    }

    //Setters
    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }
}
