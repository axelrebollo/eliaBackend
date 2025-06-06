package com.axel.notebook.API.controllers;

import com.axel.notebook.application.DTOs.DeleteResponse;
import com.axel.notebook.application.DTOs.SubjectResponse;
import com.axel.notebook.application.DTOs.UpdateResponse;
import com.axel.notebook.application.services.IManageSubjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subjects")
@CrossOrigin(origins = "http://localhost:3000")
public class SubjectController {
    //Dependency Injection
    private final IManageSubjectUseCase manageSubjectUseCase;

    //Constructor
    @Autowired
    public SubjectController(final IManageSubjectUseCase manageSubjectUseCase) {
        this.manageSubjectUseCase = manageSubjectUseCase;
    }

    //ENDPOINTS

    //create subject
    @PostMapping("/addSubject")
    public ResponseEntity<?> addSubject(@RequestParam String token, @RequestParam String nameSubject) {
        SubjectResponse subjectResponse = manageSubjectUseCase.addSubjectUseCase(token, nameSubject);
        return new ResponseEntity<>(subjectResponse, HttpStatus.OK);
    }

    //get all subjects
    @GetMapping("/getSubjects")
    public ResponseEntity<?> getSubject(@RequestParam String token) {
        SubjectResponse subjectResponse = manageSubjectUseCase.getAllSubjectsFromTokenUseCase(token);
        return new ResponseEntity<>(subjectResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deleteSubject")
    public ResponseEntity<?> deleteSubject(@RequestParam String token, @RequestParam String nameSubject) {
        DeleteResponse deleteResponse = manageSubjectUseCase.deleteSubjectUseCase(token, nameSubject);
        return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
    }

    @PatchMapping("/updateNameSubject")
    public ResponseEntity<?> updateNameSubject(@RequestParam String token, @RequestParam String nameSubject, @RequestParam String newNameSubject) {
        UpdateResponse updateResponse = manageSubjectUseCase.updateSubjectUseCase(token, nameSubject, newNameSubject);
        return new ResponseEntity<>(updateResponse, HttpStatus.OK);
    }
}
