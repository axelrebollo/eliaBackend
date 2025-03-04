package com.axel.notebook.infrastructure.JpaEntities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "courseEntity")
public class CourseEntity {
    //Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idCourse")
    private int idCourse;

    @Column(name="nameCourse", nullable=false)
    private String nameCourse;

    //foreign key idYear
    @ManyToOne
    @JoinColumn(name="idYear", nullable = false)
    private YearEntity year;

    //relation with Groups
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupEntity> groups = new ArrayList<>();

    //Constructor
    public CourseEntity() {}

    public CourseEntity(String nameCourse, YearEntity year) {
        this.nameCourse = nameCourse;
        this.year = year;
    }

    //Getters
    public int getIdCourse() {
        return idCourse;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public YearEntity getYear() {
        return year;
    }

    public List<GroupEntity> getGroups() {
        return groups;
    }

    //Setters
    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public void setYear(YearEntity year) {
        this.year = year;
    }

    public void setGroups(List<GroupEntity> groups) {
        this.groups = groups;
    }
}
