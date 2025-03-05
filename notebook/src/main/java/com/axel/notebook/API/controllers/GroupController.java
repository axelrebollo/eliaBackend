package com.axel.notebook.API.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
@CrossOrigin(origins = "http://localhost:3000")
public class GroupController {

    //endpoints
    @PostMapping("/addGroup")
    public ResponseEntity<?> addGroup() {
        //TODO
        return null;
    }

    @GetMapping("/getGroup")
    public ResponseEntity<?> getGroup() {
        //TODO
        return null;
    }

    @PutMapping("/updateGroup")
    public ResponseEntity<?> updateGroup() {
        //TODO
        return null;
    }

    @PatchMapping("/updateNameGroup")
    public ResponseEntity<?> updateNameGroup() {
        //TODO
        return null;
    }

    @DeleteMapping("/deleteGroup")
    public ResponseEntity<?> deleteGroup() {
        //TODO
        return null;
    }
}
