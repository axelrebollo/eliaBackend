package com.axel.user.API.controllers;

import com.axel.user.application.DTOs.ProfileResponse;
import com.axel.user.application.exceptions.ApplicationException;
import com.axel.user.application.services.IManageProfileUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/profiles")
@CrossOrigin(origins = "http://localhost:3000")
public class ProfileController {

    //Dependency injection
    private final IManageProfileUseCase manageProfileUserCase;

    //Constructor
    @Autowired
    public ProfileController(IManageProfileUseCase manageProfileUserCase) {
        this.manageProfileUserCase = manageProfileUserCase;
    }

    //Endpoints
    @GetMapping("/getProfile")
    public ResponseEntity<?> getProfile(@RequestParam String token) {
        ProfileResponse profileResponse = manageProfileUserCase.getProfile(token);
        return new ResponseEntity<>(profileResponse, HttpStatus.OK);
    }

    @PostMapping("updateProfile")
    public ResponseEntity<?> updateProfile(@RequestParam String token, @RequestParam String name,
                                           @RequestParam String surname1, @RequestParam String surname2) {
        ProfileResponse profileResponse = manageProfileUserCase.updateProfile(token, name, surname1,surname2);
        return new ResponseEntity<>(profileResponse, HttpStatus.OK);
    }
}
