package com.axel.notebook.domain.services;

import com.axel.notebook.domain.entities.Course;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    public Course addCourse(String nameCourse, int idProfile, int idYear){
        return new Course(nameCourse, idProfile, idYear);
    }
}
