package com.axel.notebook.domain.valueObjects;

public class Task {
    //Attributes
    private int idTask;
    private String nameTask;

    //Constructor
    public Task(int idTask, String nameTask) {
        this.idTask = idTask;
        this.nameTask = nameTask;
    }

    //getters
    public int getIdTask() {
        return idTask;
    }

    public String getNameTask() {
        return nameTask;
    }

    //setters
    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }
}
