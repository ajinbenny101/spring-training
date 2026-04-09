# Lab 02 - POST + Manual Validation

## What you'll build

A simple banking API with two endpoints:

1. **POST /api/accounts** - create a new bank account (with validation)
2. **GET /api/accounts/{accountNumber}** - look up an account by its number

You will validate incoming data by hand using if/else checks - no frameworks, no magic. Think of it like being the bank clerk who checks every form before opening an account. Is the name filled in? Is the account number the right length? Is the sort code in the right format?

By the end of this lab you will know how to receive JSON data from a client, validate it, store it in memory, and return appropriate HTTP status codes.

---

## Key concepts

### 1. @PostMapping - receiving data from the client

When a customer fills in a form at a bank branch and hands it to the clerk, that is a POST request. The customer is sending data to the bank for processing.

```java
@PostMapping
public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest request) {
    // The request object contains the data the client sent
}
```

`@PostMapping` tells Spring: "When someone sends a POST request to this URL, call this method."

### 2. @RequestBody - automatic JSON to Java conversion

When a client sends JSON in the request body, Spring reads it and fills in a Java object automatically. You do not need to parse JSON yourself.

```
Client sends:  { "holderName": "Asha Patel", "accountNumber": "12345678" }
Spring creates: a CreateAccountRequest object with those values already set
```

It is like having an assistant who reads the paper form and types everything into the computer for you. By the time you look at it, the data is already in a nice Java object.

### 3. @PathVariable - reading values from the URL

Sometimes data lives in the URL itself rather than in the body. For example, in `/api/accounts/12345678`, the account number is part of the URL path.

```java
@GetMapping("/{accountNumber}")
public ResponseEntity<?> getAccount(@PathVariable String accountNumber) {
    // accountNumber = "12345678"
}
```

The curly braces `{accountNumber}` act as a placeholder. Spring pulls the actual value from the URL and passes it to your method parameter. Think of it like a filing system where the folder label is the account number - you read the label to know which folder to open.

### 4. ResponseEntity - controlling what you send back

A plain `return account;` always sends back 200 OK. But sometimes you need to say "201 Created" or "400 Bad Request" or "404 Not Found". `ResponseEntity` gives you full control.

```java
// 201 - successfully created
return ResponseEntity.status(HttpStatus.CREATED).body(successData);

// 400 - client sent bad data
return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorData);

// 404 - thing not found
return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorData);
```

Think of it like an envelope. The status code is written on the outside (so the client knows immediately if it was good or bad news), and the body is the letter inside with the details.

### 5. Manual validation - checking fields yourself

Before processing any request, you should check the data is valid. In this lab, you write those checks yourself with simple if/else statements:

```java
if (isBlank(request.getHolderName())) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse("BAD_REQUEST", "Holder name is required"));
}
```

This is intentionally manual. In later labs you will learn annotation-based validation that does this automatically, but understanding the manual approach first helps you appreciate what those annotations do behind the scenes.

---

## Project structure

```
02-post-validation/
  pom.xml
  lab-02-post-validation.rest          <-- test requests (run these in IntelliJ/VS Code)
  src/main/resources/
    application.properties             <-- server.port=8082
  src/main/java/com/training/banking/
    Lab02PostValidationApplication.java  <-- YOUR WORK GOES HERE (TODOs 1-9)
    CreateAccountRequest.java            <-- pre-built DTO (no changes needed)
    Account.java                         <-- pre-built model (no changes needed)
    SuccessResponse.java                 <-- pre-built response (no changes needed)
    ErrorResponse.java                   <-- pre-built response (no changes needed)
  solution/
    Lab02PostValidationApplication.java  <-- fully working solution (peek if stuck)
```

---

## How to run

```bash
# From the 02-post-validation directory:
./mvnw spring-boot:run

# Or if you have Maven installed:
mvn spring-boot:run
```

The app starts on **http://localhost:8082**.

---

## Your tasks

Open `Lab02PostValidationApplication.java` and complete the numbered TODOs.

**Start with the helper methods at the bottom (they are used by the validation above):**

| TODO | What to do | Hint |
|------|-----------|------|
| **TODO 7** | Implement `isBlank(String)` | `value == null \|\| value.trim().isEmpty()` |
| **TODO 8** | Implement `isValidAccountNumber(String)` | Check length is 8 and all digits: `matches("\\d{8}")` |
| **TODO 9** | Implement `isValidSortCode(String)` | Check format XX-XX-XX: `matches("\\d{2}-\\d{2}-\\d{2}")` |

**Then work through the POST endpoint from top to bottom:**

| TODO | What to do | Status code |
|------|-----------|-------------|
| **TODO 1** | Validate holderName, accountNumber, sortCode, openingBalance | 400 Bad Request |
| **TODO 2** | Check for duplicate account number (loop through array) | 409 Conflict |
| **TODO 3** | Check if the array is full | 409 Conflict |
| **TODO 4** | Create an Account object and store it in the array | - |
| **TODO 5** | Return 201 Created with a SuccessResponse | 201 Created |

**Finally, the GET endpoint:**

| TODO | What to do | Status code |
|------|-----------|-------------|
| **TODO 6** | Loop through accounts, return match or 404 | 200 OK or 404 Not Found |

---

## How to test

Open `lab-02-post-validation.rest` in IntelliJ or VS Code (with the REST Client extension) and run each request. You can also use curl:

```bash
# Create an account (expect 201)
curl -X POST http://localhost:8082/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":"12345678","sortCode":"12-34-56","holderName":"Asha Patel","openingBalance":1000.00}'

# Get the account (expect 200)
curl http://localhost:8082/api/accounts/12345678

# Get a missing account (expect 404)
curl http://localhost:8082/api/accounts/99999999

# Missing holder name (expect 400)
curl -X POST http://localhost:8082/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":"22222222","sortCode":"11-22-33","holderName":"","openingBalance":100.00}'

# Bad account number (expect 400)
curl -X POST http://localhost:8082/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":"123","sortCode":"11-22-33","holderName":"Bad Account","openingBalance":100.00}'

# Bad sort code (expect 400)
curl -X POST http://localhost:8082/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":"33333333","sortCode":"1234-56","holderName":"Bad Sort Code","openingBalance":100.00}'

# Negative balance (expect 400)
curl -X POST http://localhost:8082/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":"44444444","sortCode":"11-22-33","holderName":"Negative","openingBalance":-50.00}'

# Duplicate account (run the first curl again, expect 409)
curl -X POST http://localhost:8082/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":"12345678","sortCode":"12-34-56","holderName":"Duplicate","openingBalance":500.00}'
```

### Expected responses

**201 Created (successful account creation):**
```json
{
  "message": "Account created successfully",
  "account": {
    "accountNumber": "12345678",
    "sortCode": "12-34-56",
    "holderName": "Asha Patel",
    "balance": 1000.0
  }
}
```

**400 Bad Request (validation failure):**
```json
{
  "error": "BAD_REQUEST",
  "message": "Account number must be exactly 8 digits"
}
```

**404 Not Found:**
```json
{
  "error": "NOT_FOUND",
  "message": "Account 99999999 not found"
}
```

**409 Conflict (duplicate):**
```json
{
  "error": "DUPLICATE_ACCOUNT",
  "message": "Account 12345678 already exists"
}
```

---

## Done?

Check your work against these criteria:

- [ ] POST with valid data returns 201 and the account details
- [ ] POST with blank holder name returns 400
- [ ] POST with a short or non-numeric account number returns 400
- [ ] POST with a malformed sort code returns 400
- [ ] POST with negative balance returns 400
- [ ] POST with a duplicate account number returns 409
- [ ] GET with an existing account number returns 200 and the account
- [ ] GET with a non-existent account number returns 404
- [ ] All error responses include both "error" and "message" fields

If all tests pass, you are ready for Lab 03.
