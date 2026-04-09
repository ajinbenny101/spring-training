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

    // TODO 1: Create a GET /api/health endpoint
    //
    // It should return a JSON object with two fields:
    //   "status"  -> "UP"
    //   "service" -> "banking-api"
    //
    // How:
    //   - Add @GetMapping("/api/health") above a new method
    //   - The method should return Map<String, String>
    //   - Use Map.of("status", "UP", "service", "banking-api") to create the response
    //   - Spring automatically converts the Map to JSON — you just return it


    // TODO 2: Create a GET /api/welcome endpoint
    //
    // It should accept an optional query parameter called "name"
    //   - If no name is provided, default to "Customer"
    //   - Return: { "message": "Welcome to the Banking API, <name>!" }
    //
    // How:
    //   - Add @GetMapping("/api/welcome") above a new method
    //   - Add a parameter: @RequestParam(required = false, defaultValue = "Customer") String name
    //   - Return Map.of("message", "Welcome to the Banking API, " + name + "!")

}
