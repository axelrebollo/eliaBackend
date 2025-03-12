package com.axel.notebook.infrastructure.persistence;

import com.axel.notebook.infrastructure.JpaEntities.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
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

    //Delete Subject
    public void deleteById(Integer idSubject);
}
