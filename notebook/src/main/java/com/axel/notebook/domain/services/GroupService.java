package com.axel.notebook.domain.services;

import com.axel.notebook.domain.entities.Group;

public class GroupService {
    public Group addGroup(String nameGroup, int idCourse, int idSubject){
        return new Group(nameGroup, idCourse, idSubject);
    }
}
