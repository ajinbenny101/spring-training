package com.training.banking.exception;

import com.training.banking.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global Exception Handler - the "complaints department" of the application.
 *
 * Instead of every controller method handling errors on its own
 * (with if/else checks and manual ResponseEntity building), this
 * class catches exceptions thrown ANYWHERE in the app and converts
 * them into clean, consistent error responses.
 *
 * Think of a bank with 50 tellers. Without this, each teller handles
 * complaints differently - one writes a note, another calls the manager,
 * a third just shrugs. With a central complaints department, every
 * problem gets handled the same professional way.
 *
 * How it works:
 *   1. Controller calls service
 *   2. Service throws an exception (e.g., AccountNotFoundException)
 *   3. Spring catches the exception and looks for a matching @ExceptionHandler
 *   4. This class has a method for that exception type
 *   5. That method builds an ErrorResponse and returns it with the right HTTP status
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFound(AccountNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateAccount(DuplicateAccountException ex) {
        ErrorResponse error = new ErrorResponse("CONFLICT", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFunds(InsufficientFundsException ex) {
        ErrorResponse error = new ErrorResponse("BAD_REQUEST", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
