# 05.PerformanceOptimization

`05.PerformanceOptimization` turns the earlier EF Core lessons into a repeatable tuning workflow. Students start with a deliberately heavy query, inspect the generated SQL, compare benchmark stages, and then ship an optimized version that uses `AsNoTracking`, projection, paging, and a compiled query.

## Learning objectives

- Diagnose a slow EF Core query before changing code.
- Inspect generated SQL with `ToQueryString()` and application logging.
- Compare a baseline query against optimized stages.
- Explain why projection, reduced tracking, and paging reduce database work.
- Use pgAdmin or Azure Data Studio to run `EXPLAIN ANALYZE`.

## Lesson flow

1. Open the optimization lab homepage.
2. Navigate to the benchmark lab and compare the four stages.
3. Inspect the generated SQL for each stage.
4. Run the optimized product leaderboard.
5. Use pgAdmin or Azure Data Studio to inspect tables and execution plans.

## Pages

- `/` overview and workflow
- `/OptimizationLab` side-by-side benchmark stages
- `/Products` optimized leaderboard with pagination

## Technology stack

- ASP.NET Core Razor Pages
- Entity Framework Core 10
- PostgreSQL via Docker
- Npgsql EF Core provider
- Playwright JavaScript for screenshot/video capture

## Quick links

- [`QUICKSTART.md`](./QUICKSTART.md)
- [`FRD.md`](./FRD.md)
- [`docs/Key-Takeaways.md`](./docs/Key-Takeaways.md)
