package com.axel.notebook.infrastructure.persistence;

import com.axel.notebook.infrastructure.JpaEntities.StudentCellEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaStudentCellRepository extends JpaRepository<StudentCellEntity, Integer> {
    @Query(value = "SELECT student_cell_entity.id_cell FROM student_cell_entity WHERE student_cell_entity.student = :idProfile", nativeQuery = true)
    public List<Integer> findAllCellsForStudent(int idProfile);

    @Query(value = "SELECT student_cell_entity.student FROM student_cell_entity WHERE student_cell_entity.id_cell = :idCell", nativeQuery = true)
    public List<Integer> findProfileForIdCell(int idCell);

    //use JPQL
    @Query("SELECT s FROM StudentCellEntity s WHERE s.idCell = :idCell")
    public StudentCellEntity findStudentCellEntityById(int idCell);

    @Query("SELECT s FROM StudentCellEntity s WHERE s.idProfile = :idProfile")
    public List<StudentCellEntity> findStudentCellEntityByIdProfile(int idProfile);

    @Query("SELECT t FROM StudentCellEntity t WHERE t.idCell = :idCell")
    public StudentCellEntity findByIdCell(int idCell);

    @Transactional
    @Modifying
    @Query("DELETE FROM StudentCellEntity t WHERE t.idCell = :idCell")
    public void deleteByIdCell(int idCell);
}
