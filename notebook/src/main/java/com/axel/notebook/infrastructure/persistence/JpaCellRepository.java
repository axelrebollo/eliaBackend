package com.axel.notebook.infrastructure.persistence;

import com.axel.notebook.infrastructure.JpaEntities.CellEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaCellRepository extends JpaRepository<CellEntity, Integer> {
    public Optional<CellEntity> findById(Integer id);

    @Query(value = "SELECT COUNT(*) FROM cell_entity WHERE id_table = :idTable AND cell_type = 'STUDENT'",
            nativeQuery = true)
    public int countStudentsIntoTable(int idTable);

    @Query(value = "SELECT cell_entity.id_cell FROM cell_entity WHERE cell_entity.id_table = :idTable AND cell_entity.cell_type = 'STUDENT'",nativeQuery = true)
    public List<Integer> findByTableIdAndStudentType(int idTable);

    @Query(value = "SELECT c.id_cell, c.position_col, c.position_row,  c.id_table FROM cell_entity c WHERE c.id_table = :idTable AND c.cell_type = :type",nativeQuery = true)
    public List<Object[]> getAllByIdAndType(int idTable, String type);

    @Transactional
    @Modifying
    @Query("UPDATE CellEntity c SET c.positionCol = :newPositionCol WHERE c.idCell = :idCell")
    public void setPositionCol(int idCell, int newPositionCol);

    @Transactional
    @Modifying
    @Query("UPDATE CellEntity c SET c.positionRow = :newPositionRow WHERE c.idCell = :idCell")
    public void setPositionRow(int idCell, int newPositionRow);

    @Query(value = "SELECT cell_entity.id_cell FROM cell_entity WHERE cell_entity.id_table = :idTable AND cell_entity.cell_type = :type AND  cell_entity.position_col = :positionColumn", nativeQuery = true)
    public List<Integer> findForTypeIdAndColumn(int idTable, int positionColumn, String type);

    @Query(value = "SELECT cell_entity.id_cell FROM cell_entity WHERE cell_entity.id_table = :idTable AND cell_entity.cell_type = :type AND  cell_entity.position_row = :positionRow", nativeQuery = true)
    public List<Integer> findForTypeIdAndRow(int idTable, int positionRow, String type);

    @Transactional
    @Modifying
    @Query("DELETE FROM CellEntity c WHERE c.idCell = :idCell")
    public void deleteByIdCell(int idCell);

    @Query(value = "SELECT cell_entity.idCell FROM cell_entity WHERE cell_entity.id_cell = :idCell AND cell_entity.id_table = :idTable", nativeQuery = true)
    public int findByCellAndTable(int idCell, int idTable);

    @Query(value = "SELECT cell_entity.position_row FROM cell_entity WHERE cell_entity.id_cell = :idCell", nativeQuery = true)
    public int findPositionRowByIdCell(int idCell);

    @Query(value = "SELECT cell_entity.id_cell FROM cell_entity WHERE cell_entity.cell_type = :type AND cell_entity.position_col = :positionCol AND cell_entity.position_row = :positionRow AND cell_entity.id_table = :idTable", nativeQuery = true)
    public int findByTypeColRowTable(String type, int positionCol, int positionRow, int idTable);
}
