package com.axel.user.API.controllers;

import com.axel.user.application.DTOs.UserRequest;
import com.axel.user.application.DTOs.UserResponse;
import com.axel.user.application.useCases.UserUseCases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.axel.user.domain.exceptions.UserCreationException;   //To remove

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserUseCases userCaseRegisterUser;

    @Autowired
    public UserController(UserUseCases UCRegisterUser) {
        this.userCaseRegisterUser = UCRegisterUser;
    }

    //Register new user endpoint
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        try{
            UserResponse userResponse  = userCaseRegisterUser.registerUser(userRequest.getEmail(), userRequest.getPassword(), userRequest.getRole());
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        }
        catch(UserCreationException e){
            return new ResponseEntity<>(e.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /*
    //Return all users (to debugging)
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    /*
    //Return user from id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Update user from id
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    //Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }*/
}
