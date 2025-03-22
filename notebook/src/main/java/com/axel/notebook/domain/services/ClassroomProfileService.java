package com.axel.notebook.domain.services;

import com.axel.notebook.domain.valueObjects.Student;
import com.axel.notebook.domain.valueObjects.ClassroomProfile;

public class ClassroomProfileService {
    public ClassroomProfile addClassroomProfile(String classCode, String nameYear, String nameCourse, String nameGroup,
                                                String nameSubject, String nameTable) {
        return new ClassroomProfile(classCode, nameYear, nameCourse, nameGroup, nameSubject, nameTable);
    }

    public Student addStudent(int idProfile, String name, String surname1, String surname2) {
        return new Student(idProfile, name, surname1, surname2);
    }
}
