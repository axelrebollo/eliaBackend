package com.axel.notebook.infrastructure.JpaEntities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("NOTE")
public class NoteCellEntity extends CellEntity{

    //Attributes
    @Column(name = "note", nullable = false)
    private Double note;

    //Relation with student one to one
    @OneToOne
    @JoinColumn(name = "student_cell_id", unique = true)
    private StudentCellEntity studentCell;

    //Relation with task one to one
    @OneToOne
    @JoinColumn(name = "task_cell_id", unique = true)
    private TaskCellEntity taskCell;

    //Constructors
    public NoteCellEntity() {}

    public NoteCellEntity(TableEntity table, int positionRow, int positionCol, Double note) {
        super(table, positionRow, positionCol);
        this.note = note;
    }

    //Getters
    public Double getNote() {
        return note;
    }

    public StudentCellEntity getStudentCell() {
        return studentCell;
    }

    public TaskCellEntity getTaskCell() {
        return taskCell;
    }

    //Setters
    public void setNote(Double note) {
        this.note = note;
    }

    public void setStudentCell(StudentCellEntity studentCell) {
        this.studentCell = studentCell;
    }

    public void setTaskCell(TaskCellEntity taskCell) {
        this.taskCell = taskCell;
    }
}
