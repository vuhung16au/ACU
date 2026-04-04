# Functional Requirements Document (FRD)
## 01.ConcurrencyConflicts

## Purpose

Teach optimistic concurrency in EF Core through a concrete PostgreSQL-backed editing workflow.

## Functional Requirements

1. The app must display a list of products with editable fields.
2. The app must detect when another user has changed the same product since the current page was loaded.
3. The app must catch `DbUpdateConcurrencyException` and show a friendly conflict summary.
4. The app must display both the user’s attempted values and the latest database values.
5. The app must let the user refresh current values, cancel editing, or overwrite the database values.
6. The app must log generated SQL and teach students how to inspect it.
7. The app must seed repeatable demo data.

## Non-Functional Requirements

- The code must remain beginner-friendly.
- Public members must include XML comments.
- The UI must work on desktop and mobile widths.

## Success Criteria

- Students can trigger a conflict reliably with two browser tabs.
- Students can explain how the version column prevents silent overwrites.
- Students can locate the concurrency check in the generated SQL.
