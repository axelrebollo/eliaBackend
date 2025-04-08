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

    //get all names Subjects for user
    public List<String> getAllSubjectsNameForUser(int idProfile) {
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

    //get all Subjects for user
    public List<Subject> getAllSubjectsForUser(int idProfile){
        List<SubjectEntity> subjectEntities = jpaSubjectRepository.findByIdProfile(idProfile);
        if(subjectEntities == null) {
            throw new InfrastructureException("No se han encontrado asignaturas.");
        }
        List<Subject> subjects = new ArrayList<>();

        for (SubjectEntity subjectEntity : subjectEntities) {
            Subject subject = subjectAdapter.toApplication(subjectEntity);
            subjects.add(subject);
        }
        return subjects;
    }

    //find idSubject for idProfile and nameSubject
    public int getIdSubjectForUser(int idProfile, String nameSubject) {
        if(existSubjectForUser(nameSubject, idProfile)){
            List<Subject> subjects = getAllSubjectsForUser(idProfile);
            if(subjects.isEmpty()){
                return 0;
            }
            else{
                for(Subject subject : subjects){
                    if(subject.getNameSubject().equals(nameSubject)){
                        return subject.getIdSubject();
                    }
                }
            }
        }
        return 0;
    }

    //delete Subject that user are created
    public boolean deleteSubject(int idProfile, String nameSubject) {
        if(idProfile <= 0 || nameSubject == null || nameSubject.isEmpty()) {
            throw new InfrastructureException("Alguno de los datos para borrar la asignatura no es correco.");
        }

        boolean isDeleted = false;
        SubjectEntity subjectEntity = jpaSubjectRepository.findByNameAndIdProfile(nameSubject, idProfile);
        if(subjectEntity == null) {
            throw new InfrastructureException("No se ha encontrado la asignatura.");
        }

        try{
            jpaSubjectRepository.deleteById(subjectEntity.getIdSubject());
            isDeleted = true;
        }
        catch(InfrastructureException e){
            throw new InfrastructureException("Error al eliminar la asignatura");
        }
        return isDeleted;
    }

    public int updateNameSubject(int idProfile, String nameSubject, String newNameSubject){
        if(idProfile <= 0 || nameSubject == null || nameSubject.isEmpty() || newNameSubject == null || newNameSubject.isEmpty()) {
            throw new InfrastructureException("No es posible actualizar el nombre del año con los datos obtenidos.");
        }

        if(existSubjectForUser(nameSubject, idProfile)){
            throw new InfrastructureException("El nombre de la asignatura existe para este usuario.");
        }

        SubjectEntity subjectEntity = jpaSubjectRepository.findByNameAndIdProfile(nameSubject, idProfile);
        if(subjectEntity == null) {
            throw new InfrastructureException("No se ha encontrado la asignatura.");
        }

        int isUpdated = jpaSubjectRepository.updateNameByIdSubject(subjectEntity.getIdSubject(), newNameSubject);

        if(isUpdated == 1){
            return subjectEntity.getIdSubject();
        }
        return -1;
    }
}
