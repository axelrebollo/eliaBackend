package com.axel.notebook.application.repositories;

import com.axel.notebook.domain.valueObjects.Student;
import com.axel.notebook.domain.valueObjects.ClassroomProfile;
import java.util.List;

public interface IClassroomProfileRepository {
    //get data with teacher id profile
    public List<ClassroomProfile> getTeacherDataByIdProfile(int idProfile);

    //get data with student id profile
    public List<ClassroomProfile> getStudentDataByIdProfile(int idProfile);

    //return boolean if student is into class
    public boolean enrollStudentToTable(Student student, String classCode);
}
