package com.axel.notebook.application.repositories;

import com.axel.notebook.domain.entities.Course;

import java.util.List;

public interface ICourseRepository {
    public List<String> getAllCoursesForUser(int idProfile, String nameYear);

    public Course updateCourse(Course course);
}
