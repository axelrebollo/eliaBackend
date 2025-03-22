package com.axel.notebook.infrastructure.repositories;

import com.axel.notebook.application.repositories.IStudentCellRepository;
import com.axel.notebook.infrastructure.JpaEntities.StudentCellEntity;
import com.axel.notebook.infrastructure.exceptions.InfrastructureException;
import com.axel.notebook.infrastructure.persistence.JpaStudentCellRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class StudentCellRepository implements IStudentCellRepository {
    //Dependency injection
    private final JpaStudentCellRepository jpaStudentCellRepository;

    //Constructor
    public StudentCellRepository(JpaStudentCellRepository jpaStudentCellRepository) {
        this.jpaStudentCellRepository = jpaStudentCellRepository;
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
}
