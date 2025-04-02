package com.axel.notebook.application.services;

import com.axel.notebook.application.DTOs.DeleteResponse;
import com.axel.notebook.application.DTOs.SubjectResponse;
import com.axel.notebook.application.DTOs.UpdateResponse;

public interface IManageSubjectUseCase {
    //get all subjects
    public SubjectResponse getAllSubjectsFromTokenUseCase(String token);

    //create a new subject
    public SubjectResponse addSubjectUseCase(String token, String nameSubject);

    //delete all information about this subject
    public DeleteResponse deleteSubjectUseCase(String token, String nameSubject);

    //update name subject
    public UpdateResponse updateSubjectUseCase(String token, String nameSubject, String newNameSubject);
}
