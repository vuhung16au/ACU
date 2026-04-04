# 40-Advanced-EFCore

Advanced Entity Framework Core lessons for ITEC323. This module extends Week 9 by focusing on data-access issues students will meet in real applications: concurrency, inefficient query shapes, N+1 query problems, targeted raw SQL, and systematic performance tuning.

## Module Outcomes

- Handle optimistic concurrency conflicts in a beginner-friendly Razor Pages workflow.
- Inspect generated SQL and reason about query cost.
- Recognize and fix N+1 query problems.
- Use Dapper when lower-level SQL control is worthwhile.
- Compare slower and faster EF Core approaches using concrete examples.

## Lesson Order

- [`01.ConcurrencyConflicts`](./01.ConcurrencyConflicts)
- [`02.EfficientLINQ`](./02.EfficientLINQ)
- [`03.NPlusOne`](./03.NPlusOne)
- [`04.RawSQL-Dapper`](./04.RawSQL-Dapper)
- [`05.PerformanceOptimization`](./05.PerformanceOptimization)

## Shared Infrastructure

This module uses one Dockerized PostgreSQL server and one pgAdmin instance at the module root:

```bash
cd 40-Advanced-EFCore
cp .env.example .env
docker compose up -d
```

Default tools:

- PostgreSQL on `localhost:5432`
- pgAdmin on `http://localhost:5050`
- lesson-specific databases created by the individual demos

## Query Inspection Workflow

Every lesson teaches students to:

1. Read LINQ or EF Core save logic.
2. Inspect generated SQL in console logs or teaching pages.
3. Verify data state in pgAdmin or Azure Data Studio.
4. Improve the query or update pattern.
5. Measure the effect.

## Current Status

- `01.ConcurrencyConflicts` is fully implemented.
- `02` to `05` are scaffolded with starter documentation, starter apps, and Playwright capture script placeholders so they can be expanded lesson by lesson.
