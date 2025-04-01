package com.axel.notebook.application.repositories;

import com.axel.notebook.domain.entities.Subject;

import java.util.List;

public interface ISubjectRepository {
    //find all Subjects for user and check that this Subject not exists
    public Boolean existSubjectForUser(String nameSubject, int idProfile);

    //update Subject that user are created
    public Subject updateSubject(Subject subject);

    //get all names Subjects that one user are created
    public List<String> getAllSubjectsNameForUser(int idProfile);

    //get all subjects that onw user are created
    public List<Subject> getAllSubjectsForUser(int idProfile);

    //find idSubject for idProfile and nameSubject
    public int getIdSubjectForUser(int idProfile, String nameSubject);

    //delete Subjects that user are created
    public boolean deleteSubject(int idUser, String nameSubject);
}
