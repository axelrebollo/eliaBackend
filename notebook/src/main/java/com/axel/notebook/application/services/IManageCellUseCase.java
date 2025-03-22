package com.axel.notebook.application.services;

import com.axel.notebook.application.DTOs.CellResponse;

public interface IManageCellUseCase {
    public CellResponse getCellsFromTableUseCase(String token, String nameSubject, String nameYear, String nameCourse,
                                                 String nameGroup, String nameTable);
}
