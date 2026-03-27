# Functional Requirements Document

## Purpose

Create a complete but beginner-friendly Tasks API using ASP.NET Core Minimal APIs.

## Functional Requirements

1. The application must expose task list, details, search, summary, create, update, and delete endpoints.
2. The search endpoint must support priority and completion filters.
3. The summary endpoint must return total, completed, and pending task counts.
4. The API must validate title and priority values.
5. The API must return `404` for missing tasks.
6. The API must return `409` for duplicate titles.
7. The solution must include unit tests for repository and service logic.
