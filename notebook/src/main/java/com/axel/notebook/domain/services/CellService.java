package com.axel.notebook.domain.services;

import com.axel.notebook.domain.exceptions.DomainException;
import com.axel.notebook.domain.valueObjects.Cell;
import com.axel.notebook.domain.valueObjects.Note;
import com.axel.notebook.domain.valueObjects.Row;
import com.axel.notebook.domain.valueObjects.Task;
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

    public Task addTask(String nameTask, int positionRow, int positionCol){
        if(nameTask.isEmpty() || positionRow != 0 || positionCol <= 0){
            throw new DomainException("Error al crear la tarea, hay algún dato erróneo.");
        }
        return new Task(nameTask, positionRow, positionCol);
    }

    public Note addNote(int positionRow, int positionCol, int idStudent, int idTask){
        if(positionRow <= 0 || positionCol <= 0 || idStudent <= 0 || idTask <= 0){
            throw new DomainException("La información para crear la nota es insuficiente.");
        }
        return new Note(-1, positionRow, positionCol, idStudent, idTask);
    }
}
