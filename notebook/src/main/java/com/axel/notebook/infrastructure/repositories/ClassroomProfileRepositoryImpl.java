package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.IClassroomProfileRepository;
import com.axel.notebook.domain.services.ClassroomProfileService;
import com.axel.notebook.domain.valueObjects.ClassroomProfile;
import com.axel.notebook.infrastructure.JpaEntities.CellEntity;
import com.axel.notebook.infrastructure.JpaEntities.TableEntity;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaCellRepository;
import com.axel.notebook.infrastructure.persistence.JpaTableRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ClassroomProfileRepositoryImpl implements IClassroomProfileRepository {
    //Dependency injection
    private final JpaTableRepository jpaTableRepository;
    private final JpaCellRepository jpaCellRepository;
    private final ClassroomProfileService classroomProfileService;  //domain service

    //Constructor
    public ClassroomProfileRepositoryImpl(JpaTableRepository jpaTableRepository,
                                          JpaCellRepository jpaCellRepository) {
        this.jpaTableRepository = jpaTableRepository;
        this.jpaCellRepository = jpaCellRepository;
        this.classroomProfileService = new ClassroomProfileService();
    }

    //get data with teacher id
    public List<ClassroomProfile> getTeacherDataByIdProfile(int idProfile){
        List<ClassroomProfile> rows = new ArrayList<>();

        //check
        if(idProfile <= 0){
            throw new InfrastructureException("El identificador de usuario no es correcto o el usuario no existe.");
        }

        //get all classrooms for this teacher
        List<TableEntity> classrooms = jpaTableRepository.findByIdTeacher(idProfile);

        if(classrooms == null){
            throw new InfrastructureException("El identificador de usuario es erroneo.");
        }

        //iteration for classrooms
        for(TableEntity classroom : classrooms){
            TableEntity table = jpaTableRepository.findById(classroom.getIdTable());

            ClassroomProfile newClassroom = classroomProfileService.addClassroomProfile(
                    classroom.getClassCode(),
                    classroom.getGroup().getCourse().getYear().getNameYear(),
                    classroom.getGroup().getCourse().getNameCourse(),
                    classroom.getGroup().getNameGroup(),
                    classroom.getGroup().getSubject().getNameSubject(),
                    table.getNameTable()
            );
            rows.add(newClassroom);
        }
        return rows;
    }

    public List<ClassroomProfile> getStudentDataByIdProfile(int idProfile){
        List<ClassroomProfile> rows = new ArrayList<>();

        //check
        if(idProfile <= 0){
            throw new InfrastructureException("El identificador de usuario no es correcto o el usuario no existe.");
        }

        //get all classrooms for this student
        List<CellEntity> classrooms = jpaCellRepository.findByIdStudent(idProfile);

        if(classrooms == null){
            throw new InfrastructureException("El identificador de usuario es erroneo.");
        }

        //iteration for classrooms
        for(CellEntity classroom : classrooms){
            TableEntity table = jpaTableRepository.findById(classroom.getTable().getIdTable());

            ClassroomProfile newClassroom = classroomProfileService.addClassroomProfile(
                    classroom.getTable().getClassCode(),
                    classroom.getTable().getGroup().getCourse().getYear().getNameYear(),
                    classroom.getTable().getGroup().getCourse().getNameCourse(),
                    classroom.getTable().getGroup().getNameGroup(),
                    classroom.getTable().getGroup().getSubject().getNameSubject(),
                    table.getNameTable()
            );
            rows.add(newClassroom);
        }
        return rows;
    }
}
