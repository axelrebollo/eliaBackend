package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.domain.entities.Subject;
import com.axel.notebook.application.repositories.ISubjectRepository;
import com.axel.notebook.infrastructure.JpaEntities.SubjectEntity;
import com.axel.notebook.infrastructure.adapters.SubjectAdapterInfrastructure;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaSubjectRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubjectRepositoryImpl implements ISubjectRepository {
    //Dependency injection
    private final JpaSubjectRepository jpaSubjectRepository;
    private final SubjectAdapterInfrastructure subjectAdapter;

    //Constructor
    public SubjectRepositoryImpl(JpaSubjectRepository jpaSubjectRepository, SubjectAdapterInfrastructure subjectAdapter) {
        this.jpaSubjectRepository = jpaSubjectRepository;
        this.subjectAdapter = subjectAdapter;
    }

    //find all subjects for user and check that this subject not exists
    public Boolean existSubjectForUser(String name, int idProfile) {
        List<SubjectEntity> subjectEntities = jpaSubjectRepository.findByIdProfile(idProfile);
        Boolean exist = false;
        for (SubjectEntity subjectEntity : subjectEntities) {
            if(subjectEntity.getNameSubject().equals(name)) {
                exist = true;
            }
        }
        return exist;
    }

    //update subject that user are created
    public Subject updateSubject(Subject subject) {
        if(subject == null) {
            throw new InfrastructureException("La asignatura está vacía o es inexistente.");
        }
        SubjectEntity subjectEntity = subjectAdapter.fromApplicationWhithoutId(subject);
        subjectEntity = jpaSubjectRepository.save(subjectEntity);
        return subjectAdapter.toApplication(subjectEntity);
    }

    //get all Subjects for user
    public List<String> getAllSubjectsForUser(int idProfile) {
        List<SubjectEntity> subjectEntities = jpaSubjectRepository.findByIdProfile(idProfile);
        if(subjectEntities == null) {
            throw new InfrastructureException("No se han encontrado asignaturas.");
        }
        List<String> subjects = new ArrayList<>();

        for (SubjectEntity subjectEntity : subjectEntities) {
            subjects.add(subjectEntity.getNameSubject());
        }

        return subjects;
    }

    //delete Subject that user are created
    public void deleteSubject(int idUser, int idSubject) {
        //TODO
    }
}
