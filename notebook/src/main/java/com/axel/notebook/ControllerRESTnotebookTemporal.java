package com.axel.notebook;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerRESTnotebookTemporal {
    @GetMapping("/hello")
    public String sayHello() {
        return "¡Hola desde Spring Boot!";
    }
}
