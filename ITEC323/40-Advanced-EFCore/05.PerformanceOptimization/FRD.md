# Functional Requirements Document (FRD)
## 05.PerformanceOptimization

This lesson will start with a deliberately slow page and improve it step by step while students inspect SQL and performance signals.
# Functional Requirements

## Purpose

Teach students a practical workflow for improving EF Core query performance with PostgreSQL.

## Functional requirements

1. The lesson shall seed a PostgreSQL database with enough relational data to make query shape meaningful.
2. The lesson shall provide a benchmark page that compares a deliberately slow baseline query with improved alternatives.
3. The benchmark page shall show generated SQL for each optimization stage.
4. The benchmark page shall explain why each stage improves or does not improve database performance.
5. The lesson shall provide an optimized leaderboard page that uses projection, `AsNoTracking`, and pagination.
6. The lesson shall explain how compiled queries fit into a performance workflow.
7. The lesson shall guide students to inspect query plans using pgAdmin or Azure Data Studio.
8. The project shall include a Playwright script that records the main demo and saves a screenshot and video to `artifacts/`.

## Non-functional requirements

1. The project shall target .NET 10.
2. The app shall run as an ASP.NET Core Razor Pages application.
3. The data access layer shall use EF Core with PostgreSQL.
4. The code and UI shall remain beginner-friendly and heavily explanatory.

## Constraints

1. Use the shared Docker PostgreSQL environment from the module root.
2. Use relative Markdown links in documentation.
3. Avoid exotic optimizations that distract from the educational purpose.

## Success criteria

- Students can identify why the baseline query is slow.
- Students can point to the SQL changes introduced by each optimization stage.
- Students can explain when to use `AsNoTracking`, projection, pagination, and compiled queries.
