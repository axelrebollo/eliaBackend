package com.axel.notebook.infrastructure.persistence;

import com.axel.notebook.infrastructure.JpaEntities.NoteCellEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaNoteCellRepository extends JpaRepository<NoteCellEntity, Integer> {
    public NoteCellEntity findByIdCell(int idCell);

    @Query("SELECT s FROM NoteCellEntity s WHERE s.studentCell.idCell = :idCellStudent AND s.taskCell.idCell = :idCellTask")
    public NoteCellEntity findIdNoteCell(int idCellStudent, int idCellTask);

    @Transactional
    @Modifying
    @Query("UPDATE NoteCellEntity c SET c.note = :newNote WHERE c.idCell = :idNoteCell")
    public int updateNote(int idNoteCell, double newNote);

    @Transactional
    @Modifying
    @Query("DELETE FROM NoteCellEntity n WHERE n.idCell = :idCell")
    public void deleteByIdCell(int idCell);
}
