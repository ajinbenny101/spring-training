# Lab 08 - JPA Relationships

## What you'll build

A banking API where customers can have multiple accounts, and each account can have multiple transactions. You'll connect these entities using JPA relationship annotations so the database knows how they relate to each other.

Think of it like a real bank's internal system. A customer walks in and opens two accounts - a savings account and a current account. Then they make deposits and withdrawals on each account. The bank needs to track which accounts belong to which customer, and which transactions belong to which account. That's exactly what JPA relationships do - they wire up these connections so you can ask questions like "show me all of Asha's accounts" or "show me every transaction on account 12345678."

---

## Key concepts

### @OneToMany - "One customer has many accounts"

This annotation goes on the Customer's list of accounts. One customer at a bank can hold multiple accounts (savings, current, ISA). In the database, this means the accounts table has a foreign key column pointing back to the customer.

```java
@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
private List<Account> accounts = new ArrayList<>();
```

The `mappedBy = "customer"` part says: "The Account entity has a field called `customer` - that's the field that owns this relationship."

### @ManyToOne - "Many accounts belong to one customer"

This is the reverse side. You put it on the Account's `customer` field. Many accounts can point to the same customer.

```java
@ManyToOne
@JoinColumn(name = "customer_id")
private Customer customer;
```

### @JoinColumn - "Which column links the two tables?"

This tells JPA which column in the database holds the foreign key. Think of it like a label on a filing folder that says "belongs to customer #5." The `name = "customer_id"` means the accounts table will have a column called `customer_id` that stores the ID of the customer who owns that account.

### cascade = CascadeType.ALL - "Save everything together"

When you save a customer, automatically save their accounts too. Like saving a folder and everything inside it. Without cascade, you'd have to save the customer first, then save each account separately.

### @JsonManagedReference and @JsonBackReference - "Stop infinite loops"

Without these annotations, converting to JSON would cause an infinite loop: Customer -> Accounts -> Customer -> Accounts -> Customer... forever. The application would crash or hang.

- `@JsonManagedReference` says "include this side in the JSON output" (put it on the parent's list)
- `@JsonBackReference` says "skip this side in the JSON output" (put it on the child's reference back to parent)

When you fetch a Customer, you'll see their accounts in the JSON. But when you look at an Account's JSON, the `customer` field won't appear (which prevents the loop).

---

## Project structure

```
08-jpa-relationships/
  pom.xml
  lab-08-jpa-relationships.rest          <-- test all 5 endpoints
  src/main/resources/
    application.properties               <-- server.port=8088
  src/main/java/com/training/banking/
    Lab08JpaRelationshipsApplication.java   <-- pre-built (just the main class)
    entity/
      Customer.java                      <-- YOUR WORK (TODOs 1-3)
      Account.java                       <-- YOUR WORK (TODOs 4-6)
      Transaction.java                   <-- YOUR WORK (TODOs 7-8)
    repository/
      CustomerRepository.java            <-- pre-built
      AccountRepository.java             <-- pre-built
      TransactionRepository.java         <-- pre-built
    controller/
      BankingController.java             <-- pre-built (all 5 endpoints)
    dto/
      CreateCustomerRequest.java         <-- pre-built
      CreateAccountRequest.java          <-- pre-built
      CreateTransactionRequest.java      <-- pre-built
  solution/
    entity/
      Customer.java                      <-- complete working solution
      Account.java                       <-- complete working solution
      Transaction.java                   <-- complete working solution
```

---

## How to run

```bash
cd 08-jpa-relationships
./mvnw spring-boot:run
```

Or if you have Maven installed:

```bash
cd 08-jpa-relationships
mvn spring-boot:run
```

The server starts on **http://localhost:8088**

---

## Your tasks

Open the three entity files and complete the 8 TODOs.

### TODO 1 - Mark Customer as an entity (`Customer.java`)
- Add the `@Entity` annotation to the Customer class
- This tells JPA to create a database table for customers

### TODO 2 - Add primary key to Customer (`Customer.java`)
- Add `@Id` and `@GeneratedValue(strategy = GenerationType.IDENTITY)` to the `id` field
- This makes `id` the primary key and auto-generates it

### TODO 3 - Add one-to-many relationship to Customer (`Customer.java`)
- Add `@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)` to the `accounts` field
- Add `@JsonManagedReference` to the `accounts` field
- `mappedBy` points to the `customer` field in the Account entity
- `cascade = ALL` means saving a Customer will also save its Accounts
- `@JsonManagedReference` tells Jackson to include this side when converting to JSON

### TODO 4 - Mark Account as an entity with a primary key (`Account.java`)
- Add `@Entity` to the class
- Add `@Id` and `@GeneratedValue(strategy = GenerationType.IDENTITY)` to the `id` field

### TODO 5 - Add many-to-one relationship to Account (`Account.java`)
- Add `@ManyToOne` to the `customer` field
- Add `@JoinColumn(name = "customer_id")` to the `customer` field
- Add `@JsonBackReference` to the `customer` field
- This creates a `customer_id` column in the accounts table
- `@JsonBackReference` tells Jackson to skip this side (prevents infinite loop)

### TODO 6 - Add one-to-many relationship for transactions (`Account.java`)
- Add `@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)` to the `transactions` field
- Add `@JsonManagedReference` to the `transactions` field
- Same pattern as Customer -> Accounts, but now Account -> Transactions

### TODO 7 - Mark Transaction as an entity with a primary key (`Transaction.java`)
- Add `@Entity` to the class
- Add `@Id` and `@GeneratedValue(strategy = GenerationType.IDENTITY)` to the `id` field

### TODO 8 - Add many-to-one relationship to Transaction (`Transaction.java`)
- Add `@ManyToOne` to the `account` field
- Add `@JoinColumn(name = "account_id")` to the `account` field
- Add `@JsonBackReference` to the `account` field
- Same pattern as Account -> Customer, but now Transaction -> Account

---

## How to test

Use the `lab-08-jpa-relationships.rest` file in IntelliJ or VS Code (with the REST Client extension).

### Test sequence:

1. **Create a customer** (POST /api/customers) - should get 200 with the customer JSON
2. **Create an account** for that customer (POST /api/customers/1/accounts) - should get 200 with the account JSON
3. **Create a transaction** on that account (POST /api/accounts/1/transactions) - should get 200 with the transaction JSON (balance should update)
4. **Create another transaction** (deposit or withdrawal) - balance should change accordingly
5. **Get the customer** (GET /api/customers/1) - should see the customer with their accounts nested inside, and each account with its transactions nested inside
6. **Get transactions for an account** (GET /api/accounts/1/transactions) - should see all transactions for that account

### What to look for:

- When you GET a customer, the JSON should include their accounts as a nested array
- Each account should include its transactions as a nested array
- No infinite loop errors - if you see a stack overflow or the response never finishes, check your @JsonManagedReference and @JsonBackReference annotations
- Deposits should add to the balance, withdrawals should subtract from it

---

## Done?

Check your work:

- [ ] POST /api/customers creates a customer and returns it as JSON
- [ ] POST /api/customers/1/accounts creates an account linked to customer 1
- [ ] POST /api/accounts/1/transactions creates a transaction and updates the account balance
- [ ] GET /api/customers/1 returns the customer with accounts and transactions nested inside
- [ ] GET /api/accounts/1/transactions returns all transactions for account 1
- [ ] No infinite loop errors in the JSON responses
- [ ] Deposits increase the balance, withdrawals decrease it

If everything works, you've just built a relational data model with JPA. This is how real banking systems store data - customers own accounts, accounts own transactions, and JPA manages all the foreign keys for you.
