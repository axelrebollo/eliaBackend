package com.axel.notebook.API.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

    //endpoints
    @PostMapping("/addCourse")
    public ResponseEntity<?> addCourse() {
        //TODO
        return null;
    }

    @GetMapping("/getCourse")
    public ResponseEntity<?> getCourse() {
        //TODO
        return null;
    }

    @PutMapping("/updateCourse")
    public ResponseEntity<?> updateCourse() {
        //TODO
        return null;
    }

    @PatchMapping("/updateNameCourse")
    public ResponseEntity<?> updateNameCourse() {
        //TODO
        return null;
    }

    @DeleteMapping("/deleteYear")
    public ResponseEntity<?> deleteCourse() {
        //TODO
        return null;
    }
}
