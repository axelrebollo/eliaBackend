package com.axel.notebook.domain.valueObjects;

public class Note {
    //Attributes
    private int idNote;
    private double note;
    private int positionRow;
    private int positionCol;
    private int idStudent;
    private int idTask;

    //Constructor
    public Note(int idNote, double note, int positionRow, int positionCol, int idStudent, int idTask) {
        this.idNote = idNote;
        this.note = note;
        this.positionRow = positionRow;
        this.positionCol = positionCol;
        this.idStudent = idStudent;
        this.idTask = idTask;
    }

    public Note(double note, int positionRow, int positionCol, int idStudent, int idTask) {
        this.note = note;
        this.positionRow = positionRow;
        this.positionCol = positionCol;
        this.idStudent = idStudent;
        this.idTask = idTask;
    }

    //getters
    public int getIdNote() {
        return idNote;
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

    public int getIdStudent() {
        return idStudent;
    }

    public int getIdTask() {
        return idTask;
    }

    //setters
    public void setIdNote(int idNote) {
        this.idNote = idNote;
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

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }
}
