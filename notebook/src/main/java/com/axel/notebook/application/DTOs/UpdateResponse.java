package com.axel.notebook.application.DTOs;

public class UpdateResponse {
    //Atributes
    private int id;

    //constructor
    public UpdateResponse(int id) {
        this.id = id;
    }

    //getters
    public int getId() {
        return id;
    }

    //setters
    public void setId(int id) {
        this.id = id;
    }
}
