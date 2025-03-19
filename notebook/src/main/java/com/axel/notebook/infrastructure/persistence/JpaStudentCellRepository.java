package com.axel.notebook.infrastructure.persistence;

import com.axel.notebook.infrastructure.JpaEntities.StudentCellEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaStudentCellRepository extends JpaRepository<StudentCellEntity, Integer> {
    @Query(value = "SELECT student_cell_entity.id_cell FROM student_cell_entity WHERE student_cell_entity.student = :idProfile", nativeQuery = true)
    public List<Integer> findAllCellsForStudent(int idProfile);
}
