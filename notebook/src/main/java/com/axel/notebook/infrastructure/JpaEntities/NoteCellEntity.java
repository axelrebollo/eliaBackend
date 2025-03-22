package com.axel.notebook.infrastructure.JpaEntities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("NOTE")
public class NoteCellEntity extends CellEntity{

    //Attributes
    @Column(name = "note", nullable = false)
    private double note;

    //Relation with student many to one
    @ManyToOne
    @JoinColumn(name = "student_cell_id", nullable = false)
    private StudentCellEntity studentCell;

    //Relation with task one to one
    @ManyToOne
    @JoinColumn(name = "task_cell_id", nullable = false)
    private TaskCellEntity taskCell;

    //Constructors
    public NoteCellEntity() {}

    public NoteCellEntity(TableEntity table, int positionRow, int positionCol, double note) {
        super(table, positionRow, positionCol);
        this.note = note;
    }

    //Getters
    public double getNote() {
        return note;
    }

    public StudentCellEntity getStudentCell() {
        return studentCell;
    }

    public TaskCellEntity getTaskCell() {
        return taskCell;
    }

    //Setters
    public void setNote(double note) {
        this.note = note;
    }

    public void setStudentCell(StudentCellEntity studentCell) {
        this.studentCell = studentCell;
    }

    public void setTaskCell(TaskCellEntity taskCell) {
        this.taskCell = taskCell;
    }
}
