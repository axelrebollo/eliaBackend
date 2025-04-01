package com.axel.notebook.API.controllers;

import com.axel.notebook.application.DTOs.DeleteResponse;
import com.axel.notebook.application.DTOs.YearResponse;
import com.axel.notebook.application.services.IManageYearUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/years")
@CrossOrigin(origins = "http://localhost:3000")
public class YearController {

    //Dependency injection
    private final IManageYearUseCase manageYearUseCase;

    //Constructor
    @Autowired
    public YearController(IManageYearUseCase manageYearUseCase) {
        this.manageYearUseCase = manageYearUseCase;
    }

    //ENDPOINTS

    //create year
    @PostMapping("/addYear")
    public ResponseEntity<?> addYear(@RequestParam String token, @RequestParam String nameYear) {
        YearResponse yearResponse = manageYearUseCase.addYearUseCase(token, nameYear);
        return new ResponseEntity<>(yearResponse, HttpStatus.OK);
    }

    //get all years
    @GetMapping("/getYears")
    public ResponseEntity<?> getYears(@RequestParam String token) {
        //get all years
        YearResponse yearResponse = manageYearUseCase.getAllYearsFromTokenUseCase(token);
        return new ResponseEntity<>(yearResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deleteYear")
    public ResponseEntity<?> deleteYear(@RequestParam String token, @RequestParam String nameYear) {
        DeleteResponse deleteResponse = manageYearUseCase.deleteYearUseCase(token, nameYear);
        return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
    }
}
