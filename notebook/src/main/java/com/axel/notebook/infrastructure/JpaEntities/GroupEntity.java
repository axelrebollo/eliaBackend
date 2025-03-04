package com.axel.notebook.infrastructure.JpaEntities;

import jakarta.persistence.*;

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
    @OneToOne
    @JoinColumn(name= "idTable", referencedColumnName = "idTable",nullable = false, unique = true)
    private TableEntity table;

    //constructor
    public GroupEntity() {}

    public GroupEntity(String nameGroup, CourseEntity course, TableEntity table, SubjectEntity subject) {
        this.nameGroup = nameGroup;
        this.course = course;
        this.table = table;
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

    public TableEntity getTable() {
        return table;
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

    public void setTable(TableEntity table) {
        this.table = table;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }
}
