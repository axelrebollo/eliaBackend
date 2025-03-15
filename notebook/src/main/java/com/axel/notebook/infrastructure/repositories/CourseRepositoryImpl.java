package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.ICourseRepository;
import com.axel.notebook.domain.entities.Course;
import com.axel.notebook.infrastructure.JpaEntities.CourseEntity;
import com.axel.notebook.infrastructure.adapters.CourseAdapterInfrastructure;
import com.axel.notebook.infrastructure.adapters.YearAdapterInfrastructure;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseRepositoryImpl implements ICourseRepository {
    private final YearAdapterInfrastructure yearAdapterInfrastructure;
    //Dependency injection
    YearRepositoryImpl yearRepository;
    JpaCourseRepository jpaCourseRepository;
    private final CourseAdapterInfrastructure courseAdapter;

    @Autowired
    public CourseRepositoryImpl(YearRepositoryImpl yearRepository,
                                JpaCourseRepository jpaCourseRepository,
                                CourseAdapterInfrastructure courseAdapter, YearAdapterInfrastructure yearAdapterInfrastructure) {
        this.yearRepository = yearRepository;
        this.jpaCourseRepository = jpaCourseRepository;
        this.courseAdapter = courseAdapter;
        this.yearAdapterInfrastructure = yearAdapterInfrastructure;
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

    public Course updateCourse(Course course){
        if(course == null){
            throw new InfrastructureException("El curso está vacío o es inexistente.");
        }
        CourseEntity courseEntity = courseAdapter.fromApplicationWithoutId(course);
        courseEntity = jpaCourseRepository.save(courseEntity);
        return courseAdapter.toApplication(courseEntity);
    }
}
