# Functional Requirements Document

## Purpose

Create a CRUD-style Minimal API that stores tasks in memory.

## Functional Requirements

1. The application must expose `GET /api/tasks`.
2. The application must expose `GET /api/tasks/{id}`.
3. The application must expose `POST /api/tasks`.
4. The application must expose `PUT /api/tasks/{id}`.
5. The application must expose `DELETE /api/tasks/{id}`.
6. The API must return `404 Not Found` for unknown task ids.
7. The API must validate `Title` and `Priority`.
8. The solution must include unit tests for repository and validation logic.
