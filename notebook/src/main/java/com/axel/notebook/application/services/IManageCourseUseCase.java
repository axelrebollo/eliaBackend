package com.axel.notebook.application.services;

import com.axel.notebook.application.DTOs.CourseResponse;

public interface IManageCourseUseCase {
    //get all courses with token + idYear
    public CourseResponse getAllCoursesUseCase(String token, String nameCourse);

    //create a new course into year selected
    public CourseResponse addCourseUseCase(String token, String nameCourse, String nameYear);
}
