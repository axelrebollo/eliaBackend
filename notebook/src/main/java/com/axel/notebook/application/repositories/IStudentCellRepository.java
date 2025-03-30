package com.axel.notebook.application.repositories;

import com.axel.notebook.domain.entities.Table;

public interface IStudentCellRepository {
    public int getIdStudentByIdCell(int idCell);

    public boolean deleteStudentRowIntoTable(Table table, int idProfile);
}
