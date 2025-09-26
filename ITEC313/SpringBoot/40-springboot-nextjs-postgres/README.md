# Java Programming Blog

A simple learning project: Spring Boot (backend) + Next.js (frontend) + PostgreSQL, orchestrated with Docker Compose.

## Quick Start

Prereqs: Docker Desktop.

```bash
docker compose up -d --build
```

Services:
- Backend (Spring Boot): http://localhost:8080
  - Health: http://localhost:8080/actuator/health
  - Swagger UI: http://localhost:8080/swagger-ui/index.html
  - OpenAPI JSON: http://localhost:8080/v3/api-docs
- Frontend (Next.js): http://localhost:3000
- Database (Postgres): localhost:5432 (blogdb/bloguser/blogpassword)

## Project Structure

```
backend/   # Spring Boot API
frontend/  # Next.js 15 app router UI
postgres/  # Local volume for Postgres data
data/      # SQL init (schema + seed)
docs/      # Additional docs
```

## Environment

- Database env (compose): POSTGRES_DB=blogdb, POSTGRES_USER=bloguser, POSTGRES_PASSWORD=blogpassword
- Backend env (compose): SPRING_DATASOURCE_URL=jdbc:postgresql://database:5432/blogdb

## Local Development

You can develop inside containers (volumes are mounted):
- Backend: `./backend` with Gradle wrapper (to be added). The compose service runs `gradle bootRun`.
- Frontend: `./frontend` runs `npm run dev` inside the container.

## Docs

See `docs/spring-boot-api.md`, `docs/nextjs-frontend.md`, and `docs/postgres-database.md` for details.

## License

MIT


