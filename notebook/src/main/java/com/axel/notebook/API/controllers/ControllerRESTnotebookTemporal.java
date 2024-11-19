package com.axel.notebook.API.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerRESTnotebookTemporal {
    @GetMapping("/hello")
    public String sayHello() {
        return "¡Hola desde Spring Boot y desde tu controlador en el microservicio notebook!";
    }
}
