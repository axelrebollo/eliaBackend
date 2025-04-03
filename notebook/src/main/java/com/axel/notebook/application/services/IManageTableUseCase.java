package com.axel.notebook.application.services;

import com.axel.notebook.application.DTOs.DeleteResponse;
import com.axel.notebook.application.DTOs.TableResponse;
import com.axel.notebook.application.DTOs.UpdateResponse;

public interface IManageTableUseCase {

    public TableResponse getAllTablesFromTokenUseCase(String token, String nameGroup, String nameCourse,
                                                      String nameSubject, String nameYear);

    public TableResponse addTableUseCase(String token, String nameTable, String nameGroup, String nameCourse,
                                         String nameSubject, String nameYear);

    public int getIdGroup(int idProfile, String nameGroup, String nameCourse, String nameSubject, String nameYear);

    public DeleteResponse deleteTableUseCase(String token, String classCode);

    public UpdateResponse updateTableUseCase(String token, String nameSubject, String nameYear, String nameCourse,
                                             String nameGroup, String nameTable, String newNameTable);
}
