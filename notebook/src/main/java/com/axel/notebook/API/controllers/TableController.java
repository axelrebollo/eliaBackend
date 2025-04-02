package com.axel.notebook.API.controllers;

import com.axel.notebook.application.DTOs.DeleteResponse;
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

    //ENDPOINTS

    //add table to selection
    @PostMapping("/addTable")
    public ResponseEntity<?> addTable(@RequestParam String token, @RequestParam String nameTable,
                                      @RequestParam String nameGroup, @RequestParam String nameCourse,
                                      @RequestParam String nameSubject, @RequestParam String nameYear) {
        TableResponse tableResponse = manageTableUseCase.addTableUseCase(token, nameTable, nameGroup, nameCourse, nameSubject, nameYear);
        return new ResponseEntity<>(tableResponse, HttpStatus.OK);
    }

    //get table to selection
    @GetMapping("/getTables")
    public ResponseEntity<?> getTables(@RequestParam String token, @RequestParam String nameGroup,
                                       @RequestParam String nameCourse, @RequestParam String nameSubject,
                                       @RequestParam String nameYear) {
        TableResponse tableResponse = manageTableUseCase.getAllTablesFromTokenUseCase(token, nameGroup, nameCourse, nameSubject, nameYear);
        return new ResponseEntity<>(tableResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deleteTable")
    public ResponseEntity<?> deleteTable(@RequestParam String token, @RequestParam String classCode){
        DeleteResponse deleteResponse = manageTableUseCase.deleteTableUseCase(token, classCode);
        return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
    }

    @PatchMapping("/updateNameTable")
    public ResponseEntity<?> updateNameTable(@RequestParam String token,
                                         @RequestParam String nameSubject,
                                         @RequestParam String nameYear,
                                         @RequestParam String nameCourse,
                                         @RequestParam String nameGroup,
                                         @RequestParam String nameTable,
                                         @RequestParam String newNameTable){
        TableResponse tableResponse = manageTableUseCase.updateTableUseCase(token, nameSubject, nameYear, nameCourse,
                nameGroup, nameTable, newNameTable);
        return new ResponseEntity<>(tableResponse, HttpStatus.OK);
    }
}
