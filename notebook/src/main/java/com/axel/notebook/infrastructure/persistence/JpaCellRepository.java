package com.axel.notebook.infrastructure.persistence;

import com.axel.notebook.infrastructure.JpaEntities.CellEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaCellRepository extends JpaRepository<CellEntity, Integer> {
    public Optional<CellEntity> findById(Integer id);

    @Query(value = "SELECT COUNT(*) FROM cell_entity WHERE id_table = :idTable AND cell_type = 'STUDENT'",
            nativeQuery = true)
    public int countStudentsIntoTable(int idTable);

    @Query(value = "SELECT COUNT(*) FROM cell_entity WHERE id_table = :idTable AND cell_type = 'TASK'",
            nativeQuery = true)
    public int countTasksIntoTable(int idTable);
}
