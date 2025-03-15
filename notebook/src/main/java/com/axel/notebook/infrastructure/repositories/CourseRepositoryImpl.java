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
        int idYear = yearRepository.getIdYearForUser(idProfile, nameYear);

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

    public boolean existCourseForUser(int idYear, String nameCourse){
        List<CourseEntity> coursesEntities = jpaCourseRepository.findAllCoursesByIdYear(idYear);
        for(CourseEntity course : coursesEntities){
            if(course.getNameCourse().equals(nameCourse)){
                return true;
            }
        }
        return false;
    }

    public int getIdCourseForUserYear(int idYear, String nameCourse){
        if(existCourseForUser(idYear, nameCourse)){
            List<CourseEntity> courses = jpaCourseRepository.findAllCoursesByIdYear(idYear);
            if(courses.isEmpty()){
                return 0;
            }
            else{
                for(CourseEntity course : courses){
                    if(course.getNameCourse().equals(nameCourse)){
                        return course.getIdCourse();
                    }
                }
            }
        }
        return 0;
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
