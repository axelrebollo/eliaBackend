package com.axel.notebook.domain.valueObjects;

import java.util.List;

public class RowNotebook {
    //Attributes
    private Student student;
    private List<Note> notes;
    private List<Task> tasks;

    //Constructor
    public RowNotebook(Student student, List<Note> notes, List<Task> tasks) {
        this.student = student;
        this.notes = notes;
        this.tasks = tasks;
    }

    //getters
    public Student getStudent() {
        return student;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    //setters
    public void setStudent(Student student) {
        this.student = student;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
