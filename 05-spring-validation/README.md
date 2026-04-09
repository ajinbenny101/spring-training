# Lab 05 - Spring Validation (Annotations)

In Lab 02, you wrote ~20 lines of if/else to validate an account. In this lab, you'll do the same thing with 5 annotations.

Spring Boot has a built-in validation framework that lets you declare rules directly on your fields - no manual checking required. You annotate a field with something like `@NotBlank`, and Spring rejects the request automatically if the rule is broken.

## What you'll build

A REST API for creating bank accounts - the same idea as Lab 02, but with annotation-based validation instead of manual if/else blocks. Two endpoints:

1. **POST /api/accounts** - create an account (with annotation-based validation)
2. **GET /api/accounts** - list all accounts (no validation needed)

The POST endpoint uses `@Valid` on the request body, which triggers Spring's validation engine. If any field breaks a rule, Spring throws an exception before your code even runs. You'll write a global exception handler to catch that and return clean error messages.

## Key concepts

### 1. @Valid - the bouncer at the door

When you put `@Valid` on a `@RequestBody` parameter, you're telling Spring: "Check this object before passing it to my method." Think of it like a bouncer checking IDs at the door - if something's wrong, you don't even get inside the method.

```java
@PostMapping("/api/accounts")
public ResponseEntity<?> create(@Valid @RequestBody CreateAccountRequest request) {
    // This code only runs if ALL validations pass
}
```

### 2. @NotBlank - required field

"This field must not be null or empty." Think of a required field on a form marked with an asterisk (*). If someone submits the form with that field blank, it gets rejected.

```java
@NotBlank
private String holderName;
```

### 3. @Size - length limits

"This field must be between X and Y characters long." Like a form field that says "Enter exactly 8 characters."

```java
@Size(min = 8, max = 8)
private String accountNumber;
```

### 4. @Pattern - format matching

"This field must match a specific format." Like a form that only accepts phone numbers in a certain format - if you type letters or use the wrong separator, it gets rejected.

```java
@Pattern(regexp = "\\d{8}", message = "Account number must be 8 digits")
private String accountNumber;
```

### 5. @DecimalMin - minimum number value

"This number must be at least X." Like a bank requiring a minimum opening deposit.

```java
@DecimalMin(value = "0.0", message = "Opening balance must be 0 or positive")
private double openingBalance;
```

### 6. Handling validation errors

When validation fails, Spring throws a `MethodArgumentNotValidException`. You catch it with `@RestControllerAdvice` and return a clean error map instead of a giant stack trace.

The error response looks like this:

```json
{
  "accountNumber": "Account number must be 8 digits",
  "holderName": "must not be blank"
}
```

## Project structure

```
src/main/java/com/training/banking/
  Lab05SpringValidationApplication.java   <-- PRE-BUILT (main class)
  dto/
    CreateAccountRequest.java             <-- YOUR WORK (add validation annotations)
  model/
    Account.java                          <-- PRE-BUILT
  controller/
    AccountController.java                <-- PRE-BUILT (already uses @Valid)
  service/
    AccountService.java                   <-- PRE-BUILT
  exception/
    ValidationExceptionHandler.java       <-- YOUR WORK (global error handler)
```

## How to run

```bash
cd 05-spring-validation
./mvnw spring-boot:run
```

Or from your IDE, run `Lab05SpringValidationApplication.java`.

The app starts on **port 8085**.

## Your tasks

### Task 1 - Add validation annotations to CreateAccountRequest.java

Open `src/main/java/com/training/banking/dto/CreateAccountRequest.java`.

You'll see four fields with no validation. Add annotations to enforce these rules:

| Field | Rules |
|-------|-------|
| `holderName` | Must not be blank |
| `accountNumber` | Must not be blank, must be exactly 8 digits |
| `sortCode` | Must not be blank, must match XX-XX-XX format (digits with dashes) |
| `openingBalance` | Must be 0 or positive |

Follow the TODO comments in the file. You will need these imports:

```java
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
```

### Task 2 - Build the validation exception handler

Open `src/main/java/com/training/banking/exception/ValidationExceptionHandler.java`.

This class catches validation errors and returns a clean JSON response. Follow the TODO comments to:

1. Add `@RestControllerAdvice` to the class
2. Add a method annotated with `@ExceptionHandler(MethodArgumentNotValidException.class)`
3. Inside the method, extract field errors and build a `Map<String, String>` of field name to error message
4. Return the map with a 400 status

Hint for extracting errors:

```java
Map<String, String> errors = new HashMap<>();
ex.getBindingResult().getFieldErrors().forEach(error ->
    errors.put(error.getField(), error.getDefaultMessage())
);
```

## How to test

Use the provided `lab-05-spring-validation.rest` file in your IDE (IntelliJ or VS Code with REST Client).

### Before you add annotations (all requests return 200 - even bad ones):

```bash
# This will succeed even with garbage data - no validation yet
curl -X POST http://localhost:8085/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":"","sortCode":"","holderName":"","openingBalance":-100}'
```

### After you add annotations:

```bash
# Valid request - should return 201
curl -X POST http://localhost:8085/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":"12345678","sortCode":"12-34-56","holderName":"Jane Smith","openingBalance":500.00}'

# Blank holder name - should return 400
curl -X POST http://localhost:8085/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":"12345678","sortCode":"12-34-56","holderName":"","openingBalance":500.00}'

# Bad account number - should return 400
curl -X POST http://localhost:8085/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":"ABC","sortCode":"12-34-56","holderName":"Jane Smith","openingBalance":500.00}'

# List all accounts - should return 200
curl http://localhost:8085/api/accounts
```

## Done?

Check these off:
- [ ] POST with valid data returns 201 and the created account
- [ ] POST with blank holderName returns 400 with error message
- [ ] POST with invalid accountNumber returns 400 with "Account number must be 8 digits"
- [ ] POST with invalid sortCode returns 400 with "Sort code must be in XX-XX-XX format"
- [ ] POST with negative openingBalance returns 400 with "Opening balance must be 0 or positive"
- [ ] GET /api/accounts returns all created accounts
- [ ] Error response is a flat JSON map of field name to error message (not a stack trace)

If all checks pass, you've successfully replaced ~20 lines of manual validation with 5 annotations. That's the power of Spring Validation.
