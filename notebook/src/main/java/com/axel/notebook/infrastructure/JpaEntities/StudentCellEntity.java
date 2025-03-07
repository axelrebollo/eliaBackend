package com.axel.notebook.infrastructure.JpaEntities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("STUDENT")
public class StudentCellEntity extends CellEntity {

    //Attributes
    //Many to one between microservices
    @Column(name="student", nullable=false)
    private int idProfile;

    //Relation with note one to one
    @OneToOne(mappedBy = "studentCell", cascade = CascadeType.ALL)
    private NoteCellEntity note;

    //Constructors
    public StudentCellEntity() {}

    public StudentCellEntity(TableEntity table, int positionRow, int positionCol, int idProfile) {
        super(table, positionRow, positionCol);
        this.idProfile = idProfile;
    }

    //Getters
    public NoteCellEntity getNoteCell() {
        return note;
    }

    public int getIdProfile() {
        return idProfile;
    }

    //Setters
    public void setNoteCell(NoteCellEntity note) {
        this.note = note;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }
}
