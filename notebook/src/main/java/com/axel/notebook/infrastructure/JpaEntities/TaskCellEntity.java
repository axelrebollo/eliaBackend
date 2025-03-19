package com.axel.notebook.infrastructure.JpaEntities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("TASK")
public class TaskCellEntity extends CellEntity {

    //Attributes
    @Column(name="nameTask", nullable=false)
    private String nameTask;

    //Relation with note one to one
    @OneToMany(mappedBy = "taskCell", cascade = CascadeType.ALL)
    private List<NoteCellEntity> noteCell;

    //Constructors
    public TaskCellEntity() {}

    public TaskCellEntity(TableEntity table, int positionRow, int positionCol, String nameTask) {
        super(table, positionRow, positionCol);
        this.nameTask = nameTask;
    }

    //Getters
    public List<NoteCellEntity> getNoteCell() {
        return noteCell;
    }

    public String getNameTask() {
        return nameTask;
    }

    //Setters
    public void setNoteCell(List<NoteCellEntity> noteCell) {
        this.noteCell = noteCell;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }
}
