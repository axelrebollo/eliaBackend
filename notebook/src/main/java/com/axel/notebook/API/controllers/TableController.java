package com.axel.notebook.API.controllers;

import com.axel.notebook.application.DTOs.TableResponse;
import com.axel.notebook.application.services.IManageTableUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tables")
@CrossOrigin(origins = "http://localhost:3000")
public class TableController {
    //Dependency injection
    private final IManageTableUseCase manageTableUseCase;

    //Constructor
    @Autowired
    public TableController(IManageTableUseCase manageTableUseCase){
        this.manageTableUseCase = manageTableUseCase;
    }

    //endpoints
    @PostMapping("/addTable")
    public ResponseEntity<?> addTable(@RequestParam String token, @RequestParam String nameTable,
                                      @RequestParam String nameGroup, @RequestParam String nameCourse,
                                      @RequestParam String nameSubject, @RequestParam String nameYear) {
        TableResponse tableResponse = manageTableUseCase.addTableUseCase(token, nameTable, nameGroup, nameCourse, nameSubject, nameYear);
        return new ResponseEntity<>(tableResponse, HttpStatus.OK);
    }

    @GetMapping("/getTables")
    public ResponseEntity<?> getTables(@RequestParam String token, @RequestParam String nameGroup,
                                       @RequestParam String nameCourse, @RequestParam String nameSubject,
                                       @RequestParam String nameYear) {
        TableResponse tableResponse = manageTableUseCase.getAllTablesFromTokenUseCase(token, nameGroup, nameCourse, nameSubject, nameYear);
        return new ResponseEntity<>(tableResponse, HttpStatus.OK);
    }
}
