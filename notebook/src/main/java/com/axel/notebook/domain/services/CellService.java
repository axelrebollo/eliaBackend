package com.axel.notebook.domain.services;

import com.axel.notebook.domain.valueObjects.Student;

public class CellService {
    public Student addStudent(int idStudent, String name, String surname1, String surname2, int idCellStudent){
        return new Student(idStudent, name, surname1, surname2, idCellStudent);
    }
}
