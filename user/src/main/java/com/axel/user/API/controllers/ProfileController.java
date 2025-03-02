package com.axel.user.API.controllers;

import com.axel.user.API.exceptions.APIException;
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

    private final IManageProfileUseCase manageProfileUserCase;

    @Autowired
    public ProfileController(IManageProfileUseCase manageProfileUserCase) {
        this.manageProfileUserCase = manageProfileUserCase;
    }

    @GetMapping("/getProfile")
    public ResponseEntity<?> getProfile(@RequestParam String token) {
        try{
            ProfileResponse profileResponse = manageProfileUserCase.getProfile(token);
            return new ResponseEntity<>(profileResponse, HttpStatus.OK);
        }
        catch(ApplicationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("Error", e.getMessage(), "status", HttpStatus.UNAUTHORIZED.value()));
        }
    }
}
