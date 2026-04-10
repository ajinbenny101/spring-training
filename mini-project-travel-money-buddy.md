# Mini Project - Travel Money Buddy

## The story

You just got your first pay cheque and you are already thinking about the Bali trip in August. Someone on your team mentions "the pound is strong against the rupiah right now, you should lock in some cash". You nod like you understand. You do not understand. You open three browser tabs, get confused by five different rate websites, and give up.

You decide to fix the problem properly. You build a small backend that answers the questions a normal human actually has when planning to spend money abroad:

- What is 500 GBP in Indonesian Rupiah right now?
- Was the rate better last week, or is now a good moment?
- How much have I already converted to IDR this year?
- If I have 1000 GBP to split between IDR, THB and JPY for a South East Asia trip, what does that look like?

No stock tickers. No portfolios. Just money you are actually going to spend, explained in a way that makes sense.

---

## What you'll build

A backend only REST API that:

1. Converts an amount between two currencies using live rates
2. Returns a full rate sheet (one base currency against many targets)
3. Shows how a rate has moved over the last N days, with a simple up or down trend
4. Splits one amount across several target currencies in a single call
5. Keeps an in memory history of every conversion the user has made, with a stats endpoint

This goes well beyond lab 09. Lab 09 made one call and returned one number. This project makes chained calls, historical calls, and has real state that changes as users interact with it.

---

## The external API: Frankfurter

You will use [Frankfurter](https://www.frankfurter.dev/). It is free, needs no API key, no sign up, no rate limit problems for a training project, and importantly it supports historical rates. The data comes from the European Central Bank so it is trustworthy enough for a learning exercise.

### Latest rates

```
GET https://api.frankfurter.dev/v1/latest?base=GBP&symbols=USD,EUR,JPY
```

Sample response:
```json
{
  "amount": 1.0,
  "base": "GBP",
  "date": "2026-04-09",
  "rates": {
    "USD": 1.265,
    "EUR": 1.173,
    "JPY": 191.42
  }
}
```

### Historical rates (a single day in the past)

```
GET https://api.frankfurter.dev/v1/2026-03-09?base=GBP&symbols=USD
```

Sample response:
```json
{
  "amount": 1.0,
  "base": "GBP",
  "date": "2026-03-09",
  "rates": {
    "USD": 1.249
  }
}
```

### Time series (a range of dates)

```
GET https://api.frankfurter.dev/v1/2026-03-01..2026-04-09?base=GBP&symbols=USD
```

Sample response (trimmed):
```json
{
  "base": "GBP",
  "start_date": "2026-03-01",
  "end_date": "2026-04-09",
  "rates": {
    "2026-03-01": { "USD": 1.252 },
    "2026-03-02": { "USD": 1.254 },
    "2026-03-03": { "USD": 1.259 }
  }
}
```

You will use all three endpoints. Each one teaches you a slightly different thing.

---

## New concepts (building on lab 09)

Lab 09 made a single call and returned a single number. Here is what this project adds.

### 1. URL building with query parameters

The Frankfurter endpoints take `base` and `symbols` as query parameters. Build the URL cleanly instead of concatenating strings:

```java
String url = UriComponentsBuilder
    .fromUriString("https://api.frankfurter.dev/v1/latest")
    .queryParam("base", base)
    .queryParam("symbols", String.join(",", targets))
    .toUriString();
```

This is the idiomatic Spring way to build URLs and it handles encoding for you.

### 2. Multiple conversions from one API call

If the user wants GBP converted to USD, EUR, JPY and INR, you do not make four calls. You make one call to `/latest` with a comma separated `symbols` list, then do four local multiplications. This is a very common pattern: be greedy with upstream calls so your own API is fast.

### 3. Historical data and computing deltas

The trend endpoint needs you to fetch a rate from today and a rate from N days ago, then compute:

```java
double delta = todayRate - pastRate;
double percent = (delta / pastRate) * 100.0;
String direction = delta > 0 ? "UP" : delta < 0 ? "DOWN" : "FLAT";
```

You return all three. The caller decides how to display it.

### 4. Stateful service with in memory history

Every time someone calls `/api/convert`, you save the result into a list inside a service:

```java
@Service
public class ConversionHistoryService {
    private final List<ConversionRecord> history = new ArrayList<>();

    public void record(ConversionRecord record) { history.add(record); }
    public List<ConversionRecord> all() { return List.copyOf(history); }
}
```

Then the stats endpoint reads the list and computes totals per currency. This is the first time in your course that one call influences the response of a later call, without a database.

### 5. Stream operations over your history

The stats endpoint is a perfect place to try Java streams:

```java
Map<String, Double> totalPerTargetCurrency = history.stream()
    .collect(Collectors.groupingBy(
        ConversionRecord::getTo,
        Collectors.summingDouble(ConversionRecord::getConvertedAmount)
    ));
```

If streams are new to you, do it with a `for` loop first, then refactor.

### 6. Input validation that actually matters

Currency codes must be exactly three uppercase letters. Amounts must be positive. The date range for the trend cannot be more than 365 days. Validate these at the controller boundary and return 400 with a useful message. Do not let garbage reach your service.

---

## Endpoints

| Method | Path | What it does |
|--------|------|--------------|
| GET | `/api/convert?from=GBP&to=USD&amount=500` | Convert one amount, record it in history |
| GET | `/api/rates/{base}` | Full rate sheet for a base currency against a default basket |
| GET | `/api/rates/{base}?symbols=USD,EUR,JPY` | Full rate sheet with a custom target list |
| GET | `/api/trend?from=GBP&to=USD&days=30` | Rate today, rate N days ago, delta, percent, direction |
| POST | `/api/split` | Split one amount across several target currencies in one response |
| GET | `/api/history` | List every conversion made so far, newest first |
| DELETE | `/api/history` | Clear the history |
| GET | `/api/history/stats` | Totals per target currency, count of conversions, most used pair |

### Sample `POST /api/split` body

```json
{
  "from": "GBP",
  "amount": 1000,
  "targets": ["IDR", "THB", "JPY", "VND"]
}
```

### Sample `POST /api/split` response

```json
{
  "from": "GBP",
  "amount": 1000,
  "rateDate": "2026-04-09",
  "results": [
    { "currency": "IDR", "rate": 20750.12, "converted": 20750120.00 },
    { "currency": "THB", "rate": 45.23, "converted": 45230.00 },
    { "currency": "JPY", "rate": 191.42, "converted": 191420.00 },
    { "currency": "VND", "rate": 31215.50, "converted": 31215500.00 }
  ]
}
```

---

## Suggested project structure

```
src/main/java/com/training/money/
  MoneyApplication.java
  config/
    AppConfig.java                   (RestTemplate @Bean)
  controller/
    ConvertController.java
    RatesController.java
    TrendController.java
    SplitController.java
    HistoryController.java
  service/
    ExchangeService.java             (calls Frankfurter)
    TrendService.java                (computes delta, percent, direction)
    HistoryService.java              (in memory list, stats)
  client/dto/
    FrankfurterLatest.java
    FrankfurterHistorical.java
  dto/
    ConversionResponse.java
    RateSheetResponse.java
    TrendResponse.java
    SplitRequest.java
    SplitResponse.java
    ConversionRecord.java
    HistoryStats.java
    ErrorResponse.java
```

Again, this is a suggestion, not a rule.

---

## Stats endpoint (what to include)

The `/api/history/stats` endpoint is where the project becomes fun. A good response might look like:

```json
{
  "totalConversions": 12,
  "totalBySourceCurrency": { "GBP": 2500.00, "USD": 150.00 },
  "totalByTargetCurrency": { "IDR": 38000000.00, "JPY": 55000.00 },
  "mostUsedPair": "GBP -> IDR",
  "mostRecent": {
    "from": "GBP",
    "to": "IDR",
    "amount": 500,
    "convertedAmount": 10375000.00,
    "at": "2026-04-10T14:22:11"
  }
}
```

Every field here comes from looping over the in memory history list. Nothing hits the external API during a stats call.

---

## How to test

```bash
# Basic conversion, should also show up in history afterwards
curl "http://localhost:8080/api/convert?from=GBP&to=USD&amount=500"

# Full rate sheet for GBP
curl "http://localhost:8080/api/rates/GBP"

# Rate sheet with a custom basket
curl "http://localhost:8080/api/rates/GBP?symbols=USD,EUR,JPY,INR"

# Trend: has the pound got stronger or weaker against the dollar in 30 days?
curl "http://localhost:8080/api/trend?from=GBP&to=USD&days=30"

# Split 1000 GBP across a South East Asia trip
curl -X POST "http://localhost:8080/api/split" \
  -H "Content-Type: application/json" \
  -d '{"from":"GBP","amount":1000,"targets":["IDR","THB","JPY","VND"]}'

# History and stats
curl "http://localhost:8080/api/history"
curl "http://localhost:8080/api/history/stats"
curl -X DELETE "http://localhost:8080/api/history"

# Validation errors
curl "http://localhost:8080/api/convert?from=gbp&to=USD&amount=100"       # lowercase
curl "http://localhost:8080/api/convert?from=GB&to=USD&amount=100"        # wrong length
curl "http://localhost:8080/api/convert?from=GBP&to=USD&amount=-10"       # negative
curl "http://localhost:8080/api/trend?from=GBP&to=USD&days=500"           # too long
```

---

## Done checklist

- [ ] App starts on port 8080 with no errors
- [ ] Convert endpoint returns the correct amount using a live rate, and records the conversion in history
- [ ] Rate sheet endpoint returns a full basket in one Frankfurter call, not one call per currency
- [ ] Split endpoint does exactly one external call regardless of how many target currencies are in the request
- [ ] Trend endpoint returns today rate, past rate, delta, percent, and direction
- [ ] Invalid currency codes return 400 with a clear message
- [ ] Negative amounts return 400 with a clear message
- [ ] History endpoint returns records in newest first order
- [ ] Clearing history returns an empty list on the next call
- [ ] Stats endpoint computes totals from history without calling Frankfurter
- [ ] You used typed DTOs for at least one external response, not `Map.class`

---

## Stretch goals

Attempt any that catch your eye. No solution provided.

1. **Best day in the last month.** For a given pair, call the time series endpoint and return which day in the last 30 days had the best rate, plus how much that would have been worth for a supplied amount.
2. **Fee aware conversion.** Accept an optional `feePercent` query param and subtract it from the converted amount. This is closer to what a real bank shows you at the ATM.
3. **Watchlist and alerts.** Accept a list of pairs and target rates. Expose an endpoint that returns only the pairs where the current rate has crossed the target.
4. **CSV export.** Add `/api/history/export` that returns the history as `text/csv`. A real user would paste this into a spreadsheet.
5. **Default home currency.** Read a `home.currency` property from `application.properties` and use it as the default `from` when the query param is missing.
6. **Funny money.** Add a hardcoded joke basket like `"pint of beer in London = 6 GBP"` and expose `/api/cost-of-living?city=Tokyo` that converts that baseline into the target country currency.

---

## A nudge

There is a temptation to make one external call per conversion. Resist it. The most interesting thing about this project is that your own API can feel fast and rich even though it only talks to the same upstream API that everyone else uses. The skill you are practising here is "fetch once, serve many answers". That same instinct will make you a better backend engineer on every project you touch after this one.
