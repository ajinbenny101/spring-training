package com.training.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@SpringBootApplication
@RestController
public class Lab00HelloSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab00HelloSpringApplication.class, args);
    }

    @GetMapping("/api/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "banking-api");
    }

    @GetMapping("/api/welcome")
    public Map<String, String> welcome(
            @RequestParam(required = false, defaultValue = "Customer") String name) {
        return Map.of("message", "Welcome to the Banking API, " + name + "!");
    }
}
