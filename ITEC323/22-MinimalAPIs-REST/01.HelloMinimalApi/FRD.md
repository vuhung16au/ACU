# Functional Requirements Document

## Purpose

Create a short, beginner-friendly Minimal API project that teaches the core REST ideas of routes, JSON responses, request bodies, and HTTP status codes.

## Functional Requirements

1. The application must expose a root endpoint at `/`.
2. The root endpoint must return a JSON description of the available API endpoints.
3. The application must expose a `GET /api/message` endpoint.
4. The message endpoint must return a short JSON welcome message.
5. The application must expose a `GET /api/languages` endpoint.
6. The language endpoint must return a JSON list containing `Java`, `Python`, and `C#`.
7. The application must expose a `GET /api/greet/{name}` endpoint.
8. The greet endpoint must return a JSON greeting that includes the route value.
9. The application must expose a `POST /api/submissions` endpoint.
10. The submissions endpoint must accept a JSON body with `Name`, `Email`, and `FavoriteLanguage`.
11. The submissions endpoint must return `400 Bad Request` when `Name` is missing.
12. The submissions endpoint must return `400 Bad Request` when `Email` is missing.
13. The submissions endpoint must return `201 Created` with normalized response data when the request is valid.
14. The solution must include unit tests for the small service classes that support the API.

## Non-Functional Requirements

1. The code must remain simple enough for first-time learners.
2. Public C# members must include XML documentation comments.
3. The project must build with .NET 10.
4. The documentation must explain how to run the API and tests.
5. The code should stay short and focused on REST basics.

## Constraints

- Use ASP.NET Core Minimal APIs.
- Keep the example lightweight and beginner-friendly.
- Use xUnit and FluentAssertions for the supporting unit tests.

## Success Criteria

- A student can run the API locally.
- A student can send `GET` and `POST` requests successfully.
- The API returns clear JSON responses and appropriate status codes.
- The unit tests pass successfully.
