package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.IClassroomProfileRepository;
import com.axel.notebook.domain.services.ClassroomProfileService;
import com.axel.notebook.domain.valueObjects.ClassroomProfile;
import com.axel.notebook.infrastructure.JpaEntities.CellEntity;
import com.axel.notebook.infrastructure.JpaEntities.TableEntity;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaCellRepository;
import com.axel.notebook.infrastructure.persistence.JpaStudentCellRepository;
import com.axel.notebook.infrastructure.persistence.JpaTableRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ClassroomProfileRepositoryImpl implements IClassroomProfileRepository {
    //Dependency injection
    private final JpaTableRepository jpaTableRepository;
    private final JpaStudentCellRepository jpaStudentCellRepository;
    private final ClassroomProfileService classroomProfileService;  //domain service
    private final JpaCellRepository jpaCellRepository;

    //Constructor
    public ClassroomProfileRepositoryImpl(JpaTableRepository jpaTableRepository,
                                          JpaStudentCellRepository jpaStudentCellRepository, JpaCellRepository jpaCellRepository) {
        this.jpaTableRepository = jpaTableRepository;
        this.jpaStudentCellRepository = jpaStudentCellRepository;
        this.classroomProfileService = new ClassroomProfileService();
        this.jpaCellRepository = jpaCellRepository;
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

            if(table == null){
                throw new InfrastructureException("Error al recuperar las tablas. Están vacías.");
            }

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

        //for one student get all times that this student exist into one table
        List<Integer> listIdCell = jpaStudentCellRepository.findAllCellsForStudent(idProfile);

        List<CellEntity> cellEntityes = new ArrayList<>();
        //find cells that appear this student
        for(int idCell : listIdCell){
            //convert optional in CellEntity
            Optional<CellEntity> cellsForStudent = jpaCellRepository.findById(idCell);
            if(!cellsForStudent.isEmpty()){
                CellEntity cell = cellsForStudent.get();
                cellEntityes.add(cell);
            }
        }

        if(cellEntityes == null){
            throw new InfrastructureException("El identificador de usuario es erroneo.");
        }

        //extract for this list the tables that is included
        for(CellEntity studentCell : cellEntityes){
            if(studentCell == null){
                throw new InfrastructureException("Error al recuperar las tablas. Están vacías.");
            }

            ClassroomProfile newClassroom = classroomProfileService.addClassroomProfile(
                    studentCell.getTable().getClassCode(),
                    studentCell.getTable().getGroup().getCourse().getYear().getNameYear(),
                    studentCell.getTable().getGroup().getCourse().getNameCourse(),
                    studentCell.getTable().getGroup().getNameGroup(),
                    studentCell.getTable().getGroup().getSubject().getNameSubject(),
                    studentCell.getTable().getNameTable()
            );
            rows.add(newClassroom);
        }
        return rows;
    }
}
