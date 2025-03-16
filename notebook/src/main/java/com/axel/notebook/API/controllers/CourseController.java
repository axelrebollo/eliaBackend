package com.axel.notebook.API.controllers;

import com.axel.notebook.application.DTOs.CourseResponse;
import com.axel.notebook.application.services.IManageCourseUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {
    //dependency injection
    private final IManageCourseUseCase manageCourseUseCase;

    //constructor
    @Autowired
    public CourseController(final IManageCourseUseCase manageCourseUseCase) {
        this.manageCourseUseCase = manageCourseUseCase;
    }

    //endpoints

    //add course into year for one teacher
    @PostMapping("/addCourse")
    public ResponseEntity<?> addCourse(@RequestParam String token,
                                       @RequestParam String nameCourse,
                                       @RequestParam String nameYear) {
        CourseResponse courseResponse = manageCourseUseCase.addCourseUseCase(token, nameCourse, nameYear);
        return new ResponseEntity<>(courseResponse, HttpStatus.OK);
    }

    //get all courses for one teacher
    @GetMapping("/getCourses")
    public ResponseEntity<?> getCourses(@RequestParam String token, @RequestParam String nameYear) {
        CourseResponse courseResponse = manageCourseUseCase.getAllCoursesUseCase(token, nameYear);
        return new ResponseEntity<>(courseResponse, HttpStatus.OK);
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

    @DeleteMapping("/deleteCourse")
    public ResponseEntity<?> deleteCourse() {
        //TODO
        return null;
    }
}
