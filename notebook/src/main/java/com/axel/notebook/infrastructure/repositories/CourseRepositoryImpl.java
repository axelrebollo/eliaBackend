package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.ICourseRepository;
import com.axel.notebook.infrastructure.JpaEntities.CourseEntity;
import com.axel.notebook.infrastructure.persistence.JpaCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseRepositoryImpl implements ICourseRepository {
    //Dependency injection
    YearRepositoryImpl yearRepository;
    JpaCourseRepository jpaCourseRepository;

    @Autowired
    public CourseRepositoryImpl(YearRepositoryImpl yearRepository, JpaCourseRepository jpaCourseRepository) {
        this.yearRepository = yearRepository;
        this.jpaCourseRepository = jpaCourseRepository;
    }

    public List<String> getAllCoursesForUser(int idProfile, String nameYear){
        List<String> courses = new ArrayList<>();

        //get idYear
        int idYear = yearRepository.getYearForUser(nameYear, idProfile);

        //if user not created years, not contains courses
        if(idYear <= 0){
            return courses;
        }

        //find courses into this idYear
        List<CourseEntity> coursesEntities = jpaCourseRepository.findAllCoursesByIdYear(idYear);

        //check courses not null
        if (coursesEntities != null) {
            //parse nameCourse into strings list
            for (CourseEntity course : coursesEntities) {
                courses.add(course.getNameCourse());
            }
        }
        return courses;
    }
}
