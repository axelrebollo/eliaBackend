package com.axel.notebook.infrastructure.persistence;

import com.axel.notebook.infrastructure.JpaEntities.CourseEntity;
import com.axel.notebook.infrastructure.JpaEntities.YearEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaCourseRepository extends JpaRepository<CourseEntity, Integer> {
    //find all courses that exist into one year
    @Query(value = "SELECT * FROM course_entity WHERE course_entity.id_year = :idYear", nativeQuery = true)
    public List<CourseEntity> findAllCoursesByIdYear(int idYear);

    public CourseEntity findByIdCourse(int idCourse);

    @Query("SELECT c FROM CourseEntity c WHERE c.year = :year AND c.nameCourse = :name")
    public CourseEntity findByYearSubjectName(YearEntity year, String name);

    public void deleteById(int idCourse);

    @Transactional
    @Modifying
    @Query("UPDATE CourseEntity c SET c.nameCourse = :newNameCourse WHERE c.idCourse = :idCourse")
    public int updateNameByIdCourse(int idCourse, String newNameCourse);
}
