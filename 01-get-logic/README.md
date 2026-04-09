# Lab 01 - GET Logic (Query Params, Enums, and Error Handling)

## What you'll build

A single REST endpoint that returns interest rate information for different bank account types. The endpoint accepts optional query parameters and returns either a JSON success response or a JSON error response.

| Method | URL                                        | Description                              |
|--------|--------------------------------------------|------------------------------------------|
| GET    | `/api/rates`                               | Returns rates with default params        |
| GET    | `/api/rates?accountType=savings`           | Returns rates for a specific account     |
| GET    | `/api/rates?accountType=savings&currency=EUR` | Returns rates with both params        |
| GET    | `/api/rates?accountType=invalid`           | Returns 400 error for bad account type   |

---

## Key concepts in this lab

### 1. Enums - fixed choices, nothing else

Think of an enum like a vending machine with exactly three buttons: SAVINGS, CURRENT, and ISA. You can only press one of those three buttons. If someone tries to press a button labeled "PREMIUM", the machine rejects it.

In Java, enums let you define a fixed set of allowed values:

```java
public enum AccountType {
    SAVINGS,
    CURRENT,
    ISA
}
```

You can convert a string to an enum using `valueOf()`:

```java
// This works - "SAVINGS" matches the enum value
AccountType type = AccountType.valueOf("SAVINGS");

// This throws IllegalArgumentException - "PREMIUM" is not a valid button
AccountType type = AccountType.valueOf("PREMIUM");
```

The `valueOf()` method is case-sensitive, so always convert user input to uppercase first:

```java
String userInput = "savings";
AccountType type = AccountType.valueOf(userInput.toUpperCase()); // Works!
```

### 2. ResponseEntity - controlling what you send back

Without `ResponseEntity`, every response your endpoint returns will automatically get a 200 OK status code. That is a problem because sometimes you need to tell the client that something went wrong.

Think of it like sending a letter. Without `ResponseEntity`, you can only put a letter in the envelope. With `ResponseEntity`, you can choose the envelope colour too (green for success, red for error) AND put a letter inside.

```java
// Without ResponseEntity - always returns 200, even for errors
@GetMapping("/api/example")
public RateResponse getRate() {
    return new RateResponse(...); // Always 200 OK
}

// With ResponseEntity - you control the status code
@GetMapping("/api/example")
public ResponseEntity<?> getRate() {
    if (somethingWentWrong) {
        return ResponseEntity.badRequest().body(errorObject);  // 400 Bad Request
    }
    return ResponseEntity.ok(successObject);                   // 200 OK
}
```

The `<?>` means the response body can be any type. This is useful when your endpoint might return either a `RateResponse` (on success) or an `ErrorResponse` (on error).

Common status codes you will use:

| Code | Method                          | When to use                        |
|------|---------------------------------|------------------------------------|
| 200  | `ResponseEntity.ok(body)`       | Everything worked                  |
| 400  | `ResponseEntity.badRequest().body(body)` | Client sent bad input       |
| 404  | `ResponseEntity.notFound().build()` | Resource not found             |

---

## Project structure

```
01-get-logic/
  pom.xml
  lab-01-get-logic.rest
  src/
    main/
      resources/
        application.properties
      java/
        com/training/banking/
          Lab01GetLogicApplication.java   <-- Your work goes here
          AccountType.java                <-- Pre-built enum
          RateResponse.java               <-- Pre-built DTO
          ErrorResponse.java              <-- Pre-built DTO
  solution/
    Lab01GetLogicApplication.java         <-- Fully working solution
```

---

## How to run

1. Open a terminal and navigate to the `01-get-logic` folder.

2. Run the application with Maven:

```bash
./mvnw spring-boot:run
```

Or if you don't have the Maven wrapper, use:

```bash
mvn spring-boot:run
```

3. The application starts on **port 8081**. You should see output like:

```
Tomcat started on port 8081
Started Lab01GetLogicApplication in X seconds
```

4. Test your endpoint using the `.rest` file in your IDE, or with curl:

```bash
curl http://localhost:8081/api/rates?accountType=savings&currency=GBP
```

---

## Your tasks

Open `Lab01GetLogicApplication.java` and complete the five TODOs.

### TODO 1 - Parse the accountType string into an enum

The `accountType` query parameter arrives as a plain `String`. You need to convert it into an `AccountType` enum value.

- If the string is `null` or blank, default to `AccountType.CURRENT`
- Otherwise, convert it to uppercase and use `AccountType.valueOf()` to parse it
- Wrap the `valueOf()` call in a `try-catch` block because it throws `IllegalArgumentException` for invalid values

```java
// Hint: structure your code like this
AccountType type;
if (accountType == null || accountType.isBlank()) {
    type = // ... the default
} else {
    try {
        type = // ... parse the string
    } catch (IllegalArgumentException e) {
        // handle the error in TODO 2
    }
}
```

### TODO 2 - Return a 400 error for invalid account types

Inside the `catch` block from TODO 1, return a `ResponseEntity` with:
- Status code: 400 (Bad Request)
- Body: a new `ErrorResponse` with error `"INVALID_ACCOUNT_TYPE"` and message `"Allowed types: SAVINGS, CURRENT, ISA"`

```java
// Hint:
return ResponseEntity.badRequest().body(new ErrorResponse("INVALID_ACCOUNT_TYPE", "Allowed types: SAVINGS, CURRENT, ISA"));
```

### TODO 3 - Get the rate data

Call the `getRateInfo()` helper method with the parsed `AccountType` and `currency` string.

```java
// Hint:
RateResponse response = getRateInfo(type, currency);
```

### TODO 4 - Return a 200 OK response

Return the `RateResponse` wrapped in a `ResponseEntity` with a 200 status code.

```java
// Hint:
return ResponseEntity.ok(response);
```

### TODO 5 - Implement the getRateInfo helper method

Inside the `getRateInfo()` method, use a `switch` statement to return the correct hardcoded data:

| Account Type | Annual Interest Rate | Minimum Balance |
|-------------|---------------------|-----------------|
| SAVINGS     | 1.5                 | 500.00          |
| CURRENT     | 0.1                 | 0.00            |
| ISA         | 2.0                 | 1000.00         |

```java
// Hint: Java 17 switch expression
return switch (type) {
    case SAVINGS -> new RateResponse(type.name(), 1.5, 500.00, currency, timestamp);
    case CURRENT -> new RateResponse(...);
    case ISA -> new RateResponse(...);
};
```

---

## How to test

Use the `lab-01-get-logic.rest` file or curl to test each scenario.

### Expected results

| Test | URL | Expected Status | Expected Body |
|------|-----|-----------------|---------------|
| Default params | `GET /api/rates` | 200 | `accountType: "CURRENT"`, rate `0.1`, balance `0.0`, currency `"GBP"` |
| Savings | `GET /api/rates?accountType=savings` | 200 | `accountType: "SAVINGS"`, rate `1.5`, balance `500.0`, currency `"GBP"` |
| Case insensitive | `GET /api/rates?accountType=SaViNgS&currency=eur` | 200 | `accountType: "SAVINGS"`, currency `"EUR"` |
| Invalid type | `GET /api/rates?accountType=premium` | 400 | `error: "INVALID_ACCOUNT_TYPE"` |
| ISA with USD | `GET /api/rates?accountType=isa&currency=USD` | 200 | `accountType: "ISA"`, rate `2.0`, balance `1000.0`, currency `"USD"` |
| Blank accountType | `GET /api/rates?accountType=` | 200 | `accountType: "CURRENT"` (defaults) |
| ISA explicit | `GET /api/rates?accountType=ISA` | 200 | `accountType: "ISA"`, rate `2.0` |
| All params | `GET /api/rates?accountType=current&currency=EUR` | 200 | `accountType: "CURRENT"`, currency `"EUR"` |

---

## Done?

In this lab you learned:

- **Enums** - how to define a fixed set of allowed values and convert strings to enum values safely
- **@RequestParam** - how to read query parameters from the URL, with optional defaults
- **ResponseEntity** - how to control both the HTTP status code and the response body
- **Error handling** - how to catch invalid input and return a proper error response instead of crashing
- **DTOs** - how Java objects get automatically converted to JSON by Spring Boot

Next up: **Lab 02 - POST Validation**, where you will accept JSON request bodies and validate incoming data.
