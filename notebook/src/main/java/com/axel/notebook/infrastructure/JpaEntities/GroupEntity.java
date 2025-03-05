package com.axel.notebook.infrastructure.JpaEntities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "groupEntity")
public class GroupEntity {
    //Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idGroup")
    private int idGroup;

    @Column(name="nameGroup", nullable=false)
    private String nameGroup;

    //foreign key idCourse
    @ManyToOne
    @JoinColumn(name= "idCourse", nullable=false)
    private CourseEntity course;

    //foreign key idSubject
    @ManyToOne
    @JoinColumn(name="idSubject", nullable=false)
    private SubjectEntity subject;

    //relation one to one with table
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TableEntity> tables = new ArrayList<>();

    //constructor
    public GroupEntity() {}

    public GroupEntity(String nameGroup, CourseEntity course, SubjectEntity subject) {
        this.nameGroup = nameGroup;
        this.course = course;
        this.subject = subject;
    }

    //Getters
    public int getIdGroup() {
        return idGroup;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public List<TableEntity> getTables() {
        return tables;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    //Setters
    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public void setTables(List<TableEntity> tables) {
        this.tables = tables;
    }

    public void addTable(TableEntity table) {
        this.tables.add(table);
        table.setGroup(this);
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }
}
