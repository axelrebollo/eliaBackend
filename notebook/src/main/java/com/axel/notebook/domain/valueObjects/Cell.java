package com.axel.notebook.domain.valueObjects;

public class Cell {
    //attributes
    private int idCell;
    private String name;
    private double note;
    private int positionRow;
    private int positionCol;

    //constructors

    //if it uses this constructor is a student or task
    public Cell(String name, int positionRow, int positionCol, int idCell) {
        this.name = name;
        this.positionRow = positionRow;
        this.positionCol = positionCol;
        this.idCell = idCell;
    }

    //if it uses this constructor is a note
    public Cell(double note, int positionRow, int positionCol, int idCell) {
        this.note = note;
        this.positionRow = positionRow;
        this.positionCol = positionCol;
        this.idCell = idCell;
        this.name = "NOTE";
    }

    //getters
    public String getName() {
        return name;
    }

    public double getNote() {
        return note;
    }

    public int getPositionRow() {
        return positionRow;
    }

    public int getPositionCol() {
        return positionCol;
    }

    public int getIdCell() {
        return idCell;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public void setPositionRow(int positionRow) {
        this.positionRow = positionRow;
    }

    public void setPositionCol(int positionCol) {
        this.positionCol = positionCol;
    }

    public void setIdCell(int idCell) {
        this.idCell = idCell;
    }
}
