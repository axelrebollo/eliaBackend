package com.axel.notebook.domain.services;

import com.axel.notebook.domain.valueObjects.ClassroomProfile;

public class ClassroomProfileService {
    public ClassroomProfile addClassroomProfile(String classCode, String nameYear, String nameCourse, String nameGroup,
                                                String nameSubject, String nameTable) {
        return new ClassroomProfile(classCode, nameYear, nameCourse, nameGroup, nameSubject, nameTable);
    }
}
