package com.axel.notebook.application.repositories;

import java.util.List;

public interface ICellRepository {
    public List<Object[]> getCellsForIdTableAndType(int idTable, String type);

    public int[] getPositionsByIdCell(int idCell);
}
