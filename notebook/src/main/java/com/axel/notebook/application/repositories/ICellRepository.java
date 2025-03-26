package com.axel.notebook.application.repositories;

import com.axel.notebook.domain.valueObjects.Task;

import java.util.List;

public interface ICellRepository {
    public List<Object[]> getCellsForIdTableAndType(int idTable, String type);

    public int[] getPositionsByIdCell(int idCell);

    public Task getTaskByIdCell(int idCell);

    public void movePositionColCell(int idTaskCell, int newTaskPositionCol);
}
