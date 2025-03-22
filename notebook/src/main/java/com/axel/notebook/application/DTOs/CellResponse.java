package com.axel.notebook.application.DTOs;

import com.axel.notebook.domain.valueObjects.RowNotebook;
import java.util.List;

public class CellResponse {
    //Attributes
    private List<RowNotebook> tableCells;

    //Constructor
    public CellResponse(List<RowNotebook> tableCells) {
        this.tableCells = tableCells;
    }

    //Getters
    public List<RowNotebook> getTableCells() {
        return tableCells;
    }

    //Setters
    public void setTableCells(List<RowNotebook> tableCells) {
        this.tableCells = tableCells;
    }
}
