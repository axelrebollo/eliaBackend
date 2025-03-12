package com.axel.notebook.application.DTOs;

import java.util.List;

public class CourseResponse {
    //Attributes
    private List<String> courses;

    //Constructor
    public CourseResponse(List<String> courses) {
        this.courses = courses;
    }

    //getters
    public List<String> getCourses() {
        return courses;
    }

    //setters
    public void setCourses(List<String> courses) {
        this.courses = courses;
    }
}
