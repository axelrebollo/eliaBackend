package com.axel.notebook.infrastructure.adapters;

import com.axel.notebook.domain.entities.Subject;
import com.axel.notebook.infrastructure.JpaEntities.SubjectEntity;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import org.springframework.stereotype.Service;

@Service
public class SubjectAdapterInfrastructure {
    //Constructor
    public SubjectAdapterInfrastructure() {}

    public Subject toApplication(SubjectEntity subjectEntity) {
        if(subjectEntity == null){
            throw new InfrastructureException("La entidad de infrastructura está vacía.");
        }
        return new Subject(subjectEntity.getIdSubject(), subjectEntity.getNameSubject(), subjectEntity.getIdProfile());
    }

    public SubjectEntity fromApplicationWhithoutId(Subject subject) {
        if(subject == null){
            throw new InfrastructureException("La entidad de aplicación está vacía.");
        }
        return new SubjectEntity(subject.getNameSubject(), subject.getIdProfile());
    }
}
