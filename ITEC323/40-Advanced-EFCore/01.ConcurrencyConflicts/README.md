# 01.ConcurrencyConflicts

This lesson shows how EF Core handles optimistic concurrency when two users edit the same PostgreSQL row at nearly the same time.

## Learning Objectives

- Understand why concurrency conflicts happen in web apps.
- Configure an EF Core concurrency token.
- Catch `DbUpdateConcurrencyException`.
- Compare the student’s attempted changes with the latest database values.
- Choose between refreshing, cancelling, or overwriting.
- Inspect the generated SQL and find the concurrency check in the `UPDATE` statement.

## What This Project Demonstrates

- PostgreSQL-backed Razor Pages app
- EF Core concurrency token using a version column
- SQL logging through `LogTo(...)`
- Seeded product catalog for repeatable demos
- Conflict resolution UI at `/Products/Edit/{id}`
- SQL inspection page at `/Diagnostics`

## Key Pages

- `/` lesson overview
- `/Products` product list
- `/Products/Edit/{id}` edit form with concurrency handling
- `/Diagnostics` SQL inspection page

## Why This Matters

Without concurrency handling, the last write silently wins. That is confusing for users and dangerous for shared business data. This sample helps students see the conflict, understand why it happened, and resolve it clearly.
