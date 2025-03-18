package com.axel.notebook.API.controllers;

import com.axel.notebook.application.DTOs.ClassroomProfileResponse;
import com.axel.notebook.application.services.IManageClassroomProfileUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classroomProfile")
@CrossOrigin(origins = "http://localhost:3000")
public class ClassroomProfileController {
    //Dependency injection
    private final IManageClassroomProfileUseCase manageClassroomProfileUseCase;

    //Constructor
    @Autowired
    public ClassroomProfileController(IManageClassroomProfileUseCase manageClassroomProfileUseCase) {
        this.manageClassroomProfileUseCase = manageClassroomProfileUseCase;
    }

    //Endpoints
    @GetMapping("/getClassroomsData")
    public ResponseEntity<?> getClassroomsCreated(@RequestParam String token) {
        ClassroomProfileResponse classroomProfileResponse = manageClassroomProfileUseCase.getClassroomsForProfile(token);
        return new ResponseEntity<>(classroomProfileResponse, HttpStatus.OK);
    }
}
