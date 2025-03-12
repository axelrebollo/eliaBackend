package com.axel.notebook.API.controllers;

import com.axel.notebook.application.DTOs.YearResponse;
import com.axel.notebook.application.exceptions.ApplicationException;
import com.axel.notebook.application.services.IManageYearUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/years")
@CrossOrigin(origins = "http://localhost:3000")
public class YearController {

    //Dependency inyection
    private final IManageYearUseCase manageYearUseCase;

    //Constructor
    @Autowired
    public YearController(IManageYearUseCase manageYearUseCase) {
        this.manageYearUseCase = manageYearUseCase;
    }

    //endpoints

    //create year
    @PostMapping("/addYear")
    public ResponseEntity<?> addYear(@RequestParam String token, @RequestParam String nameYear) {
        try{
            YearResponse yearResponse = manageYearUseCase.addYearUseCase(token, nameYear);
            return new ResponseEntity<>(yearResponse, HttpStatus.OK);
        }
        catch(ApplicationException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Error", e.getMessage(), "status", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    //get all years
    @GetMapping("/getYears")
    public ResponseEntity<?> getYears(@RequestParam String token) {
        //get all years
        try{
            YearResponse yearResponse = manageYearUseCase.getAllYearsFromTokenUseCase(token);
            return new ResponseEntity<>(yearResponse, HttpStatus.OK);
        }
        catch(ApplicationException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Error", e.getMessage(), "status", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    //update year
    @PutMapping("/updateYear")
    public ResponseEntity<?> updateYear() {
        //update name year
        //TODO
        return null;
    }

    @PatchMapping("/updateNameYear")
    public ResponseEntity<?> updateNameYear() {
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
