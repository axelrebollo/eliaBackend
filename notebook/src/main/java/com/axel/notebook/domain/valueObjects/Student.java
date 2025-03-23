package com.axel.notebook.domain.valueObjects;

public class Student {
    //Attributes
    private int idProfile;
    private int idCellStudent;
    private String name;
    private String surname1;
    private String surname2;
    private int positionRow;
    private int positionCol;

    //constructor
    public Student() {}

    public Student(String name, String surname1, String surname2) {
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
    }

    public Student(int id, String name, String surname1, String surname2) {
        this.idProfile = id;
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
    }

    public Student(int id, String name, String surname1, String surname2, int idCellStudent) {
        this.idProfile = id;
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.idCellStudent = idCellStudent;
    }

    public Student(String name, String surname1, String surname2, int positionRow, int positionCol, int idCellStudent) {
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.positionRow = positionRow;
        this.positionCol = positionCol;
        this.idCellStudent = idCellStudent;
    }

    //getters
    public int getIdProfile() {
        return idProfile;
    }

    public String getName() {
        return name;
    }

    public String getSurname1() {
        return surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public int getIdCellStudent() {
        return idCellStudent; }

    public int getPositionRow() {
        return positionRow;
    }

    public int getPositionCol() {
        return positionCol;
    }

    //setters
    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public void setIdCellStudent(int idCellStudent) {
        this.idCellStudent = idCellStudent;
    }

    public void setPositionRow(int positionRow) {
        this.positionRow = positionRow;
    }

    public void setPositionCol(int positionCol) {
        this.positionCol = positionCol;
    }
}
