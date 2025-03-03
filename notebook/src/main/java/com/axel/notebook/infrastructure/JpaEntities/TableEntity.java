package com.axel.notebook.infrastructure.JpaEntities;

import jakarta.persistence.*;

@Entity
@Table(name= "tableEntity")
public class TableEntity {
    //Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idTable")
    private int idTable;

    @Column(name="nameTable")
    private String nameTable;

    //Many to one between microservices
    @Column(name="teacher", nullable=false)
    private int idProfile;

    @Column(name="classCode", nullable=false, unique=true)
    private String classCode;

    //Constructors
    public TableEntity() {}

    public TableEntity( int idTable, String nameTable, int idProfile, String classCode ) {
        this.idTable = idTable;
        this.nameTable = nameTable;
        this.idProfile = idProfile;
        this.classCode = generateUniqueClassCode();
    }

    //Setters
    public int getIdTable() {
        return idTable;
    }

    public String getNameTable() {
        return nameTable;
    }

    public int getIdProfile() {
        return idProfile;
    }

    public String getClassCode() {
        return classCode;
    }

    //Getters
    public void setIdTable( int idTable ) {
        this.idTable = idTable;
    }

    public void setNameTable( String nameTable ) {
        this.nameTable = nameTable;
    }

    public void setIdProfile( int idProfile ) {
        this.idProfile = idProfile;
    }

    //Generate a code for class
    private String generateUniqueClassCode() {
        return "CLS-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
