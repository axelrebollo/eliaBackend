package com.axel.notebook.application.useCases;

import com.axel.notebook.application.DTOs.TableResponse;
import com.axel.notebook.application.exceptions.ApplicationException;
import com.axel.notebook.application.repositories.*;
import com.axel.notebook.application.services.IManageTableUseCase;
import com.axel.notebook.application.services.producers.ITableProducer;
import com.axel.notebook.domain.entities.Table;
import com.axel.notebook.domain.services.TableService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManageTableUseCaseImpl implements IManageTableUseCase {
    //Dependency injection
    private final IGroupRepository groupRepository;
    private final ICourseRepository courseRepository;
    private final IYearRepository yearRepository;
    private final ISubjectRepository subjectRepository;
    private final ITableProducer tableProducer;
    private final ITableRepository tableRepository;
    private final TableService tableService;

    //Constructor
    public ManageTableUseCaseImpl(IGroupRepository groupRepository,
                                  ICourseRepository courseRepository,
                                  IYearRepository yearRepository,
                                  ISubjectRepository subjectRepository,
                                  ITableProducer tableProducer,
                                  ITableRepository tableRepository,
                                  TableService tableService) {
        this.groupRepository = groupRepository;
        this.courseRepository = courseRepository;
        this.yearRepository = yearRepository;
        this.subjectRepository = subjectRepository;
        this.tableProducer = tableProducer;
        this.tableRepository = tableRepository;
        this.tableService = tableService;
    }

    public TableResponse getAllTablesFromTokenUseCase(String token, String nameGroup, String nameCourse, String nameSubject, String nameYear){
        List<String> tables = new ArrayList<>();

        //check data
        if(token == null || token.isEmpty()){
            throw new ApplicationException("El Token es nulo o vacío.");
        }

        //if not selected group, return empty list
        if(nameGroup == null || nameGroup.isEmpty() || nameCourse == null || nameCourse.isEmpty() ||
                nameSubject == null || nameSubject.isEmpty() || nameYear == null || nameYear.isEmpty()){
            return new TableResponse(tables);
        }

        //extract idProfile and check if exist into the system
        int idProfile = getProfileId(token);

        if(idProfile <= 0){
            throw new ApplicationException("Error al recuperar el usuario, el perfil no existe.");
        }

        //return response with all tables for this name group and user
        return new TableResponse(getAllTablesForUser(idProfile, nameGroup, nameCourse, nameSubject, nameYear));
    }

    public int getProfileId(String token){
        return tableProducer.sendToken(token);
    }

    public List<String> getAllTablesForUser(int idProfile, String nameGroup, String nameCourse, String nameSubject, String nameYear){
        List<String> tables = new ArrayList<>();

        if(idProfile <= 0){
            throw new ApplicationException("El usuario no existe, no se ha encontrado el perfil.");
        }

        if(nameGroup == null || nameGroup.isEmpty() || nameCourse == null || nameCourse.isEmpty() ||
                nameSubject == null || nameSubject.isEmpty() || nameYear == null || nameYear.isEmpty()){
            throw new ApplicationException("Algun campo está vacío o nulo.");
        }

        int idGroup = getIdGroup(idProfile, nameGroup, nameCourse, nameSubject, nameYear);

        if(idGroup <= 0){
            throw new ApplicationException("No existe grupo con los datos seleccionados.");
        }

        return tableRepository.getAllTablesForNameSubject(idProfile, idGroup);
    }

    public TableResponse addTableUseCase(String token, String nameTable, String nameGroup, String nameCourse, String nameSubject, String nameYear){
        List<String> tables = new ArrayList<>();

        //check data
        if(token == null || token.isEmpty()){
            throw new ApplicationException("El Token es nulo o vacío.");
        }

        //if not selected group, return empty list
        if(nameGroup == null || nameGroup.isEmpty() || nameCourse == null || nameCourse.isEmpty() ||
                nameSubject == null || nameSubject.isEmpty() || nameYear == null || nameYear.isEmpty()){
            return new TableResponse(tables);
        }

        //extract idProfile and check if exist into the system
        int idProfile = getProfileId(token);

        if(idProfile <= 0){
            throw new ApplicationException("Error al recuperar el usuario, el perfil no existe.");
        }

        //extract idGroup and check if exist into the system
        int idGroup = getIdGroup(idProfile, nameGroup, nameCourse, nameSubject, nameYear);

        if(idGroup <= 0){
            throw new ApplicationException("Error al recuperar el grupo.");
        }

        //create table
        Table newTable = tableService.addTable(nameTable, idProfile, idGroup);

        //save table
        try{
            newTable = tableRepository.updateTable(newTable);
        }
        catch(ApplicationException e){
            throw new ApplicationException("Error al crear la tabla.");
        }

        //return response with all tables for this group and user
        if(newTable != null){
            tables = getAllTablesForUser(idProfile, nameGroup, nameCourse, nameSubject, nameYear);
        }
        return new TableResponse(tables);
    }

    public int getIdGroup(int idProfile, String nameGroup, String nameCourse, String nameSubject, String nameYear){
        //extract idYear
        int idYear = yearRepository.getIdYearForUser(idProfile, nameYear);

        if(idYear <= 0){
            throw new ApplicationException("Error al recuperar el año.");
        }

        //extract idCourse
        int idCourse = courseRepository.getIdCourseForUserYear(idYear, nameCourse);

        if(idCourse <= 0){
            throw new ApplicationException("Error al recuperar el curso.");
        }

        //extract idSubject
        int idSubject = subjectRepository.getIdSubjectForUser(idProfile, nameSubject);

        if(idSubject <= 0){
            throw new ApplicationException("Error al recuperar la asignatura.");
        }

        int idGroup = groupRepository.getGroup(idCourse, idSubject, nameGroup).getIdGroup();

        if(idGroup <= 0){
            return 0;
        }
        return idGroup;
    }
}
