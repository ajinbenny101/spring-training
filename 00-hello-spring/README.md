# Lab 00 - Hello Spring Boot

## What you'll build

A tiny banking API with two endpoints:

| Method | URL              | What it does                      |
|--------|------------------|-----------------------------------|
| GET    | `/api/health`    | Returns a status message (JSON)   |
| GET    | `/api/welcome`   | Greets a customer by name (JSON)  |

That's it. The goal is to **get a Spring Boot app running** and see JSON come back in your browser or REST client.

---

## Before you start - What is Spring Boot?

When you write a normal Java program, you write a `main()` method and run it. But building a web server from scratch in Java - listening for HTTP requests, parsing URLs, converting objects to JSON - would take hundreds of lines of boilerplate.

**Spring Boot takes care of all of that.** You just write your business logic, add a few annotations (labels), and Spring Boot handles the rest: starting the server, listening on a port, routing requests, converting your Java objects to JSON.

Think of it like this:

> You're opening a restaurant. Without Spring Boot, you'd have to build the kitchen, install plumbing, wire the electricity, and buy furniture - before you even cook a single dish.
> With Spring Boot, you walk into a fully equipped restaurant. You just write the menu and start cooking.

---

## Key concepts in this lab

### 1. `@SpringBootApplication`

This is an **annotation** (a label you put on a class). It tells Spring:
_"This is the starting point. Set everything up and start the server."_

Think of it like the power button on a machine - one press and the entire system comes alive: the web server starts, routes are registered, and everything is ready to receive requests.

```java
@SpringBootApplication
public class MyApp {
    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);  // this starts everything
    }
}
```

### 2. `@RestController`

This annotation tells Spring:
_"This class handles HTTP requests. Whatever I return from its methods, convert it to JSON and send it back."_

Without this, Spring wouldn't know that your class is meant to handle web requests. It's like putting an "OPEN" sign on your shop - without it, customers (HTTP requests) don't know to come in.

### 3. `@GetMapping("/some-path")`

This maps a method to a **GET request** at a specific URL path.

```java
@GetMapping("/api/health")
public Map<String, String> health() {
    return Map.of("status", "UP");
}
```

When someone visits `http://localhost:8080/api/health`, Spring:
1. Sees that this method is mapped to `/api/health`
2. Calls the method
3. Takes the returned `Map` and converts it to JSON: `{"status": "UP"}`
4. Sends it back as the HTTP response

You don't write any JSON yourself - you just return a Java object and Spring handles the conversion.

### 4. `@RequestParam`

This reads **query parameters** from the URL. Query parameters are the `?key=value` pairs at the end of a URL.

For example, in the URL `http://localhost:8080/api/welcome?name=Asha`:
- The path is `/api/welcome`
- The query parameter is `name` with value `Asha`

```java
@GetMapping("/api/welcome")
public Map<String, String> welcome(@RequestParam String name) {
    return Map.of("message", "Hello, " + name + "!");
}
```

You can make it **optional** with a default value:
```java
@RequestParam(required = false, defaultValue = "Customer") String name
```

This means: _"If `name` is in the URL, use it. If not, use `Customer` instead."_

### 5. `Map.of(...)` - Quick key-value pairs

`Map.of("key1", "value1", "key2", "value2")` creates a simple key-value structure. Spring converts it directly to JSON:

```java
Map.of("status", "UP", "service", "banking-api")
// becomes: {"status": "UP", "service": "banking-api"}
```

---

## Project structure

```
00-hello-spring/
├── pom.xml                          <-- dependencies and build config (explained below)
├── README.md                        <-- you're reading this
├── lab-00-hello-spring.rest         <-- test requests (click to run in your editor)
└── src/
    └── main/
        └── java/
            └── com/training/banking/
                └── Lab00HelloSpringApplication.java   <-- your code goes here (TODOs inside)
```

### What is `pom.xml`?

Every Java project managed by **Maven** (a build tool) has a `pom.xml`. It lists:
- **Dependencies** - libraries your project needs (like `spring-boot-starter-web`)
- **Build config** - how to compile and run the project
- **Java version** - which version of Java to use

You won't need to edit `pom.xml` in this lab - it's pre-configured. Just know it exists and what it's for.

---

## How to run

1. Open this folder (`00-hello-spring`) in IntelliJ or VS Code
2. Run the main class: `Lab00HelloSpringApplication.java`
   - **IntelliJ**: click the green play button next to `main()`
   - **Terminal**: `./mvnw spring-boot:run` (from the `00-hello-spring` folder)
3. The server starts on **port 8080** (you'll see `Tomcat started on port 8080` in the console)
4. Test using the `.rest` file or open `http://localhost:8080/api/health` in your browser

---

## Your tasks

Open `Lab00HelloSpringApplication.java` and complete the TODOs:

### TODO 1 - Health check endpoint
Create a `GET /api/health` endpoint that returns:

```json
{
  "status": "UP",
  "service": "banking-api"
}
```

**Hints:**
- Add `@GetMapping("/api/health")` above a new method
- The method should return `Map<String, String>`
- Use `Map.of("status", "UP", "service", "banking-api")` to create the response

### TODO 2 - Welcome endpoint
Create a `GET /api/welcome` endpoint that accepts an **optional** query parameter `name` (default: `"Customer"`) and returns:

```json
{
  "message": "Welcome to the Banking API, Chinmay!"
}
```

**Hints:**
- Add `@GetMapping("/api/welcome")` above a new method
- Add a parameter: `@RequestParam(required = false, defaultValue = "Customer") String name`
- Return `Map.of("message", "Welcome to the Banking API, " + name + "!")`

---

## How to test

Open `lab-00-hello-spring.rest` in VS Code (with the REST Client extension) or IntelliJ (built-in HTTP client) and click "Send Request" next to each request.

### Expected results

| Request                              | Status | Response                                                   |
|--------------------------------------|--------|------------------------------------------------------------|
| `GET /api/health`                    | 200    | `{"status": "UP", "service": "banking-api"}`               |
| `GET /api/welcome`                   | 200    | `{"message": "Welcome to the Banking API, Customer!"}`     |
| `GET /api/welcome?name=Chinmay`      | 200    | `{"message": "Welcome to the Banking API, Chinmay!"}`      |

---

## Done?

If all three requests return the expected JSON, you've completed Lab 00.

You now know how to:
- Create and run a Spring Boot application
- Define GET endpoints with `@GetMapping`
- Return JSON responses automatically
- Read query parameters with `@RequestParam`

Next up: **Lab 01 - GET Logic** (enums, default values, and error responses).
