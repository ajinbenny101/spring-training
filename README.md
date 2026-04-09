# Lab 02 - POST Validation (Manual)

## Objective
Build a small API that:
- accepts JSON input via POST
- validates input manually (no annotations)
- stores up to 20 students in memory (no Map)
- returns correct HTTP status codes
- returns JSON success/error responses

---

## Endpoints

### POST /api/students
Body:
```json
{ "id":"S1", "name":"Asha", "email":"asha@example.com" }```

### Rules:

- id/name/email required (not null/blank)
- id must start with "S" and length >= 2
- email must contain '@' and '.'
- if id exists -> 409 (error: USER_ALREADY_EXISTS)
- if store full -> 409 (error: STORE_FULL)
- on success -> 201 with:
```
{ "message": "Student created", "student": { ... } }
```

### Error JSON:
```
{ "error": "BAD_REQUEST", "message": "..." }
```

### GET /api/students/{id}
- 200 with Student JSON if found
- 404 with ErrorResponse JSON if not found

### Constraints (strict)
- No Map usage
- No validation annotations
- No database
- Use provided DTOs: CreateStudentRequest, Student, SuccessResponse, ErrorResponse

### How to test

Use lab-02-post-validation.rest