# Functional Requirements Document

## Purpose

Create a Minimal API that demonstrates clean status codes and safe error handling.

## Functional Requirements

1. The API must return `404 Not Found` for an unknown task id.
2. The API must return `400 Bad Request` for invalid task input.
3. The API must return `409 Conflict` when a duplicate title is submitted.
4. The API must return `500 Internal Server Error` with a safe response for unexpected failures.
5. The solution must include unit tests for success paths and failure paths.
