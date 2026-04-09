package com.training.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for validation errors.
 *
 * When Spring validates a request body (because of @Valid) and finds
 * problems, it throws a MethodArgumentNotValidException. Without this
 * handler, the client would see an ugly 403 error with a huge stack trace.
 *
 * This class catches that exception and returns a clean JSON response
 * like this:
 *
 *   {
 *     "accountNumber": "Account number must be 8 digits",
 *     "holderName": "must not be blank"
 *   }
 *
 * Think of it like a receptionist at a bank. When the form checker
 * (validation) finds problems, the receptionist doesn't dump a pile
 * of internal paperwork on the customer. Instead, they hand back the
 * form with neat notes: "This field is missing" and "This format is wrong."
 *
 * SOLUTION FILE - this is the completed version.
 */
// TODO 5: Add @RestControllerAdvice annotation to this class
@RestControllerAdvice
public class ValidationExceptionHandler {

    // TODO 6: Add a method to handle MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        // TODO 7: Extract field errors and return them
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
