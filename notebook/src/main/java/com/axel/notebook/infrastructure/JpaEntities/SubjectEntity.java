package com.axel.notebook.infrastructure.JpaEntities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="subjectEntity")
public class SubjectEntity {
    //Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idSubject")
    private int idSubject;

    @Column(name="nameSubject", nullable=false)
    private String nameSubject;

    @Column(name="teacher")
    private int idProfile;

    //relation with groups
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupEntity> groups = new ArrayList<>();

    //Constructors
    public SubjectEntity() {}

    public SubjectEntity(String nameSubject, int idProfile) {
        this.nameSubject = nameSubject;
        this.idProfile = idProfile;
    }

    //Getters
    public int getIdSubject() {
        return idSubject;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public List<GroupEntity> getGroups() {
        return groups;
    }

    public int getIdProfile() {
        return idProfile;
    }

    //Setters
    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public void setGroups(List<GroupEntity> groups) {
        this.groups = groups;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }
}
