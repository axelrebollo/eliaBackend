package com.axel.notebook.infrastructure.persistence;

import com.axel.notebook.infrastructure.JpaEntities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCourseRepository extends JpaRepository<CourseEntity, Integer> {
    //find all courses that exist into one year
    @Query(value = "SELECT * FROM course_entity WHERE course_entity.id_year = :idYear", nativeQuery = true)
    public List<CourseEntity> findAllCoursesByIdYear(int idYear);
}
