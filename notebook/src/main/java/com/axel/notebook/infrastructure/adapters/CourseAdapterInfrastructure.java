package com.axel.notebook.infrastructure.adapters;

import com.axel.notebook.domain.entities.Course;
import com.axel.notebook.infrastructure.JpaEntities.CourseEntity;
import com.axel.notebook.infrastructure.JpaEntities.YearEntity;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaYearRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseAdapterInfrastructure {
    //Dependency injection
    private final JpaYearRepository jpaYearRepository;

    //constructor
    public CourseAdapterInfrastructure(JpaYearRepository jpaYearRepository) {
        this.jpaYearRepository = jpaYearRepository;
    }

    public Course toApplication(CourseEntity courseEntity) {
        if(courseEntity == null){
            throw new InfrastructureException("La entidad de infrastructura está vacía.");
        }
        return new Course(courseEntity.getIdCourse(), courseEntity.getNameCourse(),
                courseEntity.getYear().getIdProfile(), courseEntity.getYear().getIdYear());
    }

    public CourseEntity fromApplicationWithoutId(Course course) {
        if(course == null){
            throw new InfrastructureException("La entidad de aplicación está vacía.");
        }
        YearEntity entityYear = jpaYearRepository.findByIdYear(course.getIdYear());
        return new CourseEntity(course.getNameCourse(), entityYear);
    }
}
