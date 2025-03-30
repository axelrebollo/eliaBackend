package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.IStudentCellRepository;
import com.axel.notebook.infrastructure.JpaEntities.StudentCellEntity;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaCellRepository;
import com.axel.notebook.infrastructure.persistence.JpaStudentCellRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentCellRepositoryImpl implements IStudentCellRepository {
    //Dependency injection
    private final JpaStudentCellRepository jpaStudentCellRepository;
    private final JpaCellRepository jpaCellRepository;

    //Constructor
    public StudentCellRepositoryImpl(JpaStudentCellRepository jpaStudentCellRepository, JpaCellRepository jpaCellRepository) {
        this.jpaStudentCellRepository = jpaStudentCellRepository;
        this.jpaCellRepository = jpaCellRepository;
    }

    public int getIdStudentByIdCell(int idCell){
        if(idCell <= 0){
            throw new InfrastructureException("El identificador de celda no existe o es nulo.");
        }

        StudentCellEntity student = jpaStudentCellRepository.findStudentCellEntityById(idCell);

        if(student == null){
            throw new InfrastructureException("En la celda no existe ningun estudiante.");
        }
        return student.getIdProfile();
    }

    public StudentCellEntity getStudentByIdCell(int idCell){
        if(idCell <= 0){
            throw new InfrastructureException("El identificador de celda no existe o es nulo.");
        }

        StudentCellEntity student = jpaStudentCellRepository.findStudentCellEntityById(idCell);

        if(student == null){
            throw new InfrastructureException("En la celda no existe ningun estudiante.");
        }
        return student;
    }

    public int getIdCellByIdStudent(int idStudent, int idTable){
        if(idStudent <= 0 || idTable <= 0){
            throw new InfrastructureException("El identificador del perfil del estudiante no es correcto.");
        }

        List<StudentCellEntity> students = jpaStudentCellRepository.findStudentCellEntityByIdProfile(idStudent);
        if(students == null){
            throw new InfrastructureException("No se ha encontrado el estudiante en la base de datos.");
        }
        for(StudentCellEntity student : students){
            //student.getIdCell() -  idTable
            List<Integer> studentsIntoTable = jpaCellRepository.findByTableIdAndStudentType(idTable);
            if(studentsIntoTable == null){
                throw new InfrastructureException("Error buscando el estudiante en la base de datos.");
            }

            for(Integer idCellStudent : studentsIntoTable){
                if(idCellStudent == student.getIdCell()){
                    return student.getIdCell();
                }
            }
        }
        return -1;
    }

    public boolean deleteStudent(){
        //TODO
        return false;
    }
}
