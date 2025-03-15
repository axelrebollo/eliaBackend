package com.axel.notebook.application.DTOs;

import java.util.List;

public class GroupResponse {
    //Attributes
    private List<String> groups;

    //constructor
    public GroupResponse(List<String> groups) {
        this.groups = groups;
    }

    //getters
    public List<String> getGroups() {
        return groups;
    }

    //setters
    public void setGroups(List<String> groups) {
        this.groups = groups;
    }
}
