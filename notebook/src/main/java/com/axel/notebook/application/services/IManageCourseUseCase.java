package com.axel.notebook.application.services;

import com.axel.notebook.application.DTOs.CourseResponse;
import com.axel.notebook.application.DTOs.DeleteResponse;
import com.axel.notebook.application.DTOs.UpdateResponse;

public interface IManageCourseUseCase {
    //get all courses with token + idYear
    public CourseResponse getAllCoursesUseCase(String token, String nameCourse);

    //create a new course into year selected
    public CourseResponse addCourseUseCase(String token, String nameCourse, String nameYear);

    public DeleteResponse deleteCourseUseCase(String token, String nameCourse, String nameYear);

    public UpdateResponse updateCourseUseCase(String token, String nameCourse, String nameYear, String newNameCourse);
}
