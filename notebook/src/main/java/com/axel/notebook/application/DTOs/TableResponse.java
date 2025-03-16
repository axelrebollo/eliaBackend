package com.axel.notebook.application.DTOs;

import java.util.List;

public class TableResponse {
    //Attributes
    private List<String> tables;

    //Constructor
    public TableResponse(List<String> tables) {
        this.tables = tables;
    }

    //getters
    public List<String> getTables() {
        return tables;
    }

    //setters
    public void setTables(List<String> tables) {
        this.tables = tables;
    }
}
