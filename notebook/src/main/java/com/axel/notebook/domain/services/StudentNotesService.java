package com.axel.notebook.domain.services;

import com.axel.notebook.domain.exceptions.DomainException;
import com.axel.notebook.domain.valueObjects.ClassroomProfile;
import com.axel.notebook.domain.valueObjects.StudentClassroomNotes;

import java.util.*;

public class StudentNotesService {

    //insert class code into list and create rows for classroom
    public List<StudentClassroomNotes> addClassrooms(List<ClassroomProfile> classroomsData) {
        List<StudentClassroomNotes> result = new ArrayList<>();

        if (classroomsData.isEmpty()) {
            return result;
        }

        for(ClassroomProfile classroom : classroomsData) {
            StudentClassroomNotes studentClassroomNotes = new StudentClassroomNotes(classroom.getClassCode(), null, null);
            result.add(studentClassroomNotes);
        }
        return result;
    }

    //insert for class code the name of subject
    public List<StudentClassroomNotes> addSubjects(List<ClassroomProfile> classroomsData, List<StudentClassroomNotes> listToResponse){
        if (classroomsData.isEmpty()) {
            return listToResponse;
        }

        for(ClassroomProfile classroom : classroomsData) {
            for(StudentClassroomNotes classroomFromList : listToResponse) {
                if (classroomFromList.getClassCode().equals(classroom.getClassCode())) {
                    classroomFromList.setNameSubject(classroom.getNameSubject());
                }
            }
        }
        return listToResponse;
    }

    //insert into classroom tasks that exists
    public List<StudentClassroomNotes> addTasks(String classCode, ArrayList<String> tasks, List<StudentClassroomNotes> listToResponse){
        Map<String,Double> tasksMap = new LinkedHashMap<>();

        if(classCode == null || classCode.isEmpty() || tasks == null || tasks.isEmpty() || listToResponse == null || listToResponse.isEmpty()) {
           throw new DomainException("Faltan datos para insertar las tareas en la clase.");
        }

        for(String task : tasks) {
            tasksMap.put(task, -1.0);
        }

        for(StudentClassroomNotes classroomFromList : listToResponse){
            if(classroomFromList.getClassCode().equals(classCode)) {
                classroomFromList.setNotes(tasksMap);
            }
        }
        return listToResponse;
    }

    //insert notes for tasks into classrooms
    public List<StudentClassroomNotes> addNotes(String classCode, ArrayList<Double> notes, List<StudentClassroomNotes> listToResponse){
        if(classCode == null || classCode.isEmpty() || notes == null || notes.isEmpty() || listToResponse == null || listToResponse.isEmpty()) {
            throw new DomainException("Faltan datos para insertar las notas en la clase.");
        }

        for(StudentClassroomNotes classroomFromList : listToResponse){
            if(classroomFromList.getClassCode().equals(classCode)) {
                Map<String,Double> tasksAndNotes = classroomFromList.getNotes();

                if(notes.size() != tasksAndNotes.size()){
                    throw new DomainException("El número de notas no coincide con el número de tareas.");
                }

                int index = 0;
                for(String task : tasksAndNotes.keySet()) {
                    tasksAndNotes.put(task, notes.get(index));
                    index++;
                }
            }
        }
        return listToResponse;
    }
}
