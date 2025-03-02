package com.axel.user.API.controllers;

import com.axel.user.application.DTOs.UserRequest;
import com.axel.user.application.DTOs.UserResponse;
import com.axel.user.application.DTOs.UserResponseToken;
import com.axel.user.application.exceptions.ApplicationException;
import com.axel.user.application.services.ILoginUserCase;
import com.axel.user.application.services.IRegisterUserCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final IRegisterUserCase registerUserCase;
    private final ILoginUserCase loginUserCase;

    @Autowired
    public UserController(IRegisterUserCase registerUserCase, ILoginUserCase loginUserCase) {
        this.registerUserCase = registerUserCase;
        this.loginUserCase = loginUserCase;
    }

    //Register new user endpoint
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        try{
            UserResponse userResponse  = registerUserCase.registerUser(
                    userRequest.getEmail(), userRequest.getPassword(), userRequest.getRole());
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        }
        catch(ApplicationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("Error", e.getMessage(), "status", HttpStatus.CONFLICT.value()));
        }
    }

    //login user into app
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRequest userRequest) {
        try {
            UserResponseToken userResponse = loginUserCase.loginUser(userRequest.getEmail(), userRequest.getPassword());
            return new ResponseEntity<>(userResponse, HttpStatus.ACCEPTED);
        }
        catch(ApplicationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("Error", e.getMessage(), "status", HttpStatus.UNAUTHORIZED.value()));
        }
    }
}
