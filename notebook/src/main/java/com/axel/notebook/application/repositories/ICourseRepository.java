package com.axel.notebook.application.repositories;

import com.axel.notebook.domain.entities.Course;

import java.util.List;

public interface ICourseRepository {
    public List<String> getAllCoursesForUser(int idProfile, String nameYear);

    public int getIdCourseForUserYear(int idYear, String nameCourse);

    public boolean existCourseForUser(int idYear, String nameCourse);

    public Course updateCourse(Course course);

    public boolean deleteCourse(int idProfile, String nameCourse, String nameYear);
}
