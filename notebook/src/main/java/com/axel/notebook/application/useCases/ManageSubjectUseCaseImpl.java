package com.axel.notebook.application.useCases;

import com.axel.notebook.application.DTOs.SubjectResponse;
import com.axel.notebook.application.exceptions.ApplicationException;
import com.axel.notebook.application.repositories.ISubjectRepository;
import com.axel.notebook.application.services.IManageSubjectUseCase;
import com.axel.notebook.application.services.producers.ISubjectProducer;
import com.axel.notebook.domain.entities.Subject;
import com.axel.notebook.domain.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ManageSubjectUseCaseImpl implements IManageSubjectUseCase {
    //Dependency injection
    private final ISubjectRepository subjectRepository;   //infrastructure layer
    private final SubjectService subjectService;  //domain layer
    private final ISubjectProducer subjectProducer;   //infrastructure layer


    //Constructor
    @Autowired
    public ManageSubjectUseCaseImpl(ISubjectRepository subjectRepository,
                                    ISubjectProducer subjectProducer) {
        this.subjectRepository = subjectRepository;
        this.subjectService = new SubjectService();
        this.subjectProducer = subjectProducer;
    }

    //create a new subject
    public SubjectResponse addSubjectUseCase(String token, String nameSubject) {
        if(token == null || token.isEmpty()){
            throw new ApplicationException("Ha habido un problema con el registro de usuario.");
        }

        if(nameSubject == null || nameSubject.isEmpty()){
            throw new ApplicationException("El nombre de la asignatura está vacía o no existe.");
        }

        //Check if token is correct and returns decoded
        int idProfile = getProfileId(token);

        //Check if subject exist into system for this user
        if(idProfile <= 0){
            throw new ApplicationException("No se ha encontrado el perfil del usuario, el usuario no existe");
        }

        //Check if exist year for this user
        if(subjectRepository.existSubjectForUser(nameSubject, idProfile)){
            throw new ApplicationException("Existe un año con ese nombre con este usuario.");
        }

        //Create year
        Subject newSubject = subjectService.addSubject(nameSubject,idProfile);

        //Save year
        try{
            newSubject = subjectRepository.updateSubject(newSubject);
        }
        catch(ApplicationException e){
            throw new ApplicationException("Error al crear el año.");
        }

        //return the list of subjects for this user
        List<String> subjectsToUser = null;

        if(newSubject != null){
            subjectsToUser = getAllSubjectsForUser(idProfile);
        }

        return new SubjectResponse(subjectsToUser);
    }

    //Get all subjects for user from token
    public SubjectResponse getAllSubjectsFromTokenUseCase(String token) {
        if(token == null || token.isEmpty()){
            throw new ApplicationException("Error el token está vacío.");
        }
        //decode token
        int idProfile = getProfileId(token);

        return new SubjectResponse(getAllSubjectsForUser(idProfile));
    }

    public int getProfileId(String token) {
        return subjectProducer.sendToken(token);
    }

    //Get all subjects for one user
    public List<String> getAllSubjectsForUser(int idProfile){
        if(idProfile <= 0){
            throw new ApplicationException("El usuario no existe, no se ha encontrado el perfil.");
        }
        return subjectRepository.getAllSubjectsNameForUser(idProfile);
    }

    //update name subject
    public void updateSubjectUseCase() {
        //TODO
    }

    //delete all information about this subject
    public void deleteSubjectUseCase() {
        //delete in cascade
        //TODO
    }

}
