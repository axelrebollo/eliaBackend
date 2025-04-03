package com.axel.notebook.infrastructure.persistence;

import com.axel.notebook.infrastructure.JpaEntities.TableEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaTableRepository extends JpaRepository<TableEntity, Integer> {
    @Query(value = "SELECT * FROM table_entity WHERE table_entity.id_group = :idGroup", nativeQuery = true)
    public List<TableEntity> findAllTablesByIdGroup(int idGroup);

    public TableEntity findById(int idTable);

    public TableEntity save(TableEntity table);

    @Query(value = "SELECT * FROM table_entity WHERE table_entity.teacher = :idTeacher", nativeQuery = true)
    public List<TableEntity> findByIdTeacher(int idTeacher);

    public TableEntity findByClassCode(String classCode);

    @Query(value = "SELECT COUNT(*) > 0 " +
            "FROM table_entity " +
            "WHERE table_entity.teacher = :teacher " +
            "AND table_entity.name_table = :nameTable " +
            "AND table_entity.id_group = :idGroup",
            nativeQuery = true)
    public boolean exists(int teacher, String nameTable, int idGroup);

    @Query(value="SELECT * FROM table_entity WHERE table_entity.teacher = :idProfile AND table_entity.id_group = :idGroup " +
            "AND table_entity.name_table = :nameTable", nativeQuery = true)
    public TableEntity findByProfileGroupName(int idProfile, int idGroup, String nameTable);

    @Transactional
    @Modifying
    @Query("UPDATE TableEntity t SET t.nameTable = :newNameTable WHERE t.idTable = :idTable")
    public int updateNameByIdTable(int idTable, String newNameTable);
}
