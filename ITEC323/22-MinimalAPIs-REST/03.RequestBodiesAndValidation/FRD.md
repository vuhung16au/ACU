# Functional Requirements Document

## Purpose

Create a small Minimal API that teaches JSON request bodies and simple request validation.

## Functional Requirements

1. The application must expose `GET /`.
2. The application must expose `GET /api/priorities`.
3. The application must expose `GET /api/tasks`.
4. The application must expose `GET /api/tasks/{id}`.
5. The priorities endpoint must return `Low`, `Medium`, and `High`.
6. The application must expose `POST /api/tasks`.
7. The application must expose `PUT /api/tasks/{id}`.
8. The application must expose `DELETE /api/tasks/{id}`.
9. The create and update endpoints must require `Title`.
10. The create and update endpoints must require `Priority`.
11. The create and update endpoints must reject unsupported priorities.
12. The create endpoint must return `201 Created` when the request is valid.
13. The update endpoint must return `200 OK` when the request is valid.
14. The delete endpoint must return `204 No Content` when the task is deleted.
15. The solution must include unit tests for request validation, task creation logic, and in-memory task storage.

## Non-Functional Requirements

1. The code must remain beginner-friendly.
2. Public C# members must include XML documentation comments.
3. The project must build with .NET 10.
