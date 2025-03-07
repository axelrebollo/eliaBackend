package com.axel.notebook.application.DTOs;

import java.util.List;

public class YearResponse {
    //Attributes
    private List<String> years;

    //Constructor
    public YearResponse(List<String> years) {
        this.years = years;
    }

    //Getters
    public List<String> getYears() {
        return years;
    }

    //Setters
    public void setYears(List<String> years) {
        this.years = years;
    }
}
