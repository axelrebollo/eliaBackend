package com.axel.notebook.application.useCases;

import com.axel.notebook.application.DTOs.GroupResponse;
import com.axel.notebook.application.exceptions.ApplicationException;
import com.axel.notebook.application.repositories.ICourseRepository;
import com.axel.notebook.application.repositories.IGroupRepository;
import com.axel.notebook.application.repositories.ISubjectRepository;
import com.axel.notebook.application.repositories.IYearRepository;
import com.axel.notebook.application.services.producers.IGroupProducer;
import com.axel.notebook.application.services.IManageGroupUseCase;
import com.axel.notebook.domain.entities.Group;
import com.axel.notebook.domain.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManageGroupUseCase implements IManageGroupUseCase {
    //Dependency injection
    private final IGroupProducer groupProducer; //infrastructure layer
    private final ISubjectRepository subjectRepository; //infrastructure layer
    private final IYearRepository yearRepository;   //infrastructure layer
    private final ICourseRepository courseRepository;   //infrastructure layer
    private final IGroupRepository groupRepository; //infrastructure layer
    private final GroupService groupService;    //domain layer

    //Constructor
    @Autowired
    public ManageGroupUseCase(IGroupProducer groupProducer,
                              ISubjectRepository subjectRepository,
                              IYearRepository yearRepository,
                              ICourseRepository courseRepository,
                              IGroupRepository groupRepository,
                              GroupService groupService) {
        this.groupProducer = groupProducer;
        this.subjectRepository = subjectRepository;
        this.yearRepository = yearRepository;
        this.courseRepository = courseRepository;
        this.groupRepository = groupRepository;
        this.groupService = groupService;
    }

    //get all groups into course for one subject
    public GroupResponse getAllGroupsUseCase(String token, String nameCourse, String nameSubject, String nameYear){
        List<String> groups = new ArrayList<>();

        //check data
        if(token == null || token.isEmpty()){
            throw new ApplicationException("El token está vacío o es nulo.");
        }

        //if not selected subject and course return empty list
        if(nameCourse == null || nameCourse.isEmpty() ||
                nameSubject == null || nameSubject.isEmpty() ||
                nameYear == null || nameYear.isEmpty()){
            return new GroupResponse(groups);
        }

        //decode token and get idProfile
        int idProfile = getProfileId(token);

        if(idProfile <= 0){
            throw new ApplicationException("Error al recuperar los usuarios, el perfil no existe");
        }

        //get all groups into course and into subject for one user
        return new GroupResponse(getAllGroupsForSelection(idProfile, nameCourse, nameSubject, nameYear));
    }

    public int getProfileId(String token) {
        return groupProducer.sendToken(token);
    }

    public List<String> getAllGroupsForSelection(int idProfile, String nameCourse, String nameSubject, String nameYear){
        if(idProfile <= 0){
            throw new ApplicationException("El usuario no existe, no se ha encontrado el perfil.");
        }

        if(nameCourse.isEmpty()){
            throw new ApplicationException("El nombre del curso es nulo");
        }

        if(nameSubject.isEmpty()){
            throw new ApplicationException("El nombre de la asignatura es nulo");
        }

        //find idSubject for idProfile and nameSubject
        int idSubject = subjectRepository.getIdSubjectForUser(idProfile, nameSubject);

        //find idYear
        int idYear = yearRepository.getIdYearForUser(idProfile, nameYear);

        //find idCourse
        int idCourse = courseRepository.getIdCourseForUserYear(idYear, nameCourse);

        //find groups for this course and subject id's
        return groupRepository.getAllGroupsForSubjectAndCourse(idSubject, idCourse);
    }

    public GroupResponse addGroupUseCase(String token, String nameCourse, String nameSubject, String nameYear, String nameGroup){
        //check data
        if(token == null || token.isEmpty()){
            throw new ApplicationException("El token está vacío o es nulo.");
        }

        if(nameCourse == null || nameCourse.isEmpty() ||
                nameSubject == null || nameSubject.isEmpty() ||
                nameYear == null || nameYear.isEmpty() ||
                nameGroup == null || nameGroup.isEmpty()){
            throw new ApplicationException("Alguno de los valores seleccionados está vacío o es nulo.");
        }

        //decode token and get idProfile
        int idProfile = getProfileId(token);

        if(idProfile <= 0){
            throw new ApplicationException("Error al recuperar los usuarios, el perfil no existe");
        }

        //check if exist group into system

        //find idSubject for idProfile and nameSubject
        int idSubject = subjectRepository.getIdSubjectForUser(idProfile, nameSubject);
        //find idYear
        int idYear = yearRepository.getIdYearForUser(idProfile, nameYear);
        //find idCourse
        int idCourse = courseRepository.getIdCourseForUserYear(idYear, nameCourse);
        //check if group exists into system
        if(groupRepository.existGroup(idCourse, idSubject, nameGroup)){
            throw new ApplicationException("El grupo ya existe");
        }

        //create group
        Group newGroup = groupService.addGroup(nameGroup, idCourse, idSubject);

        //save group
        try{
            newGroup = groupRepository.updateGroup(newGroup);
        }
        catch(ApplicationException e){
            throw new ApplicationException("Ha ocurrido un error al guardar el grupo.");
        }

        //return all groups for selection
        List<String> groups = new ArrayList<>();
        if(newGroup != null){
            groups = getAllGroupsForSelection(idProfile, nameCourse, nameSubject, nameYear);
        }
        return new GroupResponse(groups);
    }
}
