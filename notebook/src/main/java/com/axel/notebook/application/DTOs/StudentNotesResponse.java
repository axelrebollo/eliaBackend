package com.axel.notebook.application.DTOs;

import com.axel.notebook.domain.valueObjects.StudentClassroomNotes;
import java.util.List;

public class StudentNotesResponse {
    //Attributes
    private List<StudentClassroomNotes> classrooms;

    //Constructor
    public StudentNotesResponse(List<StudentClassroomNotes> classrooms) {
        this.classrooms = classrooms;
    }

    //Getters
    public List<StudentClassroomNotes> getClassrooms() {
        return classrooms;
    }

    //Setters
    public void setClassrooms(List<StudentClassroomNotes> classrooms) {
        this.classrooms = classrooms;
    }
}
