package com.axel.notebook.API.controllers;

import com.axel.notebook.application.DTOs.CellResponse;
import com.axel.notebook.application.services.IManageCellUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cells")
@CrossOrigin(origins = "http://localhost:3000")
public class CellController {
    //Dependency injection
    private final IManageCellUseCase manageCellUseCase;

    //Constructor
    @Autowired
    public CellController(IManageCellUseCase manageCellUseCase) {
        this.manageCellUseCase = manageCellUseCase;
    }

    //endpoints

    @GetMapping("/getTable")
    public ResponseEntity<?> getCell(@RequestParam String token,
                                     @RequestParam String nameSubject,
                                     @RequestParam String nameYear,
                                     @RequestParam String nameCourse,
                                     @RequestParam String nameGroup,
                                     @RequestParam String nameTable) {
        CellResponse cellsResponse = manageCellUseCase.getCellsFromTableUseCase(token, nameSubject, nameYear,
                nameCourse, nameGroup, nameTable);
        return new ResponseEntity<>(cellsResponse, HttpStatus.OK);
    }

    @PutMapping("/addTaskLeft")
    public ResponseEntity<?> addTaskLeft(@RequestParam String token,
                                          @RequestParam String classCode,
                                          @RequestParam String nameNewTask,
                                          @RequestParam String nameReferenceTask,
                                          @RequestParam String nameSubject,
                                          @RequestParam String nameYear,
                                          @RequestParam String nameCourse,
                                          @RequestParam String nameGroup){
        CellResponse cellResponse = manageCellUseCase.addTaskUseCase(token, classCode, nameNewTask, nameReferenceTask,
                nameSubject, nameYear, nameCourse, nameGroup);
        return new ResponseEntity<>(cellResponse, HttpStatus.OK);
    }
}