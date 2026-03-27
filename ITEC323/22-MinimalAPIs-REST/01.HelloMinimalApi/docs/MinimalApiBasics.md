# Minimal API Basics

## Why Minimal APIs?

Minimal APIs reduce setup code so we can focus on the core ideas of HTTP endpoints, JSON responses, and request handling.

Instead of creating controllers, this sample maps endpoints directly in `Program.cs`.

## What This Project Teaches

This project shows three important REST ideas:

- `GET` endpoints for reading data
- route parameters such as `/api/greet/{name}`
- `POST` endpoints for sending JSON bodies

## Endpoint Examples

### `GET /api/message`

Returns a small JSON object.

### `GET /api/languages`

Returns a JSON array.

### `GET /api/greet/{name}`

Uses a route value to build a personalized response.

### `POST /api/submissions`

Accepts JSON input and returns:

- `400 Bad Request` for missing required fields
- `201 Created` for a valid request

## Why We Still Use Small Services

The endpoints stay short because the greeting, language, and submission logic live in small service classes.

This helps students learn two things at once:

- how Minimal APIs map HTTP endpoints
- how business logic can stay organized and testable
