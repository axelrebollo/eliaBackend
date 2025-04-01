package com.axel.notebook.application.services;

import com.axel.notebook.application.DTOs.DeleteResponse;
import com.axel.notebook.application.DTOs.SubjectResponse;

public interface IManageSubjectUseCase {
    //get all subjects
    public SubjectResponse getAllSubjectsFromTokenUseCase(String token);

    //create a new subject
    public SubjectResponse addSubjectUseCase(String token, String nameSubject);

    //update name subject
    public void updateSubjectUseCase();

    //delete all information about this subject
    public DeleteResponse deleteSubjectUseCase(String token, String nameSubject);
}
