package com.axel.notebook.domain.services;

import com.axel.notebook.domain.valueObjects.Cell;
import com.axel.notebook.domain.valueObjects.Row;
import java.util.ArrayList;
import java.util.List;

public class CellService {
    public Cell addCellTaskOrStudent(String name, int positionRow, int positionCol, int idCellTask){
        return new Cell(name, positionRow, positionCol, idCellTask);
    }

    public Cell addCellNote(double note, int positionRow, int positionCol, int idCellNote){
        return new Cell(note, positionRow, positionCol, idCellNote);
    }

    public Row addRowStudent(Cell student){
        List<Cell> students = new ArrayList<>();
        students.add(student);
        return new Row(students);
    }
}
