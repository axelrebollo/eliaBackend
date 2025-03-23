package com.axel.notebook.infrastructure.persistence;

import com.axel.notebook.infrastructure.JpaEntities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaGroupRepository extends JpaRepository<GroupEntity, Integer> {
    //find all groups that exist into idCourse and idSubject
    @Query(value = "SELECT * FROM group_entity WHERE group_entity.id_subject = :idSubject AND group_entity.id_course = :idCourse",
            nativeQuery = true)
    public List<GroupEntity> findAllGroupsBySubjectAndCourse(int idSubject, int idCourse);

    public GroupEntity findById(int idGroup);
}
