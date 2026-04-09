# Lab 09 - External API Call (Currency Conversion)

Your banking app doesn't exist in a vacuum. Real-world applications constantly talk to other services - payment providers, identity verification, exchange rates. In this lab, you'll teach your Spring Boot app to make HTTP calls to an external API, just like a bank clerk picking up the phone to check today's exchange rate before converting your money.

You'll call a free currency exchange API, grab the current rate, and return a clean conversion result to your user.

## What you'll build

A REST API with one endpoint that converts currencies using live exchange rates:

1. **GET /api/convert?from=GBP&to=USD&amount=100** - converts an amount from one currency to another by calling an external exchange rate API

The app calls `https://open.er-api.com/v6/latest/{currency}` to get the current rate, does the math, and returns the result. If anything goes wrong (invalid currency, API down), it returns a clean error.

## Key concepts

### 1. RestTemplate - your app's phone

RestTemplate is a tool for making HTTP requests to other servers from your Java code. Think of it as your app picking up the phone and calling another service. You give it a URL, it makes the call, and brings back the response.

```java
Map response = restTemplate.getForObject("https://some-api.com/data", Map.class);
```

This is the equivalent of typing that URL into your browser - but your Java code does it automatically.

### 2. @Bean - setting up shared tools

A method annotated with `@Bean` in a `@Configuration` class tells Spring: "create this object once and keep it ready for anyone who needs it." It's like setting up a shared tool in the office - you configure it once and everyone uses the same one.

```java
@Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}
```

Now any class in your app can ask for a `RestTemplate` and Spring will hand them the same one.

### 3. @Configuration - the setup room

`@Configuration` marks a class that contains `@Bean` definitions. Think of it as the setup/configuration room where you prepare shared tools before the office opens.

```java
@Configuration
public class AppConfig {
    // @Bean methods go here
}
```

### 4. Calling external APIs

Your app isn't alone. Real applications constantly talk to other services - payment providers, exchange rates, identity verification. RestTemplate makes this easy. You build a URL, call it, and parse the response.

The exchange rate API we're using (`open.er-api.com`) is free and requires no API key. When you call `https://open.er-api.com/v6/latest/GBP`, it returns something like:

```json
{
  "result": "success",
  "base_code": "GBP",
  "rates": {
    "USD": 1.265,
    "EUR": 1.17,
    "INR": 105.42
  }
}
```

Your code grabs the rate for the target currency and does the multiplication.

## Project structure

```
src/main/java/com/training/banking/
  Lab09ExternalApiApplication.java          <-- PRE-BUILT (main class)
  config/
    AppConfig.java                          <-- YOUR WORK (TODO 1-2)
  dto/
    ConversionResponse.java                 <-- PRE-BUILT
    ErrorResponse.java                      <-- PRE-BUILT
  service/
    ExchangeService.java                    <-- YOUR WORK (TODO 3-4)
  controller/
    ConversionController.java               <-- YOUR WORK (TODO 5-8)
```

## How to run

```bash
cd 09-external-api
./mvnw spring-boot:run
```

Or from your IDE, run `Lab09ExternalApiApplication.java`.

The app starts on **port 8089**.

## Your tasks

### Task 1 - Create the RestTemplate bean (AppConfig.java)

Open `src/main/java/com/training/banking/config/AppConfig.java`.

This is the configuration class where you set up shared objects. You need to:

1. **TODO 1**: Add the `@Configuration` annotation to the class. This tells Spring "look in this class for things to set up."

2. **TODO 2**: Add the `@Bean` annotation to the `restTemplate()` method, and make it return `new RestTemplate()`. This creates a single RestTemplate instance that the whole app can use.

### Task 2 - Build the exchange rate service (ExchangeService.java)

Open `src/main/java/com/training/banking/service/ExchangeService.java`.

This service calls the external API and extracts the exchange rate.

3. **TODO 3**: Inject `RestTemplate` via constructor injection. Add a `private final RestTemplate restTemplate` field and a constructor that accepts a `RestTemplate` parameter.

4. **TODO 4**: Implement the `getRate(String from, String to)` method:
   - Build the URL: `"https://open.er-api.com/v6/latest/" + from`
   - Call the API: `restTemplate.getForObject(url, Map.class)`
   - Extract the rates map: `(Map<String, Object>) response.get("rates")`
   - Get the specific rate: `((Number) rates.get(to)).doubleValue()`
   - Return the rate as a `Double`
   - If anything goes wrong (null response, missing currency), throw a `RuntimeException`

### Task 3 - Build the controller (ConversionController.java)

Open `src/main/java/com/training/banking/controller/ConversionController.java`.

5. **TODO 5**: Inject `ExchangeService` via constructor injection, the same way you injected `RestTemplate` into the service.

6. **TODO 6**: Add the `@GetMapping("/api/convert")` annotation to the `convert` method. The method already has three `@RequestParam` parameters: `from`, `to`, and `amount`.

7. **TODO 7**: Inside the try block, call `exchangeService.getRate(from, to)` to get the exchange rate. Then calculate `convertedAmount = amount * rate`. Build and return a `ConversionResponse` with all the fields filled in. Return it with `ResponseEntity.ok(...)`.

8. **TODO 8**: In the catch block, return a 400 error response. Use `ResponseEntity.badRequest().body(new ErrorResponse(...))` with error code `"CONVERSION_ERROR"` and a helpful message like `"Could not convert from " + from + " to " + to`.

## How to test

Use the provided `lab-09-external-api.rest` file in your IDE (IntelliJ or VS Code with REST Client).

Or use curl:

```bash
# Convert 100 GBP to USD - should return 200 with conversion result
curl "http://localhost:8089/api/convert?from=GBP&to=USD&amount=100"

# Convert 250 GBP to EUR - should return 200
curl "http://localhost:8089/api/convert?from=GBP&to=EUR&amount=250"

# Convert 50 USD to GBP - should return 200
curl "http://localhost:8089/api/convert?from=USD&to=GBP&amount=50"

# Invalid currency - should return 400 with error
curl "http://localhost:8089/api/convert?from=XYZ&to=USD&amount=100"
```

A successful response looks like:

```json
{
  "from": "GBP",
  "to": "USD",
  "originalAmount": 100.00,
  "rate": 1.265,
  "convertedAmount": 126.50
}
```

An error response looks like:

```json
{
  "error": "CONVERSION_ERROR",
  "message": "Could not convert from XYZ to USD"
}
```

## Done?

Check these off:
- [ ] App starts on port 8089 without errors
- [ ] GET /api/convert?from=GBP&to=USD&amount=100 returns 200 with a valid conversion
- [ ] GET /api/convert?from=GBP&to=EUR&amount=250 returns 200 with a valid conversion
- [ ] GET /api/convert?from=USD&to=GBP&amount=50 returns 200 with a valid conversion
- [ ] GET /api/convert?from=XYZ&to=USD&amount=100 returns 400 with CONVERSION_ERROR
- [ ] The `rate` and `convertedAmount` fields change based on live exchange rates
- [ ] Your AppConfig class has `@Configuration` and a `@Bean` method
- [ ] Your ExchangeService uses constructor injection for RestTemplate
- [ ] Your ConversionController uses constructor injection for ExchangeService

If all checks pass, your app is now talking to the outside world. This is how real banking systems work - they call external services for exchange rates, credit checks, identity verification, and more.
