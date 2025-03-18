package com.axel.notebook.application.repositories;

import com.axel.notebook.domain.valueObjects.ClassroomProfile;
import java.util.List;

public interface IClassroomProfileRepository {
    public List<ClassroomProfile> getTeacherDataByIdProfile(int idProfile);

    public List<ClassroomProfile> getStudentDataByIdProfile(int idProfile);
}
