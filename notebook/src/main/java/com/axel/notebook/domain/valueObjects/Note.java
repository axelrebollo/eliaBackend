package com.axel.notebook.domain.valueObjects;

public class Note {
    //Attributes
    private int idNote;
    private double note;

    //Constructor
    public Note(int idNote, double note) {
        this.idNote = idNote;
        this.note = note;
    }

    //getters
    public int getIdNote() {
        return idNote;
    }

    public double getNote() {
        return note;
    }

    //setters
    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public void setNote(double note) {
        this.note = note;
    }
}
