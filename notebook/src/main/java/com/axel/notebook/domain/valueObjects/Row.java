package com.axel.notebook.domain.valueObjects;

import java.util.List;

public class Row {
    //Attributes
    private List<Cell> rowNotebook;

    //Constructor
    public Row(List<Cell> rowNotebook) {
        this.rowNotebook = rowNotebook;
    }

    //Getters
    public List<Cell> getRowNotebook() {
        return rowNotebook;
    }

    //Setters
    public void setRowNotebook(List<Cell> rowNotebook) {
        this.rowNotebook = rowNotebook;
    }
}

