package com.axel.notebook.API.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cells")
@CrossOrigin(origins = "http://localhost:3000")
public class CellController {

    //endpoints


    @GetMapping("/getTable")
    public ResponseEntity<?> getCell() {
        //TODO
        return null;
    }

    @PostMapping("/addTask")
    public ResponseEntity<?> addCell() {
        //TODO
        return null;
    }
}
