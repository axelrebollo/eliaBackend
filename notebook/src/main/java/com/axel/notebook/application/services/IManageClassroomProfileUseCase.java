package com.axel.notebook.application.services;

import com.axel.notebook.application.DTOs.ClassroomProfileResponse;

public interface IManageClassroomProfileUseCase {
    //Calls other methods depends on role.
    public ClassroomProfileResponse getClassroomsForProfile(String token);

    public boolean enrollClassroomStudentUseCase(String token, String classCode);
}
