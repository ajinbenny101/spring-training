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

// =======================================================================
// TODO 4: Add the @RestControllerAdvice annotation to this class
//
// This tells Spring: "this class handles exceptions for ALL controllers."
// Without it, Spring does not know this class exists and your custom
// exceptions will just produce ugly 500 errors.
// =======================================================================
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFound(AccountNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateAccount(DuplicateAccountException ex) {
        ErrorResponse errorResponse = new ErrorResponse("CONFLICT", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFunds(InsufficientFundsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // ===================================================================
    // TODO 5: Handle AccountNotFoundException
    //
    // Steps:
    //   1. Add the @ExceptionHandler annotation with the exception class
    //      Example: @ExceptionHandler(AccountNotFoundException.class)
    //   2. Create a method that takes AccountNotFoundException as a parameter
    //   3. Build an ErrorResponse with:
    //      - error: "NOT_FOUND"
    //      - message: use ex.getMessage() to get the message from the exception
    //   4. Return ResponseEntity with status 404 (HttpStatus.NOT_FOUND)
    //      and the ErrorResponse as the body
    // ===================================================================

    // YOUR CODE HERE

    // ===================================================================
    // TODO 6: Handle DuplicateAccountException
    //
    // Same pattern as TODO 5, but:
    //   - error: "CONFLICT"
    //   - status: 409 (HttpStatus.CONFLICT)
    // ===================================================================

    // YOUR CODE HERE

    // ===================================================================
    // TODO 7: Handle InsufficientFundsException
    //
    // Same pattern as TODO 5, but:
    //   - error: "BAD_REQUEST"
    //   - status: 400 (HttpStatus.BAD_REQUEST)
    // ===================================================================

    // YOUR CODE HERE

}
