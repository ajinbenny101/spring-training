# Lab 03 - Full CRUD (Create, Read, Update, Delete)

## What you'll build

A complete bank account management API with all CRUD operations. You'll be able to create accounts, view them, update account details, deposit and withdraw money, and close accounts - all through REST endpoints.

Think of it like building the backend for a simple banking app. Every action a bank teller performs at their window - opening an account, checking a balance, making a deposit - you'll implement as an API endpoint.

---

## Key concepts

### @PutMapping - Replace the entire resource

PUT is like rewriting a form from scratch. When you update an account with PUT, you send ALL the fields you want the account to have. It replaces the whole thing.

Real-world analogy: If you fill out a change-of-address form at the bank, they don't just update one field - they rewrite your entire contact record with the new information you provide.

```java
@PutMapping("/api/accounts/{accountNumber}")
public ResponseEntity<?> updateAccount(@PathVariable String accountNumber,
                                       @RequestBody UpdateAccountRequest request) {
    // Find account and replace its updatable fields
}
```

### @PatchMapping - Update part of a resource

PATCH is like using correction fluid on specific parts of a document. You only change what needs changing - everything else stays the same.

Real-world analogy: Depositing money at the bank only changes your balance. Your name, account number, and account type stay exactly the same.

```java
@PatchMapping("/api/accounts/{accountNumber}/deposit")
public ResponseEntity<?> deposit(@PathVariable String accountNumber,
                                 @RequestBody AmountRequest request) {
    // Find account and modify just the balance
}
```

### @DeleteMapping - Remove a resource

DELETE removes a resource entirely. Once deleted, trying to access it should return a 404.

Real-world analogy: Closing a bank account. The account is gone - if someone tries to look it up afterward, they'll be told it doesn't exist.

```java
@DeleteMapping("/api/accounts/{accountNumber}")
public ResponseEntity<?> deleteAccount(@PathVariable String accountNumber) {
    // Find and remove the account
}
```

### PUT vs PATCH - When to use which?

| Action | HTTP Method | What changes |
|--------|-------------|--------------|
| Replace account details | PUT | Overwrites holderName and accountType |
| Deposit money | PATCH | Only modifies the balance |
| Withdraw money | PATCH | Only modifies the balance |

PUT is like replacing a whole document. PATCH is like using correction fluid on specific parts.

### ArrayList - Dynamic storage

In previous labs, you may have used a fixed-size array. An ArrayList can grow and shrink as needed - perfect for a list of accounts where people keep opening and closing them.

```java
// Fixed array - size decided upfront, can't change
Account[] accounts = new Account[100];

// ArrayList - grows automatically as you add items
ArrayList<Account> accounts = new ArrayList<>();
accounts.add(newAccount);     // list grows
accounts.remove(oldAccount);  // list shrinks
```

---

## Project structure

```
03-full-crud/
  pom.xml
  lab-03-full-crud.rest              <-- test requests for all 7 endpoints
  src/main/resources/
    application.properties           <-- server.port=8083
  src/main/java/com/training/banking/
    Lab03FullCrudApplication.java    <-- YOUR WORK GOES HERE (7 TODOs)
    Account.java                     <-- pre-built model (has getters AND setters)
    CreateAccountRequest.java        <-- pre-built DTO for creating accounts
    UpdateAccountRequest.java        <-- pre-built DTO for PUT updates
    AmountRequest.java               <-- pre-built DTO for deposit/withdraw
    ErrorResponse.java               <-- pre-built error DTO
  solution/
    Lab03FullCrudApplication.java    <-- complete working solution
```

---

## How to run

```bash
cd 03-full-crud
./mvnw spring-boot:run
```

Or if you have Maven installed:

```bash
cd 03-full-crud
mvn spring-boot:run
```

The server starts on **http://localhost:8083**

---

## Your tasks

Open `Lab03FullCrudApplication.java` and complete the 7 TODOs.

### TODO 1 - POST /api/accounts (Create an account)
- Read the `CreateAccountRequest` from the request body
- Create a new `Account` object from the request data
- Add it to the `accounts` ArrayList
- Return the created account with status **201 Created**
- Hint: `ResponseEntity.status(HttpStatus.CREATED).body(account)`

### TODO 2 - GET /api/accounts (List all accounts)
- Return the entire `accounts` list with status **200 OK**
- This is the simplest endpoint - just one line

### TODO 3 - GET /api/accounts/{accountNumber} (Get one account)
- Use the helper method `findAccount()` to look up the account
- If found, return it with **200 OK**
- If not found, return an ErrorResponse with **404 Not Found**

### TODO 4 - PUT /api/accounts/{accountNumber} (Full update)
- Find the account by account number
- If not found, return **404**
- Update the `holderName` and `accountType` from the request body
- Return the updated account with **200 OK**
- Note: PUT does NOT change the balance or account number

### TODO 5 - PATCH /api/accounts/{accountNumber}/deposit (Deposit money)
- Find the account by account number
- If not found, return **404**
- Add the amount to the current balance
- Return the updated account with **200 OK**

### TODO 6 - PATCH /api/accounts/{accountNumber}/withdraw (Withdraw money)
- Find the account by account number
- If not found, return **404**
- Check if the account has enough funds (balance >= amount)
- If not enough funds, return **400 Bad Request** with an error message
- Otherwise, subtract the amount and return the updated account

### TODO 7 - DELETE /api/accounts/{accountNumber} (Close account)
- Find the account by account number
- If not found, return **404**
- Remove it from the list
- Return **200 OK** with a confirmation message (use a Map)

### Helper method - findAccount()
- Already has the signature written for you
- Loop through the `accounts` list and find the one matching the account number
- Return the Account if found, or null if not

---

## How to test

Use the `lab-03-full-crud.rest` file in IntelliJ or VS Code (with the REST Client extension). The file has test requests for every endpoint, including error cases.

### Happy path test sequence:
1. Create an account (POST) - should get 201
2. Create a second account (POST) - should get 201
3. List all accounts (GET) - should see both accounts
4. Get one account (GET) - should see just one
5. Update account details (PUT) - should see new name/type
6. Deposit money (PATCH) - balance should increase
7. Withdraw money (PATCH) - balance should decrease
8. Delete account (DELETE) - should get confirmation

### Error case test sequence:
9. Get a deleted account (GET) - should get 404
10. Withdraw more than balance (PATCH) - should get 400
11. Update a non-existent account (PUT) - should get 404

---

## Done?

Check your work:

- [ ] POST creates an account and returns 201
- [ ] GET /api/accounts returns all accounts as a JSON array
- [ ] GET /api/accounts/{accountNumber} returns one account or 404
- [ ] PUT updates holderName and accountType, returns 200 or 404
- [ ] PATCH deposit adds to balance, returns updated account
- [ ] PATCH withdraw subtracts from balance, returns 400 if insufficient funds
- [ ] DELETE removes the account, returns 200 or 404
- [ ] All error responses use the ErrorResponse format

If everything works, you've just built a full CRUD API. This is the pattern behind almost every REST service in the real world - the same create/read/update/delete operations, just with different data.
