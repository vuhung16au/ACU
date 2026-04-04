# Functional Requirements Document (FRD)
## 02.EfficientLINQ

## Purpose

Teach students how to write efficient EF Core LINQ queries that translate into smaller, clearer, and faster SQL.

## Functional Requirements

1. The app must compare inefficient and efficient LINQ patterns side by side.
2. The app must show generated SQL for each improved query.
3. The app must include examples for projection, `AsNoTracking`, server-side filtering, and pagination.
4. The app must include at least one aggregate query translated to SQL.
5. The app must seed enough product data for paging and grouping examples to feel realistic.

## Non-Functional Requirements

- The code must stay beginner-friendly.
- The UI must clearly explain why the improved query is better.
- Public members must include XML comments.

## Success Criteria

- Students can explain why early materialization is wasteful.
- Students can identify when `AsNoTracking` is helpful.
- Students can read a LINQ query and predict roughly how the SQL will differ.
