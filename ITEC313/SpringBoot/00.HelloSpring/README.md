# Hello Spring (REST API) — Sydney Edition

A minimal Spring Boot REST API built with Maven. No database — just an in-memory list and two endpoints, plus lightweight tests.

- Java: 17+
- Spring Boot: 3.3.x
- Endpoints:
  - GET `/employees/` — list all employees
  - POST `/employees/` — add a new employee (JSON body)
  - DELETE `/employees/{id}` — delete an employee by id
- Seed data uses Australia/Sydney themed names and emails.

## Test

Run automated tests (covers GET, POST, and DELETE):

```sh
# From 00.HelloSpring
mvn test
```

What’s verified:

- GET /employees/ returns the seeded list (3 employees)
- POST /employees/ creates a new employee and responds 201 with Location header ending in /employees/4
- Subsequent GET reflects the newly added employee (total 4)
- DELETE /employees/{id} returns 204 and removes the record; 404 when id not found


## Run

```sh
# From 00.HelloSpring
mvn spring-boot:run
```

Optional: set JVM timezone to Australia/Sydney when starting the app:

```sh
mvn spring-boot:run -Dspring-boot.run.jvmArguments='-Duser.timezone=Australia/Sydney'
```

## Example

- GET <http://localhost:8080/employees/>
- POST <http://localhost:8080/employees/>
- DELETE <http://localhost:8080/employees/2>

Body:

```json
{
  "firstName": "Noah",
  "lastName": "Smith",
  "email": "noah.smith@sydney.example"
}
```

## Notes

- In-memory storage only. Restarting clears data back to the seed list.
- Automated tests included; use `mvn test`.

## Manual testing (optional)

With the app running:

```sh
# List employees
curl -s http://localhost:8080/employees/ | jq

# Add a new employee
curl -i -X POST http://localhost:8080/employees/ \
  -H 'Content-Type: application/json' \
  -d '{"firstName":"Noah","lastName":"Smith","email":"noah.smith@sydney.example"}'

# Verify it was added
curl -s http://localhost:8080/employees/ | jq

# Delete an employee by id
curl -i -X DELETE http://localhost:8080/employees/2
```

## Scripted curl tests

A small script is provided to exercise GET and POST using curl.

```sh
# From 00.HelloSpring
chmod +x scripts/test_endpoints.sh

# Use default payload
BASE_URL=http://localhost:8080 ./scripts/test_endpoints.sh

# Or pass a custom JSON file as payload
BASE_URL=http://localhost:8080 ./scripts/test_endpoints.sh ./payload.json
```

Notes:

- The controller maps to `/employees/` (with a trailing slash). The script uses that path.
- If `jq` is installed, responses will be pretty-printed; otherwise raw JSON is shown.

# What's next 

- H2 in-memory for dev; Postgres profile for prod.
- Spring Data JPA: Entity + Repository + Service; demonstrate CRUD.
- Pagination & sorting: Pageable endpoints; explain Page vs Slice.
- Database migrations: Flyway to auto-create tables and seed minimal data.
- Swagger UI: Auto-generated API docs and interactive console.
- Spring Security basics: HTTP Basic for admin endpoints; permitAll for public ones.
- Resilience4j: Add retry/circuit breaker to the outbound call; expose metrics.
- Unit tests with Mockito for services.
- Static resources: Serve index.html and a health page from /static.







