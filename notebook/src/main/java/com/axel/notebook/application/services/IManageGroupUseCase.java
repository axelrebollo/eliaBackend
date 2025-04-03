package com.axel.notebook.application.services;

import com.axel.notebook.application.DTOs.DeleteResponse;
import com.axel.notebook.application.DTOs.GroupResponse;
import com.axel.notebook.application.DTOs.UpdateResponse;

public interface IManageGroupUseCase {
    //get all groups into course for one subject
    public GroupResponse getAllGroupsUseCase(String token, String nameCourse, String nameSubject, String nameYear);
    //add group
    public GroupResponse addGroupUseCase(String token, String nameCourse, String nameSubject, String nameYear, String nameGroup);

    public DeleteResponse deleteGroupUseCase(String token, String nameCourse, String nameSubject, String nameYear, String nameGroup);

    public UpdateResponse updateGroupUseCase(String token, String nameSubject, String nameYear, String nameCourse, String nameGroup, String newNameGroup);
}
