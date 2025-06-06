package com.axel.notebook.infrastructure.persistence;

import com.axel.notebook.infrastructure.JpaEntities.SubjectEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaSubjectRepository extends JpaRepository<SubjectEntity, Integer> {
    //Find all Subjects
    public List<SubjectEntity> findAll();

    //Find a Subject from name
    public List<SubjectEntity> findByIdProfile(int idProfile);

    //Create Subject
    public SubjectEntity save(SubjectEntity subject);

    public SubjectEntity findById(int idSubject);

    //Delete Subject
    public void deleteById(int idSubject);

    @Query("SELECT s FROM SubjectEntity s WHERE s.idProfile = :idProfile AND s.nameSubject = :name")
    public SubjectEntity findByNameAndIdProfile(String name, int idProfile);

    @Transactional
    @Modifying
    @Query("UPDATE SubjectEntity s SET s.nameSubject = :newNameSubject WHERE s.idSubject = :idSubject")
    public int updateNameByIdSubject(int idSubject, String newNameSubject);
}
