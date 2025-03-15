package com.axel.notebook.application.services;

import com.axel.notebook.application.DTOs.GroupResponse;

public interface IManageGroupUseCase {
    //get all groups into course for one subject
    public GroupResponse getAllGroupsUseCase(String token, String nameCourse, String nameSubject, String nameYear);
}
