package com.axel.notebook.API.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tables")
@CrossOrigin(origins = "http://localhost:3000")
public class TableController {

    //endpoints
    @PostMapping("/addTable")
    public ResponseEntity<?> addTable() {
        //TODO
        return null;
    }

    @GetMapping("/getTable")
    public ResponseEntity<?> getTable() {
        //TODO
        return null;
    }

    @PutMapping("/updateTable")
    public ResponseEntity<?> updateTable() {
        //TODO
        return null;
    }

    @PatchMapping("/updateNameTable")
    public ResponseEntity<?> updateNameTable() {
        //TODO
        return null;
    }

    @DeleteMapping("/deleteTable")
    public ResponseEntity<?> deleteTable() {
        //TODO
        return null;
    }
}
