# Functional Requirements Document

## Purpose

Create a small Minimal API that teaches route values and query string values with a shared tasks example.

## Functional Requirements

1. The application must expose `GET /`.
2. The root endpoint must describe the API endpoints.
3. The application must expose `GET /api/tasks/{id}`.
4. The task-by-id endpoint must return `404 Not Found` when the id is unknown.
5. The application must expose `GET /api/tasks/search?priority=...`.
6. The search endpoint must filter tasks by priority.
7. The application must expose `GET /api/tasks/filter?completed=...`.
8. The filter endpoint must filter tasks by completion state.
9. The solution must include unit tests for the task catalog service.

## Non-Functional Requirements

1. The code must remain beginner-friendly.
2. Public C# members must include XML documentation comments.
3. The project must build with .NET 10.

## Constraints

- Use ASP.NET Core Minimal APIs.
- Use hard-coded sample data.
- Keep the example focused on `GET` requests only.
