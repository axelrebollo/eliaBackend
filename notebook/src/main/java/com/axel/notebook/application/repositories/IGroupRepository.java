package com.axel.notebook.application.repositories;

import java.util.List;

public interface IGroupRepository {

    public List<String> getAllGroupsForSubjectAndCourse(int idSubject, int idCourse);
}
