package com.axel.notebook.application.DTOs;

import com.axel.notebook.domain.valueObjects.Row;
import com.axel.notebook.domain.valueObjects.RowNotebook;
import java.util.List;

public class CellResponse {
    //Attributes
    private List<Row> tableCells;

    //Constructor
    public CellResponse(List<Row> tableCells) {
        this.tableCells = tableCells;
    }

    //Getters
    public List<Row> getTableCells() {
        return tableCells;
    }

    //Setters
    public void setTableCells(List<Row> tableCells) {
        this.tableCells = tableCells;
    }
}
