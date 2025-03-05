package com.axel.notebook.API.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subjects")
@CrossOrigin(origins = "http://localhost:3000")
public class SubjectController {

    //endpoints
    @PostMapping("/addSubject")
    public ResponseEntity<?> addSubject() {
        //TODO
        return null;
    }

    @GetMapping("/getSubject")
    public ResponseEntity<?> getSubject() {
        //TODO
        return null;
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
