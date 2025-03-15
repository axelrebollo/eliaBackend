package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.IGroupRepository;
import com.axel.notebook.infrastructure.JpaEntities.GroupEntity;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaGroupRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GroupRepositoryImpl implements IGroupRepository {
    //Dependency injection
    private JpaGroupRepository jpaGroupRepository;

    //Constructor
    public GroupRepositoryImpl(JpaGroupRepository jpaGroupRepository) {
        this.jpaGroupRepository = jpaGroupRepository;
    }

    public List<String> getAllGroupsForSubjectAndCourse(int idSubject, int idCourse){
        //check data
        if(idSubject <= 0 || idCourse <= 0){
            throw new InfrastructureException("Error al buscar grupos, asignatura o curso inválidos.");
        }

        List<GroupEntity> groups = new ArrayList<>();

        //find groups with same idSubject and idCourse
        groups = jpaGroupRepository.findAllGroupsBySubjectAndCourse(idSubject, idCourse);

        if(groups.isEmpty()){
            return new ArrayList<String>();
        }

        List<String> groupNames = new ArrayList<>();
        for(GroupEntity group : groups){
            groupNames.add(group.getNameGroup());
        }
        return groupNames;
    }
}
