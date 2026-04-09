package com.training.banking.config;

import org.springframework.web.client.RestTemplate;

/**
 * Application configuration - the setup room.
 *
 * This class prepares shared tools that the rest of the app can use.
 * Right now, we need one tool: RestTemplate (for making HTTP calls to other servers).
 *
 * Think of this like setting up a shared phone in the office.
 * You set it up once, and any department can use it to call external services.
 */

// ----------------------------------------------------------------
// TODO 1: Add the @Configuration annotation to this class
//
// @Configuration tells Spring: "This class contains setup instructions.
// Look inside for @Bean methods and run them when the app starts."
//
// You will need this import:
//   import org.springframework.context.annotation.Configuration;
//
// Add the annotation right above the class declaration below.
// ----------------------------------------------------------------
public class AppConfig {

    // ----------------------------------------------------------------
    // TODO 2: Create a RestTemplate bean
    //
    // Add the @Bean annotation to this method, and make it return
    // a new RestTemplate instance.
    //
    // You will need this import:
    //   import org.springframework.context.annotation.Bean;
    //
    // Steps:
    //   1. Add @Bean above this method
    //   2. Replace "return null" with "return new RestTemplate()"
    //
    // This tells Spring: "Create a RestTemplate object once and keep it
    // ready. When any class asks for a RestTemplate, give them this one."
    // ----------------------------------------------------------------
    public RestTemplate restTemplate() {
        // Replace this with: return new RestTemplate();
        return null;
    }
}
