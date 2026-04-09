# Lab 04 - Layered Architecture

## What you'll build

You will take a working banking API (the same account endpoints from Lab 03) and restructure it
into proper layers. The endpoints stay the same - the goal is better code organization.

When you finish, you will have:
- A **Controller** layer that only handles HTTP requests and responses
- A **Service** layer that contains all business logic and data storage
- Spring wiring them together automatically using dependency injection

**Port:** 8084

---

## Key concepts

### 1. Why layers?

Imagine walking into a bank branch.

The **teller** sits at the front desk. Their job is to talk to customers - take requests, hand
back receipts, explain errors. The teller does NOT walk into the vault, does NOT decide whether
to approve a loan, does NOT calculate interest rates.

The **manager** sits in the back office. Their job is to make decisions - approve accounts,
process transactions, enforce rules. The manager does NOT talk to customers directly.

Each person has ONE job. If the bank changes how they approve accounts, only the manager's
process changes - the teller keeps doing the same thing. If the bank redesigns their front
desk, only the teller's workflow changes - the manager is unaffected.

**Same idea in code:**
- **Controller** = the teller. Handles HTTP requests and responses. That is it.
- **Service** = the manager. Contains business logic and data. That is it.

This separation is called **separation of concerns**, and it is one of the most important
ideas in software architecture.

### 2. @Service annotation

When you put `@Service` on a class, you are telling Spring: "This class contains business
logic. Please create an instance of it and manage it for me."

Spring will create exactly ONE instance of the service when the application starts, and keep
it alive for the entire life of the app.

```java
@Service
public class AccountService {
    // Spring creates one of these and manages it
}
```

### 3. @Autowired and Constructor Injection

Instead of creating objects yourself with `new`, you let Spring create them and pass them in
through the constructor. This is called **dependency injection**.

```java
@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
}
```

**Analogy:** Instead of the teller hiring their own manager on their first day, the bank
(Spring) assigns a manager to each teller automatically. The teller just shows up and says
"I need a manager" - and one is already there waiting.

### 4. Why not just use `new`?

You might wonder - why not just write `new AccountService()` in the controller?

Two big problems:

**Problem 1 - Multiple copies of data.** If you write `new AccountService()` in the
controller, each controller instance gets its OWN service with its OWN ArrayList. Create
an account through one path, and it might not show up through another. With Spring managing
it, there is ONE service shared across the entire app, so everyone sees the same data.

**Problem 2 - Tight coupling.** If AccountService changes (say it needs a database connection
in the constructor later), you would have to find and update every place that calls
`new AccountService()`. With dependency injection, Spring handles all of that - your
controller code does not change at all.

---

## Project structure

```
04-layered-architecture/
  src/main/java/com/training/banking/
    Lab04LayeredArchitectureApplication.java   <-- PRE-BUILT (entry point)
    model/
      Account.java                             <-- PRE-BUILT (data model)
    dto/
      CreateAccountRequest.java                <-- PRE-BUILT (request body)
      ErrorResponse.java                       <-- PRE-BUILT (error response)
    controller/
      AccountController.java                   <-- YOUR WORK (TODOs here)
    service/
      AccountService.java                      <-- YOUR WORK (TODOs here)
  src/main/resources/
    application.properties                     <-- PRE-BUILT
  solution/
    controller/AccountController.java          <-- Reference solution
    service/AccountService.java                <-- Reference solution
```

---

## How to run

```bash
cd 04-layered-architecture
./mvnw spring-boot:run
```

Or run `Lab04LayeredArchitectureApplication.java` directly from your IDE.

The app starts on **http://localhost:8084**.

---

## Your tasks

You need to complete two files: `AccountService.java` and `AccountController.java`.

**Start with the service** - the controller depends on it, so build the foundation first.

### AccountService.java (6 TODOs)

| TODO | What to do |
|------|-----------|
| 1 | Add the `@Service` annotation to the class |
| 2 | Create a private `ArrayList<Account>` field to store accounts |
| 3 | Implement `createAccount(CreateAccountRequest)` - validate fields, generate account number, create Account, add to list, return it |
| 4 | Implement `getAllAccounts()` - return the list |
| 5 | Implement `getAccount(String accountNumber)` - loop through list, return matching account or null |
| 6 | Implement `deleteAccount(String accountNumber)` - find and remove the account, return true if found, false if not |

### AccountController.java (6 TODOs)

| TODO | What to do |
|------|-----------|
| 1 | Add `@RestController` and `@RequestMapping("/api/accounts")` annotations to the class |
| 2 | Add an `AccountService` field and a constructor with `@Autowired` |
| 3 | Implement POST `/` - call `accountService.createAccount()`, return 201 |
| 4 | Implement GET `/` - call `accountService.getAllAccounts()`, return 200 |
| 5 | Implement GET `/{accountNumber}` - call `accountService.getAccount()`, return 404 if null |
| 6 | Implement DELETE `/{accountNumber}` - call `accountService.deleteAccount()`, return 404 if not found |

---

## How to test

Use the provided `lab-04-layered-architecture.rest` file in IntelliJ, or use curl:

```bash
# 1. Create an account
curl -X POST http://localhost:8084/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"holderName": "Asha Patel", "sortCode": "12-34-56", "openingBalance": 1500.00}'

# 2. List all accounts
curl http://localhost:8084/api/accounts

# 3. Get one account (use the accountNumber from step 1)
curl http://localhost:8084/api/accounts/ACC-1

# 4. Get a missing account - should return 404
curl http://localhost:8084/api/accounts/ACC-999

# 5. Delete an account
curl -X DELETE http://localhost:8084/api/accounts/ACC-1

# 6. Verify it is gone
curl http://localhost:8084/api/accounts
```

---

## Done?

Check these before moving on:

- [ ] App starts without errors on port 8084
- [ ] POST `/api/accounts` creates an account and returns 201
- [ ] POST with missing fields returns 400 with an error message
- [ ] GET `/api/accounts` returns all created accounts
- [ ] GET `/api/accounts/{accountNumber}` returns one account or 404
- [ ] DELETE `/api/accounts/{accountNumber}` removes the account or returns 404
- [ ] Your controller has ZERO business logic - no validation, no ArrayList, no account number generation
- [ ] Your service has ZERO HTTP logic - no ResponseEntity, no status codes, no `@GetMapping`
- [ ] You used constructor injection with `@Autowired`, not `new AccountService()`

If all boxes are checked, you understand layered architecture. Nice work.
