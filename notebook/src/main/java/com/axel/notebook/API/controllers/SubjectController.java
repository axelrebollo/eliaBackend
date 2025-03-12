package com.axel.notebook.API.controllers;

import com.axel.notebook.application.DTOs.SubjectResponse;
import com.axel.notebook.application.exceptions.ApplicationException;
import com.axel.notebook.application.services.IManageSubjectUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

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

    //endpoints

    //create subject
    @PostMapping("/addSubject")
    public ResponseEntity<?> addSubject(@RequestParam String token, @RequestParam String nameSubject) {
        try{
            SubjectResponse subjectResponse = manageSubjectUseCase.addSubjectUseCase(token, nameSubject);
            return new ResponseEntity<>(subjectResponse, HttpStatus.OK);
        }
        catch(ApplicationException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Error", e.getMessage(), "status", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    //get all subjects
    @GetMapping("/getSubjects")
    public ResponseEntity<?> getSubject(@RequestParam String token) {
        try{
            SubjectResponse subjectResponse = manageSubjectUseCase.getAllSubjectsFromTokenUseCase(token);
            return new ResponseEntity<>(subjectResponse, HttpStatus.OK);
        }
        catch(ApplicationException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Error", e.getMessage(), "status", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping("/updateSubject")
    public ResponseEntity<?> updateSubject() {
        //TODO
        return null;
    }

    @PatchMapping("/updateNameSubject")
    public ResponseEntity<?> updateNameSubject() {
        //TODO
        return null;
    }

    @DeleteMapping("/deleteSubject")
    public ResponseEntity<?> deleteSubject() {
        //TODO
        return null;
    }
}
