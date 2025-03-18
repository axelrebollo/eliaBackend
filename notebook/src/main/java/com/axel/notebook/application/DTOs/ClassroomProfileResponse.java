package com.axel.notebook.application.DTOs;

import com.axel.notebook.domain.valueObjects.ClassroomProfile;

import java.util.List;

public class ClassroomProfileResponse {
    //Attributes
    private List<ClassroomProfile> rows;

    //Constructor
    public ClassroomProfileResponse(List<ClassroomProfile> rows) {
        this.rows = rows;
    }

    //getters
    public List<ClassroomProfile> getRows() {
        return rows;
    }

    //setters
    public void setRows(List<ClassroomProfile> rows) {
        this.rows = rows;
    }
}
