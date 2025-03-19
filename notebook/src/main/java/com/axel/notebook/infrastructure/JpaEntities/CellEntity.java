package com.axel.notebook.infrastructure.JpaEntities;

import jakarta.persistence.*;

@Entity
@Table(name="cellEntity")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "cellType", discriminatorType = DiscriminatorType.STRING)
public abstract class CellEntity {
    //Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idCell")
    private int idCell;

    @Column(name="positionRow", nullable=false)
    private int positionRow;

    @Column(name="positionCol", nullable=false)
    private int positionCol;

    //foreign key idTable
    @ManyToOne
    @JoinColumn(name="idTable", nullable = false)
    private TableEntity table;

    //Constructors
    public CellEntity() {}

    public CellEntity(TableEntity table, int positionRow, int positionCol) {
        this.table = table;
        this.positionRow = positionRow;
        this.positionCol = positionCol;
    }

    //Getters
    public int getIdCell() {
        return idCell;
    }

    public TableEntity getTable() {
        return table;
    }

    public int getPositionRow() {
        return positionRow;
    }

    public int getPositionCol() {
        return positionCol;
    }

    //Setters
    public void setIdCell(int idCell) {
        this.idCell = idCell;
    }

    public void setTable(TableEntity table) {
        this.table = table;
    }

    public void setPositionRow(int positionRow) {
        this.positionRow = positionRow;
    }

    public void setPositionCol(int positionCol) {
        this.positionCol = positionCol;
    }
}
