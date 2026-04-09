# Lab 10 - Simple API Key Authentication

## What you'll build

A banking API where some endpoints are public (anyone can access them) and others are protected (you need a valid API key to get in). You will build this protection yourself using a plain filter - no Spring Security dependency, no magic. Just a class that inspects every incoming request and decides: let it through, or reject it.

By the end of this lab you will understand how request filtering works in Spring Boot, which is the foundation behind every authentication system you will encounter in the real world.

The API has three endpoints:

1. **GET /api/public/health** - public, no authentication needed
2. **GET /api/admin/accounts** - protected, requires a valid API key
3. **POST /api/admin/accounts** - protected, requires a valid API key

---

## Key concepts

### 1. Filters - the security guard at the entrance

Think of a filter as a security guard sitting at the building entrance. Every person (request) who walks in must pass through the guard first. The guard checks credentials and either waves you through or turns you away - before you ever reach the office (controller) you were heading to.

In Spring Boot, filters run BEFORE your controller methods. They can inspect the request, modify it, reject it, or let it pass through. This makes them perfect for cross-cutting concerns like authentication, logging, or rate limiting.

### 2. OncePerRequestFilter - your base class

Spring provides a class called `OncePerRequestFilter` that you extend to create your own filter. It guarantees your filter runs exactly once per request (sometimes requests get forwarded internally, and without this guarantee your filter might run twice).

You override one method: `doFilterInternal()`. Inside that method, you have two choices:

```java
// Choice 1: Let the request through to the next filter or controller
filterChain.doFilter(request, response);

// Choice 2: Reject the request by writing directly to the response
response.setStatus(401);
response.getWriter().write("Not allowed");
```

It is like the security guard either opening the gate or handing back a rejection slip. If the guard opens the gate (`doFilter`), the request continues its journey to the controller. If the guard writes a response directly, the request never reaches the controller at all.

### 3. @Component on the filter - registering the guard

Adding `@Component` to your filter class tells Spring: "This is a bean. Please register it automatically as a filter." Without this annotation, Spring would not know your filter exists, and every request would walk right past - like having a security guard who never showed up for work.

### 4. How it works end-to-end

```
Request comes in
    |
    v
Filter checks the path
    |
    +--> Path starts with /api/public/* --> Let it through (no check needed)
    |
    +--> Path starts with /api/admin/*  --> Check X-API-KEY header
              |
              +--> Key matches "secret-key-123" --> Let it through
              |
              +--> Key missing or wrong         --> Return 401 Unauthorized
```

This is the same pattern used by real authentication systems. The only difference is that production systems check tokens against a database or identity provider instead of a hardcoded string.

---

## Project structure

```
10-simple-auth/
  pom.xml
  lab-10-simple-auth.rest                          <-- test requests
  src/main/resources/
    application.properties                         <-- server.port=8090
  src/main/java/com/training/banking/
    Lab10SimpleAuthApplication.java                <-- pre-built (no changes needed)
    model/
      Account.java                                 <-- pre-built (no changes needed)
    dto/
      CreateAccountRequest.java                    <-- pre-built (no changes needed)
      ErrorResponse.java                           <-- pre-built (no changes needed)
    filter/
      ApiKeyFilter.java                            <-- YOUR WORK GOES HERE (TODOs 1-9)
    controller/
      PublicController.java                        <-- pre-built (no changes needed)
      AdminController.java                         <-- pre-built (no changes needed)
  solution/
    filter/
      ApiKeyFilter.java                            <-- fully working solution (peek if stuck)
```

---

## How to run

```bash
# From the 10-simple-auth directory:
./mvnw spring-boot:run

# Or if you have Maven installed:
mvn spring-boot:run
```

The app starts on **http://localhost:8090**.

---

## Your tasks

Open `ApiKeyFilter.java` and complete the numbered TODOs.

| TODO | What to do | Hint |
|------|-----------|------|
| **TODO 1** | Add `@Component` annotation to the class | This registers the filter with Spring so it actually runs |
| **TODO 2** | Make the class extend `OncePerRequestFilter` | Import `org.springframework.web.filter.OncePerRequestFilter` |
| **TODO 3** | Define the API key constant | `private static final String API_KEY = "secret-key-123";` |
| **TODO 4** | Override the `doFilterInternal` method | It takes `HttpServletRequest`, `HttpServletResponse`, and `FilterChain` |
| **TODO 5** | Get the request path | Use `request.getRequestURI()` |
| **TODO 6** | If the path starts with `/api/public`, let it through and return | Call `filterChain.doFilter(request, response)` then `return` |
| **TODO 7** | If the path starts with `/api/admin`, get the `X-API-KEY` header | Use `request.getHeader("X-API-KEY")` |
| **TODO 8** | If the header is null or does not match the API key, reject with 401 | Set status to 401, content type to `application/json`, write error JSON to `response.getWriter()` |
| **TODO 9** | If the key matches, let the request through | Call `filterChain.doFilter(request, response)` |

---

## How to test

Open `lab-10-simple-auth.rest` in IntelliJ or VS Code (with the REST Client extension) and run each request. You can also use curl:

```bash
# Health check - no key needed (expect 200)
curl http://localhost:8090/api/public/health

# Get accounts without key (expect 401)
curl http://localhost:8090/api/admin/accounts

# Get accounts with wrong key (expect 401)
curl -H "X-API-KEY: wrong-key" http://localhost:8090/api/admin/accounts

# Get accounts with valid key (expect 200)
curl -H "X-API-KEY: secret-key-123" http://localhost:8090/api/admin/accounts

# Create account with valid key (expect 201)
curl -X POST http://localhost:8090/api/admin/accounts \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: secret-key-123" \
  -d '{"accountNumber":"12345678","holderName":"Asha Patel","balance":1000.00}'

# Create account without key (expect 401)
curl -X POST http://localhost:8090/api/admin/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":"12345678","holderName":"Asha Patel","balance":1000.00}'
```

### Expected responses

**200 OK (health check):**
```json
{
  "status": "UP"
}
```

**200 OK (get accounts with valid key):**
```json
[
  {
    "accountNumber": "10001000",
    "holderName": "Asha Patel",
    "balance": 2500.00
  },
  {
    "accountNumber": "10002000",
    "holderName": "Ravi Sharma",
    "balance": 18000.00
  }
]
```

**201 Created (create account with valid key):**
```json
{
  "accountNumber": "12345678",
  "holderName": "Asha Patel",
  "balance": 1000.00
}
```

**401 Unauthorized (missing or wrong key):**
```json
{
  "error": "UNAUTHORIZED",
  "message": "Missing or invalid API key"
}
```

---

## Done?

Check your work against these criteria:

- [ ] GET /api/public/health returns 200 with no API key
- [ ] GET /api/admin/accounts returns 401 with no API key
- [ ] GET /api/admin/accounts returns 401 with a wrong API key
- [ ] GET /api/admin/accounts returns 200 with the correct API key
- [ ] POST /api/admin/accounts returns 201 with the correct API key and valid body
- [ ] POST /api/admin/accounts returns 401 with no API key
- [ ] All 401 responses include both "error" and "message" fields

If all tests pass, congratulations - you have built your first request filter. This is the same pattern that powers authentication in production Spring Boot applications.
