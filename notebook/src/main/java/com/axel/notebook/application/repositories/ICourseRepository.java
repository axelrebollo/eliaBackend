package com.axel.notebook.application.repositories;

import java.util.List;

public interface ICourseRepository {
    public List<String> getAllCoursesForUser(int idProfile, String nameYear);
}
