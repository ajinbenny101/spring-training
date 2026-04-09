# Lab 07 - JPA + H2 Database

## What you'll build

A bank account API that stores data in a real database instead of an ArrayList. You'll create accounts, list them, look them up by ID, and delete them - and everything will be saved in a database table.

The big difference from previous labs: you define a Java class and an interface, and Spring generates ALL the database code for you. No SQL, no table creation, no manual wiring. You write maybe 5 lines of actual code and get a fully working database layer.

Think of it like hiring a filing clerk. You describe what the filing cabinet looks like (the entity) and hand them a job description (the repository interface). Spring reads that job description and creates a fully trained clerk who knows how to file, find, and remove records - without you teaching them any of it.

---

## Key concepts

### What is JPA?

Java Persistence API. It's a standard way to save Java objects to a database without writing SQL. Think of it as a translator - you speak Java, the database speaks SQL, and JPA translates between them.

Without JPA, you'd write raw SQL strings like `INSERT INTO account (holder_name, balance) VALUES ('Priya', 1000)`. With JPA, you just call `repository.save(account)` and it handles the SQL for you.

### What is H2?

A lightweight database that runs inside your application. No installation needed, no Docker, no setup. It's like a notepad that lives inside your app - great for development and learning. Data is lost when you restart (it's in-memory).

In a real banking app, you'd use PostgreSQL or Oracle. But for learning JPA, H2 is perfect because there's zero setup.

### @Entity - "This class is a database table"

Tells JPA "this class maps to a database table." Each field becomes a column. Each object becomes a row.

```java
@Entity
public class Account {
    private Long id;
    private String holderName;
    private double balance;
}
```

This creates a table called `ACCOUNT` with columns `ID`, `HOLDER_NAME`, and `BALANCE`. You never write a `CREATE TABLE` statement - JPA does it for you.

### @Id and @GeneratedValue - "Auto-generate the primary key"

Marks which field is the primary key and tells the database to auto-generate it (like an auto-incrementing ID).

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

When you save a new account, the database automatically assigns it an ID: 1, 2, 3, and so on. You never set the ID yourself.

### JpaRepository - "Give me free CRUD methods"

An interface you extend (not implement!). Spring automatically provides methods like `save()`, `findAll()`, `findById()`, `deleteById()`. You write ZERO implementation code.

```java
public interface AccountRepository extends JpaRepository<Account, Long> {
    // That's it. No methods to write. Spring provides everything.
}
```

Think of it as hiring a database clerk who already knows how to file, find, and remove records. You just tell them what type of records they'll be working with (`Account`) and what the key looks like (`Long`).

### H2 Console - "Peek inside the database"

A web page where you can see your database tables and run SQL queries. Like peeking inside the filing cabinet.

After starting the app, visit **http://localhost:8087/h2-console**. Set the JDBC URL to `jdbc:h2:mem:bankingdb` and click Connect. You can see your ACCOUNT table and run SQL!

---

## Project structure

```
07-jpa-h2/
  pom.xml
  lab-07-jpa-h2.rest                          <-- test requests for all endpoints
  src/main/resources/
    application.properties                    <-- port, H2 config, JPA settings
  src/main/java/com/training/banking/
    Lab07JpaH2Application.java                <-- pre-built main class
    entity/
      Account.java                            <-- YOUR WORK (TODO 1-2)
    repository/
      AccountRepository.java                  <-- YOUR WORK (TODO 3)
    dto/
      CreateAccountRequest.java               <-- pre-built
      ErrorResponse.java                      <-- pre-built
    service/
      AccountService.java                     <-- YOUR WORK (TODO 4-8)
    controller/
      AccountController.java                  <-- pre-built (uses service)
  solution/
    entity/Account.java                       <-- complete working entity
    repository/AccountRepository.java         <-- complete working repository
    service/AccountService.java               <-- complete working service
```

---

## How to run

```bash
cd 07-jpa-h2
./mvnw spring-boot:run
```

Or if you have Maven installed:

```bash
cd 07-jpa-h2
mvn spring-boot:run
```

The server starts on **http://localhost:8087**

After starting the app, visit **http://localhost:8087/h2-console**. Set JDBC URL to `jdbc:h2:mem:bankingdb` and click Connect. You can see your ACCOUNT table and run SQL!

---

## Your tasks

### TODO 1 - Add @Entity annotation (Account.java)

Open `entity/Account.java`. Add the `@Entity` annotation to the class. This tells JPA "this class represents a database table."

Without this annotation, JPA has no idea this class exists. It's like putting a label on a filing cabinet drawer so the clerk knows what goes inside.

### TODO 2 - Add @Id and @GeneratedValue (Account.java)

Mark the `id` field as the primary key using `@Id`, and tell the database to auto-generate it using `@GeneratedValue(strategy = GenerationType.IDENTITY)`.

These two annotations go on the `id` field. `@Id` says "this is the primary key" and `@GeneratedValue` says "the database should assign this automatically."

### TODO 3 - Create AccountRepository interface (AccountRepository.java)

Open `repository/AccountRepository.java`. Create an interface that extends `JpaRepository<Account, Long>`. That's it - one line. No methods to write.

Spring sees this interface at startup and automatically creates a class that implements it, with full support for `save()`, `findAll()`, `findById()`, `deleteById()`, and more.

### TODO 4 - Inject AccountRepository (AccountService.java)

Open `service/AccountService.java`. Add a constructor that accepts an `AccountRepository` and stores it in a field. Spring will automatically pass in the repository when creating the service (this is constructor injection).

### TODO 5 - Implement createAccount (AccountService.java)

Use `repository.save(account)` to save the account to the database. The `save()` method returns the saved entity (with the auto-generated ID filled in).

### TODO 6 - Implement getAllAccounts (AccountService.java)

Use `repository.findAll()` to get every account from the database. Returns a `List<Account>`.

### TODO 7 - Implement getAccountById (AccountService.java)

Use `repository.findById(id)`. This returns an `Optional<Account>` - it might contain an account, or it might be empty. Return the Optional directly and let the controller handle the empty case.

### TODO 8 - Implement deleteAccount (AccountService.java)

Use `repository.deleteById(id)`. This removes the account from the database. If the ID does not exist, it throws an exception - the controller already handles that.

---

## How to test

Use the `lab-07-jpa-h2.rest` file in IntelliJ or VS Code (with the REST Client extension). The file has test requests for every endpoint.

### Test sequence

1. Create an account (POST) - should get 201 with an auto-generated ID
2. Create a second account (POST) - should get 201 with a different ID
3. List all accounts (GET /api/accounts) - should see both accounts
4. Get one account by ID (GET /api/accounts/1) - should see just one
5. Delete an account (DELETE /api/accounts/2) - should get 204 No Content
6. Get the deleted account (GET /api/accounts/2) - should get 404

### Bonus - try the H2 Console

1. Open http://localhost:8087/h2-console in your browser
2. Set JDBC URL to `jdbc:h2:mem:bankingdb`
3. Leave username as `sa` and password blank
4. Click Connect
5. Run `SELECT * FROM ACCOUNT` to see your data
6. Try inserting a row with SQL and then fetching it through the API

---

## Done?

Check your work:

- [ ] `Account.java` has `@Entity` on the class
- [ ] `Account.java` has `@Id` and `@GeneratedValue` on the id field
- [ ] `AccountRepository.java` extends `JpaRepository<Account, Long>`
- [ ] `AccountService.java` injects the repository via constructor
- [ ] POST /api/accounts creates an account and returns 201 with auto-generated ID
- [ ] GET /api/accounts returns all accounts from the database
- [ ] GET /api/accounts/{id} returns one account or 404
- [ ] DELETE /api/accounts/{id} removes the account and returns 204
- [ ] Deleted accounts return 404 when you try to GET them
- [ ] H2 Console shows your ACCOUNT table at http://localhost:8087/h2-console

If everything works, you've just replaced an entire ArrayList-based storage layer with a real database - and you wrote almost no code to do it. That's the power of Spring Data JPA.
