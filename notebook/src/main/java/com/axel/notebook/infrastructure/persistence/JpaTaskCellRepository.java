package com.axel.notebook.infrastructure.persistence;

import com.axel.notebook.infrastructure.JpaEntities.TaskCellEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaTaskCellRepository extends JpaRepository<TaskCellEntity, Integer> {
    //use JPQL
    @Query("SELECT t FROM TaskCellEntity t WHERE t.idCell = :idCell")
    public TaskCellEntity findByIdCell(int idCell);

    @Query("SELECT t FROM TaskCellEntity t WHERE t.nameTask = :name")
    public List<TaskCellEntity> findByName(String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM TaskCellEntity t WHERE t.idCell = :idCell")
    public void deleteByIdCell(int idCell);
}
