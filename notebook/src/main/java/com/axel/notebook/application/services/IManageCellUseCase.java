package com.axel.notebook.application.services;

import com.axel.notebook.application.DTOs.CellResponse;
import com.axel.notebook.application.DTOs.DeleteResponse;
import com.axel.notebook.application.DTOs.UpdateResponse;

public interface IManageCellUseCase {
    public CellResponse getCellsFromTableUseCase(String token, String nameSubject, String nameYear, String nameCourse,
                                                 String nameGroup, String nameTable);

    public CellResponse addTaskUseCase(String token, String classCode, String nameNewTask, String nameReferenceTask,
                                       String nameSubject, String nameYear, String nameCourse, String nameGroup);

    public UpdateResponse updateNoteUseCase(String token, String classCode, String nameStudent, String nameTask, double newNote);

    public DeleteResponse deleteTaskColumnUseCase(String token, String classCode, int positionTaskColumn);

    public DeleteResponse deleteStudentTableUseCase(String token, String classCode, String nameStudent);
}
