package com.axel.notebook.application.useCases;

import com.axel.notebook.application.DTOs.CourseResponse;
import com.axel.notebook.application.services.IManageCourseUseCase;
import org.springframework.stereotype.Service;

@Service
public class ManageCourseUseCase implements IManageCourseUseCase {
    //Dependency injection

    //Constructor

    //get all courses with token + idYear
    public CourseResponse getAllCoursesUseCase(String token, String nameCourse) {
        //TODO
        //por controlar que no haya posibilidad de guardar 2 años con el mismo nombre y mismo usuario
        return null;
    }
    //create a new course into year selected
    public CourseResponse addCourseUseCase(String token, String nameCourse, String nameYear){
        //TODO
        return null;
    }
}
