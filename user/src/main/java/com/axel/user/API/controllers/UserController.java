package com.axel.user.API.controllers;

import com.axel.user.application.DTOs.UserRequest;
import com.axel.user.application.DTOs.UserResponse;
import com.axel.user.application.DTOs.UserResponseToken;
import com.axel.user.application.services.ILoginUserCase;
import com.axel.user.application.services.IRegisterUserCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    //Dependency injection
    private final IRegisterUserCase registerUserCase;
    private final ILoginUserCase loginUserCase;

    //Constructor
    @Autowired
    public UserController(IRegisterUserCase registerUserCase, ILoginUserCase loginUserCase) {
        this.registerUserCase = registerUserCase;
        this.loginUserCase = loginUserCase;
    }

    //Endpoints

    //Register new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse  = registerUserCase.registerUser(
                userRequest.getEmail(), userRequest.getPassword(), userRequest.getRole());
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    //Login user into app
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRequest userRequest) {
        UserResponseToken userResponse = loginUserCase.loginUser(userRequest.getEmail(), userRequest.getPassword());
        return new ResponseEntity<>(userResponse, HttpStatus.ACCEPTED);
    }
}
