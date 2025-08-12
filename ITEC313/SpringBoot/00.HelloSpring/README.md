# Hello Spring (REST API) — Sydney Edition

A minimal Spring Boot REST API built with Maven. No database — just an in-memory list and two endpoints, plus lightweight tests.

- Java: 17+
- Spring Boot: 3.3.x
- Endpoints:
  - GET `/employees/` — list all employees
  - POST `/employees/` — add a new employee (JSON body)
- Seed data uses Australia/Sydney themed names and emails.

## Test

Run automated tests (covers GET /employees/ and POST /employees/):

```sh
# From 00.HelloSpring
mvn test
```

What’s verified:

- GET /employees/ returns the seeded list (3 employees)
- POST /employees/ creates a new employee and responds 201 with Location header ending in /employees/4
- Subsequent GET reflects the newly added employee (total 4)


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
