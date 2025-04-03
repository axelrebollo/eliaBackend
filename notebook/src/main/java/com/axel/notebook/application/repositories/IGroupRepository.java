package com.axel.notebook.application.repositories;

import com.axel.notebook.domain.entities.Group;
import java.util.List;

public interface IGroupRepository {

    public List<String> getAllGroupsForSubjectAndCourse(int idSubject, int idCourse);

    public boolean existGroup(int idCourse, int idSubject, String nameGroup);

    public Group updateGroup(Group newGroup);

    public Group getGroup(int idCourse, int idSubject, String nameGroup);

    public boolean deleteGroup(int idProfile, String nameCourse, String nameSubject, String nameYear, String nameGroup);

    public int updateNameGroup(int idProfile, String nameSubject, String nameYear, String nameCourse, String nameGroup, String newNameGroup);
}
