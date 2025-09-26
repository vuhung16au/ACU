# Spring Boot API

Targets Spring Boot 3.5.6, Java 17. Features: JPA/Hibernate, Actuator health, Swagger UI, CORS, DTOs, services, repositories.

## Endpoints (planned)

- `GET /api/posts` with pagination `page,size,sort` (default `created_at,DESC`)
- `GET /api/posts/{id}` (includes comments)
- `POST /api/posts`, `PUT /api/posts/{id}`, `DELETE /api/posts/{id}`
- `GET /api/posts/{id}/comments`
- `GET /api/users` and `POST /api/users`
- `GET /api/users/{id}/posts`
- `POST/PUT/DELETE /api/comments`

Error response shape:
```json
{ "timestamp": "...", "status": 400, "error": "Bad Request", "message": "...", "path": "/api/..." }
```

## CORS

Allow origin `http://localhost:3000`, methods `GET,POST,PUT,DELETE`, headers `Content-Type, Authorization`.

## OpenAPI

- Swagger UI: `/swagger-ui/index.html`
- OpenAPI JSON: `/v3/api-docs`


