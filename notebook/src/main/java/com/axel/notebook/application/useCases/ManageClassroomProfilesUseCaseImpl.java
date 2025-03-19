package com.axel.notebook.application.useCases;

import com.axel.notebook.application.DTOs.ClassroomProfileResponse;
import com.axel.notebook.application.exceptions.ApplicationException;
import com.axel.notebook.application.repositories.IClassroomProfileRepository;
import com.axel.notebook.application.services.IManageClassroomProfileUseCase;
import com.axel.notebook.application.services.producers.IClassroomProfileProducer;
import com.axel.notebook.domain.valueObjects.ClassroomProfile;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManageClassroomProfilesUseCaseImpl implements IManageClassroomProfileUseCase {
    //Dependency injection
    private final IClassroomProfileProducer classroomProfileProducer;
    private final IClassroomProfileRepository classroomProfileRepository;

    //Constructor
    public ManageClassroomProfilesUseCaseImpl(IClassroomProfileProducer classroomProfileProducer,
                                              IClassroomProfileRepository classroomProfileRepository) {
        this.classroomProfileProducer = classroomProfileProducer;
        this.classroomProfileRepository = classroomProfileRepository;
    }

    //get classrooms that one teacher are created for profile
    public ClassroomProfileResponse getClassroomsForProfile(String token){
        //check data
        if(token == null || token.isEmpty()){
            throw new ApplicationException("Error el token está vacío.");
        }

        //decode token and get data
        Map<String,String> dataToken = getProfileData(token);
        String idProfileString = dataToken.get("idProfile");
        int idProfile = 0;
        if(idProfileString == null || idProfileString.isEmpty() ||
                dataToken.get("role") == null || dataToken.get("role").isEmpty()){
            throw new ApplicationException("No existe información para este usuario.");
        }

        idProfile = Integer.parseInt(idProfileString);

        if(idProfile <= 0){
            throw new ApplicationException("Error al recuperar los usuarios, el perfil no existe");
        }

        String role = dataToken.get("role");

        //calls to prepare information
        try{
            return getClassroomsDataForProfile(idProfile, role);
        }
        catch(ApplicationException e){
            throw new ApplicationException("Ha ocurrido un problema obteniendo la información de las clases.");
        }
    }

    //get classrooms that one user are created for profile
    public ClassroomProfileResponse getClassroomsDataForProfile(int idProfile, String role){
        if(idProfile <= 0){
            throw new ApplicationException("Error con el perfil, no existe.");
        }
        //get all information and returns
        if(role == null || role.isEmpty()){
            throw new ApplicationException("Error con el rol, es nulo.");
        }

        List<ClassroomProfile> data;

        if(role.equals("TEACHER")){
            data = classroomProfileRepository.getTeacherDataByIdProfile(idProfile);
        }
        else if (role.equals("STUDENT")){
            data = classroomProfileRepository.getStudentDataByIdProfile(idProfile);
        }
        else{
            throw new ApplicationException("El rol tiene datos corruptos.");
        }

        return new ClassroomProfileResponse(data);
    }

    public Map<String,String> getProfileData(String token) {
        return classroomProfileProducer.sendToken(token);
    }
}
