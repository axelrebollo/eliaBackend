package com.axel.notebook.infrastructure.JpaEntities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="yearEntity")
public class YearEntity {
    //Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idYear")
    private int idYear;

    @Column(name="nameYear", nullable=false)
    private String nameYear;

    //relation with courses
    @OneToMany(mappedBy = "year", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseEntity> courses = new ArrayList<>();

    //Constructors
    public YearEntity() {}

    public YearEntity(String nameYear) {
        this.nameYear = nameYear;
    }

    //getters
    public int getIdYear() {
        return idYear;
    }

    public String getNameYear() {
        return nameYear;
    }

    public List<CourseEntity> getCourses() {
        return courses;
    }

    //setters
    public void setIdYear(int idYear) {
        this.idYear = idYear;
    }

    public void setNameYear(String nameYear) {
        this.nameYear = nameYear;
    }

    public void setCourses(List<CourseEntity> courses) {
        this.courses = courses;
    }
}
