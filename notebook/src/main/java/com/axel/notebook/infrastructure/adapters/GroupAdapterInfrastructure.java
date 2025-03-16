package com.axel.notebook.infrastructure.adapters;

import com.axel.notebook.domain.entities.Group;
import com.axel.notebook.infrastructure.JpaEntities.CourseEntity;
import com.axel.notebook.infrastructure.JpaEntities.GroupEntity;
import com.axel.notebook.infrastructure.JpaEntities.SubjectEntity;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaCourseRepository;
import com.axel.notebook.infrastructure.persistence.JpaSubjectRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupAdapterInfrastructure {
    //Dependency injection
    private final JpaCourseRepository jpaCourseRepository;
    private final JpaSubjectRepository jpaSubjectRepository;

    //Constructor
    public GroupAdapterInfrastructure(JpaCourseRepository jpaCourseRepository, JpaSubjectRepository jpaSubjectRepository) {
        this.jpaCourseRepository = jpaCourseRepository;
        this.jpaSubjectRepository = jpaSubjectRepository;
    }

    public Group toApplication(GroupEntity groupEntity) {
        if(groupEntity == null){
            throw new InfrastructureException("La entidad de infrastructura está vacía.");
        }
        return new Group(groupEntity.getIdGroup(), groupEntity.getNameGroup(),
                groupEntity.getCourse().getIdCourse(), groupEntity.getSubject().getIdSubject());
    }

    public GroupEntity fromApplicationWithoutId(Group group) {
        if(group == null){
            throw new InfrastructureException("La entidad de aplicación está vacía.");
        }
        //course
        int idCourse = group.getIdCourse();
        CourseEntity courseEntity = jpaCourseRepository.findByIdCourse(idCourse);
        int idSubject = group.getIdSubject();
        SubjectEntity subject = jpaSubjectRepository.findById(idSubject);
        return new GroupEntity(group.getNameGroup(), courseEntity, subject);
    }
}
