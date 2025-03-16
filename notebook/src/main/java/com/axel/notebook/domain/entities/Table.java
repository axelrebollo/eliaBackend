package com.axel.notebook.domain.entities;

import java.util.UUID;

public class Table {
    //Attributes
    private int idTable;
    private String nameTable;
    private int idTeacher;
    private String classCode;
    private int idGroup;

    //Constructor
    public Table() {}

    public Table(String nameTable, int idTeacher, int idGroup) {
        this.nameTable = nameTable;
        this.idTeacher = idTeacher;
        this.classCode = UUID.randomUUID().toString();  //generate id unique for class.
        this.idGroup = idGroup;
    }

    public Table(int idTable, String nameTable, int idTeacher, String classCode, int idGroup) {
        this.idTable = idTable;
        this.nameTable = nameTable;
        this.idTeacher = idTeacher;
        this.classCode = classCode;
        this.idGroup = idGroup;
    }

    //getters
    public int getIdTable() {
        return idTable;
    }

    public String getNameTable() {
        return nameTable;
    }

    public int getIdTeacher() {
        return idTeacher;
    }

    public String getClassCode(){
        return classCode;
    }

    public int getIdGroup() {
        return idGroup;
    }

    //setters
    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }

    public void setIdTeacher(int idTeacher) {
        this.idTeacher = idTeacher;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }
}
