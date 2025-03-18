package com.axel.notebook.infrastructure.persistence;

import com.axel.notebook.infrastructure.JpaEntities.CellEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCellRepository extends JpaRepository<CellEntity, Integer> {
    //get all classrooms that this student is enrolled
    @Query(value = "SELECT * FROM cell_entity WHERE cell_entity.student = :idStudent AND cell_type = 'STUDENT'", nativeQuery = true)
    public List<CellEntity> findByIdStudent(int idStudent);
}
