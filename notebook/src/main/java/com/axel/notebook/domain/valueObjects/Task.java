package com.axel.notebook.domain.valueObjects;

public class Task {
    //Attributes
    private int idTask;
    private String nameTask;
    private int positionRow;
    private int positionCol;

    //Constructor
    public Task(int idTask, String nameTask, int positionRow, int positionCol) {
        this.idTask = idTask;
        this.nameTask = nameTask;
        this.positionRow = positionRow;
        this.positionCol = positionCol;
    }

    //getters
    public int getIdTask() {
        return idTask;
    }

    public String getNameTask() {
        return nameTask;
    }

    public int getPositionRow() {
        return positionRow;
    }

    public int getPositionCol() {
        return positionCol;
    }

    //setters
    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public void setPositionRow(int positionRow) {
        this.positionRow = positionRow;
    }

    public void setPositionCol(int positionCol) {
        this.positionCol = positionCol;
    }
}
