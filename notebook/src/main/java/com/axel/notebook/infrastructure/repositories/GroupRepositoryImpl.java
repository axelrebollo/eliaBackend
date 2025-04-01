package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.IGroupRepository;
import com.axel.notebook.domain.entities.Group;
import com.axel.notebook.infrastructure.JpaEntities.CourseEntity;
import com.axel.notebook.infrastructure.JpaEntities.GroupEntity;
import com.axel.notebook.infrastructure.JpaEntities.SubjectEntity;
import com.axel.notebook.infrastructure.JpaEntities.YearEntity;
import com.axel.notebook.infrastructure.adapters.GroupAdapterInfrastructure;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaCourseRepository;
import com.axel.notebook.infrastructure.persistence.JpaGroupRepository;
import com.axel.notebook.infrastructure.persistence.JpaSubjectRepository;
import com.axel.notebook.infrastructure.persistence.JpaYearRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GroupRepositoryImpl implements IGroupRepository {
    //Dependency injection
    private final JpaGroupRepository jpaGroupRepository;
    private final GroupAdapterInfrastructure groupAdapter;
    private final JpaSubjectRepository jpaSubjectRepository;
    private final JpaYearRepository jpaYearRepository;
    private final JpaCourseRepository jpaCourseRepository;

    //Constructor
    public GroupRepositoryImpl(JpaGroupRepository jpaGroupRepository, GroupAdapterInfrastructure groupAdapter, JpaSubjectRepository jpaSubjectRepository, JpaYearRepository jpaYearRepository, JpaCourseRepository jpaCourseRepository) {
        this.jpaGroupRepository = jpaGroupRepository;
        this.groupAdapter = groupAdapter;
        this.jpaSubjectRepository = jpaSubjectRepository;
        this.jpaYearRepository = jpaYearRepository;
        this.jpaCourseRepository = jpaCourseRepository;
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

    public boolean deleteGroup(int idProfile, String nameCourse, String nameSubject, String nameYear, String nameGroup){
        if(idProfile <= 0 || nameCourse == null || nameCourse.isEmpty() || nameSubject == null || nameSubject.isEmpty() ||
                nameYear == null || nameYear.isEmpty() || nameGroup == null || nameGroup.isEmpty()){
            throw new InfrastructureException("Alguno de los datos para borrar el grupo no es correcto.");
        }

        boolean isDeleted = false;
        SubjectEntity subject = jpaSubjectRepository.findByNameAndIdProfile(nameSubject, idProfile);
        if(subject == null){
            throw new InfrastructureException("No se ha encontrado la asignatura");
        }
        YearEntity year = jpaYearRepository.findByNameAndIdProfile(nameYear, idProfile);
        if(year == null){
            throw new InfrastructureException("No se ha encontrado el año.");
        }
        CourseEntity course = jpaCourseRepository.findByYearSubjectName(year, nameCourse);
        if(course == null){
            throw new InfrastructureException("No se ha encontrado el curso.");
        }
        int idGroup = jpaGroupRepository.findByNameCourseSubject(nameGroup, course, subject).getIdGroup();
        if(idGroup <= 0){
            throw new InfrastructureException("No se ha encontrado el grupo.");
        }

        try{
            jpaGroupRepository.deleteById(idGroup);
            isDeleted = true;
        }
        catch(InfrastructureException e){
            throw new InfrastructureException("Error al eliminar el grupo.");
        }

        return isDeleted;
    }
}
