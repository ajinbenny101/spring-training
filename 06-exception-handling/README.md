# Lab 06 - Exception Handling

## What you'll build

A banking API where errors are handled cleanly using **custom exceptions** and a **global exception handler** instead of scattering if/else error checks throughout the controller.

You will create three custom exception classes and a single global handler that catches them all and returns consistent error responses.

**Endpoints:**
- `POST /api/accounts` - create a new account (throws `DuplicateAccountException` if duplicate)
- `GET /api/accounts/{accountNumber}` - get an account (throws `AccountNotFoundException` if not found)
- `PATCH /api/accounts/{accountNumber}/deposit` - deposit money into an account
- `PATCH /api/accounts/{accountNumber}/withdraw` - withdraw money (throws `InsufficientFundsException` if balance too low)

---

## Key concepts

### 1. Custom Exceptions

Instead of returning error responses everywhere, you `throw` a specific exception. Like a bank teller saying "I can't do this" and passing the problem to the complaints department.

```java
// BEFORE (Lab 02 style - messy, repeated everywhere)
if (account == null) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse("NOT_FOUND", "Account not found"));
}

// AFTER (Lab 06 style - clean, one line)
throw new AccountNotFoundException("12345678");
```

### 2. @RestControllerAdvice - the global error handler

Think of it as a bank's complaints department. Instead of every teller handling complaints differently, there is ONE central place that handles all problems consistently. Any exception thrown anywhere in the app gets caught here.

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    // all exception handlers live here
}
```

### 3. @ExceptionHandler - one desk per problem type

Marks a method as "this handles a specific type of exception." Like different desks in the complaints department: one for missing accounts, one for insufficient funds, etc.

```java
@ExceptionHandler(AccountNotFoundException.class)
public ResponseEntity<ErrorResponse> handleNotFound(AccountNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse("NOT_FOUND", ex.getMessage()));
}
```

### 4. throw vs return

In earlier labs you did:
```java
return ResponseEntity.badRequest().body(error);
```

Now you do:
```java
throw new AccountNotFoundException("12345678");
```
...and let the global handler deal with it. The controller stays clean.

---

## Project structure

```
06-exception-handling/
  pom.xml
  lab-06-exception-handling.rest          <-- test file for VS Code REST Client
  src/main/resources/
    application.properties                <-- server.port=8086
  src/main/java/com/training/banking/
    Lab06ExceptionHandlingApplication.java    [PRE-BUILT]
    model/
      Account.java                            [PRE-BUILT]
    dto/
      CreateAccountRequest.java               [PRE-BUILT]
      AmountRequest.java                      [PRE-BUILT]
      ErrorResponse.java                      [PRE-BUILT]
    exception/
      AccountNotFoundException.java           <-- TODO 1
      DuplicateAccountException.java          <-- TODO 2
      InsufficientFundsException.java         <-- TODO 3
      GlobalExceptionHandler.java             <-- TODOs 4-7
    service/
      AccountService.java                     [PRE-BUILT]
    controller/
      AccountController.java                  [PRE-BUILT]
  solution/exception/
    AccountNotFoundException.java
    DuplicateAccountException.java
    InsufficientFundsException.java
    GlobalExceptionHandler.java
```

---

## How to run

```bash
cd 06-exception-handling
./mvnw spring-boot:run
```

Or if you don't have the Maven wrapper yet:

```bash
mvn spring-boot:run
```

The app starts on **http://localhost:8086**.

> The app will NOT compile until you complete TODOs 1-3, because the service and controller reference these exception classes.

---

## Your tasks

### TODO 1 - AccountNotFoundException

**File:** `src/main/java/com/training/banking/exception/AccountNotFoundException.java`

Make this class extend `RuntimeException`. Add a constructor that takes a `String accountNumber` and calls `super()` with the message `"Account {accountNumber} not found"`.

### TODO 2 - DuplicateAccountException

**File:** `src/main/java/com/training/banking/exception/DuplicateAccountException.java`

Same pattern as TODO 1. Message: `"Account {accountNumber} already exists"`.

### TODO 3 - InsufficientFundsException

**File:** `src/main/java/com/training/banking/exception/InsufficientFundsException.java`

Same pattern, but the constructor takes two parameters: `String accountNumber` and `double amount`. Message: `"Account {accountNumber} has insufficient funds for withdrawal of {amount}"`.

### TODO 4 - Add @RestControllerAdvice

**File:** `src/main/java/com/training/banking/exception/GlobalExceptionHandler.java`

Add the `@RestControllerAdvice` annotation to the class. This tells Spring that this class is the global exception handler for all controllers.

### TODO 5 - Handle AccountNotFoundException

**File:** `src/main/java/com/training/banking/exception/GlobalExceptionHandler.java`

Add a method annotated with `@ExceptionHandler(AccountNotFoundException.class)` that:
- Takes the exception as a parameter
- Creates an `ErrorResponse` with error `"NOT_FOUND"` and the exception's message
- Returns `ResponseEntity` with status `404`

### TODO 6 - Handle DuplicateAccountException

Same file. Same pattern as TODO 5, but:
- Error: `"CONFLICT"`
- Status: `409`

### TODO 7 - Handle InsufficientFundsException

Same file. Same pattern as TODO 5, but:
- Error: `"BAD_REQUEST"`
- Status: `400`

---

## How to test

Open `lab-06-exception-handling.rest` in VS Code with the REST Client extension, or use curl:

**1. Create an account (expect 201):**
```bash
curl -s -w "\nHTTP Status: %{http_code}\n" -X POST http://localhost:8086/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":"12345678","holderName":"Asha Patel","openingBalance":1000}'
```

**2. Create the same account again (expect 409):**
```bash
curl -s -w "\nHTTP Status: %{http_code}\n" -X POST http://localhost:8086/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":"12345678","holderName":"Someone Else","openingBalance":500}'
```

**3. Get existing account (expect 200):**
```bash
curl -s -w "\nHTTP Status: %{http_code}\n" http://localhost:8086/api/accounts/12345678
```

**4. Get missing account (expect 404):**
```bash
curl -s -w "\nHTTP Status: %{http_code}\n" http://localhost:8086/api/accounts/99999999
```

**5. Deposit money (expect 200):**
```bash
curl -s -w "\nHTTP Status: %{http_code}\n" -X PATCH http://localhost:8086/api/accounts/12345678/deposit \
  -H "Content-Type: application/json" \
  -d '{"amount":250}'
```

**6. Withdraw - success (expect 200):**
```bash
curl -s -w "\nHTTP Status: %{http_code}\n" -X PATCH http://localhost:8086/api/accounts/12345678/withdraw \
  -H "Content-Type: application/json" \
  -d '{"amount":200}'
```

**7. Withdraw too much (expect 400):**
```bash
curl -s -w "\nHTTP Status: %{http_code}\n" -X PATCH http://localhost:8086/api/accounts/12345678/withdraw \
  -H "Content-Type: application/json" \
  -d '{"amount":99999}'
```

### Expected error responses

**404 - Account not found:**
```json
{
  "error": "NOT_FOUND",
  "message": "Account 99999999 not found",
  "timestamp": "2026-04-07T10:30:00.000"
}
```

**409 - Duplicate account:**
```json
{
  "error": "CONFLICT",
  "message": "Account 12345678 already exists",
  "timestamp": "2026-04-07T10:30:00.000"
}
```

**400 - Insufficient funds:**
```json
{
  "error": "BAD_REQUEST",
  "message": "Account 12345678 has insufficient funds for withdrawal of 99999.0",
  "timestamp": "2026-04-07T10:30:00.000"
}
```

---

## Done?

Check these off:

- [ ] App compiles and starts on port 8086
- [ ] `POST /api/accounts` returns 201 for a valid new account
- [ ] `POST /api/accounts` returns 409 with an `ErrorResponse` when the account number already exists
- [ ] `GET /api/accounts/{accountNumber}` returns 200 for an existing account
- [ ] `GET /api/accounts/{accountNumber}` returns 404 with an `ErrorResponse` when the account does not exist
- [ ] `PATCH /api/accounts/{accountNumber}/deposit` returns 200 with the updated balance
- [ ] `PATCH /api/accounts/{accountNumber}/withdraw` returns 200 when balance is sufficient
- [ ] `PATCH /api/accounts/{accountNumber}/withdraw` returns 400 with an `ErrorResponse` when balance is too low
- [ ] All error responses include `error`, `message`, and `timestamp` fields
- [ ] The controller has ZERO error-handling code - it is all in `GlobalExceptionHandler`

**Bonus thought:** Look at `AccountController.java`. Compare it to Lab 02's controller. Notice how much cleaner it is? That is the whole point of this pattern. The controller just does its job (handling HTTP requests) and lets the exception handler worry about errors.
