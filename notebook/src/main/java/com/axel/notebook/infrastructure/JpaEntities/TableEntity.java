package com.axel.notebook.infrastructure.JpaEntities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "tableEntity")
public class TableEntity {
    //Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idTable")
    private int idTable;

    @Column(name="nameTable", nullable=false)
    private String nameTable;

    //Many to one between microservices
    @Column(name="teacher", nullable=false)
    private int idProfile;

    @Column(name="classCode", nullable=false, unique=true)
    private String classCode;

    //Conect with GroupEntity one to one
    @ManyToOne
    @JoinColumn(name= "idGroup", nullable = false)
    private GroupEntity group;

    //relation with Cells
    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CellEntity> cells = new ArrayList<>();

    //Constructors
    public TableEntity() {}

    public TableEntity(String nameTable, int idProfile, GroupEntity group) {
        this.nameTable = nameTable;
        this.idProfile = idProfile;
        this.classCode = generateUniqueClassCode();
        this.group = group;
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

    public GroupEntity getGroup() {
        return group;
    }

    public List<CellEntity> getCells() {
        return cells;
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

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    //Generate a code for class
    private String generateUniqueClassCode() {
        return "CLS-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void setCells(List<CellEntity> cells) {
        this.cells = cells;
    }
}
