package com.axel.notebook.application.repositories;

import com.axel.notebook.domain.valueObjects.Task;
import java.util.List;

public interface ICellRepository {
    //get all cells for idTable and type cell
    public List<Object[]> getCellsForIdTableAndType(int idTable, String type);

    //get position row and column from idCell
    public int[] getPositionsByIdCell(int idCell);

    //get task domain by idCell
    public Task getTaskByIdCell(int idCell);

    //move position column cell
    public void movePositionColCell(int idTaskCell, int newTaskPositionCol);

    //get id cell
    public int getIdCell(String name, String classCode, String type);

    //get id note cell
    public int getIdNoteCell(int idCellStudent, int idCellTask);

    //update note cell
    public int updateNote(int idCellNote, double newNote);

    //return boolean if exist task into table
    public boolean taskExistIntoTable(String classCode, String nameNewTask);

    public int updateNameTask(int positionCol, String classCode, String newNameTask);
}
