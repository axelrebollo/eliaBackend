package com.axel.notebook.API.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/years")
@CrossOrigin(origins = "http://localhost:3000")
public class YearController {

    //endpoints

    //create year
    @PostMapping("/addYear")
    public ResponseEntity<?> addYear() {
        //create a new year
        //TODO
        return null;
    }

    //get all years
    @GetMapping("/getYears")
    public ResponseEntity<?> getYears() {
        //get all years
        //TODO
        return null;
    }

    //update year
    @PutMapping("/updateYear")
    public ResponseEntity<?> updateYear() {
        //update name year
        //TODO
        return null;
    }

    //delete year cascade
    @DeleteMapping("/deleteYear")
    public ResponseEntity<?> deleteYear() {
        //delete year + course + group + table + cells
        //TODO
        return null;
    }
}
