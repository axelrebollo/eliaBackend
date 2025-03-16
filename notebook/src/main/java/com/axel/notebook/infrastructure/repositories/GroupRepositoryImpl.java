package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.IGroupRepository;
import com.axel.notebook.domain.entities.Group;
import com.axel.notebook.infrastructure.JpaEntities.GroupEntity;
import com.axel.notebook.infrastructure.adapters.GroupAdapterInfrastructure;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaGroupRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GroupRepositoryImpl implements IGroupRepository {
    //Dependency injection
    private final JpaGroupRepository jpaGroupRepository;
    private final GroupAdapterInfrastructure groupAdapter;

    //Constructor
    public GroupRepositoryImpl(JpaGroupRepository jpaGroupRepository, GroupAdapterInfrastructure groupAdapter) {
        this.jpaGroupRepository = jpaGroupRepository;
        this.groupAdapter = groupAdapter;
    }

    public List<String> getAllGroupsForSubjectAndCourse(int idSubject, int idCourse){
        //check data
        if(idSubject <= 0 || idCourse <= 0){
            throw new InfrastructureException("Error al buscar grupos, asignatura o curso inválidos.");
        }

        List<GroupEntity> groups;

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

    public boolean existGroup(int idCourse, int idSubject, String nameGroup){
        if(idCourse <= 0 || idSubject <= 0 || nameGroup == null || nameGroup.isEmpty()){
            throw new InfrastructureException("Algun dato es incompleto o nulo para determinar si existe el grupo.");
        }

        List<GroupEntity> groups;
        groups = jpaGroupRepository.findAllGroupsBySubjectAndCourse(idSubject, idCourse);

        if(!groups.isEmpty()){
            for(GroupEntity group : groups){
                if(group.getNameGroup().equals(nameGroup)){
                    return true;
                }
            }
        }
        return false;
    }

    public Group updateGroup(Group group){
        if(group == null){
            throw new InfrastructureException("El grupo está vacío o es inexistente.");
        }
        GroupEntity groupEntity = groupAdapter.fromApplicationWithoutId(group);
        groupEntity = jpaGroupRepository.save(groupEntity);
        return groupAdapter.toApplication(groupEntity);
    }

    public Group getGroup(int idCourse, int idSubject, String nameGroup){
        if(idCourse <= 0 || idSubject <= 0 || nameGroup == null || nameGroup.isEmpty()){
            throw new InfrastructureException("Algun dato es incompleto o nulo para retornar el grupo");
        }

        List<GroupEntity> groups;
        groups = jpaGroupRepository.findAllGroupsBySubjectAndCourse(idSubject, idCourse);
        if(!groups.isEmpty()){
            for(GroupEntity group : groups){
                if(group.getNameGroup().equals(nameGroup)){
                    return groupAdapter.toApplication(group);
                }
            }
        }
        return null;
    }
}
