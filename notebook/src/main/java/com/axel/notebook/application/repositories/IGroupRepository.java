package com.axel.notebook.application.repositories;

import com.axel.notebook.domain.entities.Group;

import java.util.List;

public interface IGroupRepository {

    public List<String> getAllGroupsForSubjectAndCourse(int idSubject, int idCourse);

    public boolean existGroup(int idCourse, int idSubject, String nameGroup);

    public Group updateGroup(Group newGroup);
}
