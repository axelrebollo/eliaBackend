package com.axel.notebook.application.services;

import com.axel.notebook.application.DTOs.TableResponse;

public interface IManageTableUseCase {

    public TableResponse getAllTablesFromTokenUseCase(String token, String nameGroup, String nameCourse, String nameSubject, String nameYear);

    public TableResponse addTableUseCase(String token, String nameTable, String nameGroup, String nameCourse, String nameSubject, String nameYear);
}
