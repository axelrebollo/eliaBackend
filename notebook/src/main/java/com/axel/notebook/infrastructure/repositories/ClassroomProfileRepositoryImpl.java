package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.IClassroomProfileRepository;
import com.axel.notebook.domain.valueObjects.Student;
import com.axel.notebook.domain.services.ClassroomProfileService;
import com.axel.notebook.domain.valueObjects.ClassroomProfile;
import com.axel.notebook.infrastructure.JpaEntities.CellEntity;
import com.axel.notebook.infrastructure.JpaEntities.StudentCellEntity;
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
            Optional<CellEntity> cellForStudent = jpaCellRepository.findById(idCell);
            if(!cellForStudent.isEmpty()){
                CellEntity cell = cellForStudent.get();
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

    public boolean enrollStudentToTable(Student student, String classCode){
        //check data
        if(student == null){
            throw new InfrastructureException("El estudiante está vacío.");
        }

        if(classCode == null || classCode.isEmpty()){
            throw new InfrastructureException("El código de la clase está vacío o es nulo.");
        }

        //find table with classCode
        TableEntity table = jpaTableRepository.findByClassCode(classCode);

        if(table == null){
            throw new InfrastructureException("El aula/tabla no existe.");
        }

        try{
            //find all cells for this table/classroom (idCell)
            List<Integer> cellForClass = jpaCellRepository.findByTableIdAndStudentType(table.getIdTable());

            boolean existIntoClass = false;

            if(!cellForClass.isEmpty()){
                for(Integer studentIntoClass : cellForClass){
                    //find all profiles that exist into classroom
                    List<Integer> profiles = jpaStudentCellRepository.findProfileForIdCell(studentIntoClass);

                    //if exists student into classroom change existsIntoClass
                    if(!profiles.isEmpty()){
                        for(int profile : profiles){
                            if (student.getIdProfile() == profile) {
                                existIntoClass = true;
                                break;
                            }
                        }
                    }
                }
            }

            //if student exist into class
            if(existIntoClass){
               throw new InfrastructureException("El estudiante ya existe en la clase.");
            }

            //find the last row and col
            int lastPositionRow = jpaCellRepository.countStudentsIntoTable(table.getIdTable());
            int lastPositionCol = jpaCellRepository.countTasksIntoTable(table.getIdTable()) ;

            //space for cell 0,0 contains "Alumnos" header
            if(lastPositionRow == 0){
                lastPositionRow = 1;
            }
            else{
                lastPositionRow = lastPositionRow + 1;
            }

            CellEntity newCellStudent = new StudentCellEntity(table, lastPositionRow, lastPositionCol, student.getIdProfile());
            //save into cellEntity and StudentCellEntity
            CellEntity savedCellStudent = jpaCellRepository.save(newCellStudent);

            if(savedCellStudent != null){
                return true;
            }
        }
        catch(InfrastructureException e){
            throw new InfrastructureException(e.getMessage(), e);
        }
        return false;
    }
}
