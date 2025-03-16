package com.axel.notebook.API.controllers;

import com.axel.notebook.application.DTOs.GroupResponse;
import com.axel.notebook.application.services.IManageGroupUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
@CrossOrigin(origins = "http://localhost:3000")
public class GroupController {
    //dependency injection
    private final IManageGroupUseCase manageGroupUseCase;

    //Constructor
    @Autowired
    public GroupController(final IManageGroupUseCase manageGroupUseCase) {
        this.manageGroupUseCase = manageGroupUseCase;
    }

    //endpoints
    @PostMapping("/addGroup")
    public ResponseEntity<?> addGroup(@RequestParam String token,
                                      @RequestParam String nameCourse,
                                      @RequestParam String nameSubject,
                                      @RequestParam String nameYear,
                                      @RequestParam String nameGroup) {
        GroupResponse groupResponse = manageGroupUseCase.addGroupUseCase(token, nameCourse, nameSubject, nameYear, nameGroup);
        return new ResponseEntity<>(groupResponse, HttpStatus.OK);
    }

    @GetMapping("/getGroup")
    public ResponseEntity<?> getGroups(@RequestParam String token,
                                       @RequestParam String nameCourse,
                                       @RequestParam String nameSubject,
                                       @RequestParam String nameYear) {
        GroupResponse groupResponse = manageGroupUseCase.getAllGroupsUseCase(token, nameCourse, nameSubject, nameYear);
        return new ResponseEntity<>(groupResponse, HttpStatus.OK);
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
