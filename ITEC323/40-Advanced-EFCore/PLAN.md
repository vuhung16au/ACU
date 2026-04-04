# Plan for `40-Advanced-EFCore`

## Summary

Create a new module-level folder, `40-Advanced-EFCore`, as an advanced follow-on to `09.DataPersistenceEFCore`. Use a shared Docker/PostgreSQL foundation at the module root, then implement five standalone Razor Pages demos underneath it:

- `01.ConcurrencyConflicts`
- `02.EfficientLINQ`
- `03.NPlusOne`
- `04.RawSQL-Dapper`
- `05.PerformanceOptimization`

Each subfolder should be a self-contained .NET 10 Razor Pages project with its own `README.md`, `QUICKSTART.md`, `FRD.md`, `docs/`, `tests/`, `scripts/playwright-xxx.js`, and `artifacts/`. All demos should use PostgreSQL, teach SQL inspection, and stay beginner-friendly while clearly showing when EF Core is helpful and when lower-level control is better.

## Key Changes

### Module-level structure

Add these shared assets at `40-Advanced-EFCore/`:

- `README.md`: module overview, progression, prerequisites, and links to the five lessons.
- `QUICKSTART.md`: shared setup for Docker, Postgres, pgAdmin, Azure Data Studio, `dotnet ef`, and how to run each demo.
- `FRD.md`: module-wide learning goals and acceptance criteria.
- `docker-compose.yml`: shared `postgres` and `pgadmin` services plus a named Docker volume for persistence.
- `.env.example`: container credentials/ports placeholders.
- `40-Advanced-EFCore.sln`: solution containing all five topic projects and their test projects.

Use one shared Postgres instance for the whole module. Each lesson gets its own database name or schema to avoid collisions while keeping setup simple.

### Common project conventions for all five lessons

Each lesson should follow the existing repository conventions:

- Razor Pages app on .NET 10 / C# 14.
- `Data/AppDbContext.cs`, `Models/`, `Pages/`, `Services/`, `docs/`, `tests/<Project>.Tests/`.
- PostgreSQL connection via `Npgsql` and EF Core provider.
- Logging configured to surface generated SQL in console output.
- A small seeded relational model reused conceptually across lessons so students focus on the advanced concept, not domain complexity.
- `scripts/playwright-xxx.js` that automates the demo flow and writes screenshots/video into the lesson’s `artifacts/` folder.
- Lesson docs explain both the app behavior and the exact SQL-related learning point.

Use a consistent sample domain across all lessons: `Category`, `Product`, `Order`, `OrderItem`. That gives enough relationships for concurrency, projections, N+1, joins, aggregates, and Dapper reporting.

### Lesson-by-lesson content

#### `01.ConcurrencyConflicts`

Teach optimistic concurrency with a PostgreSQL-backed `Product` edit screen.

Implementation:

- Add a concurrency token to `Product`, using EF Core’s optimistic concurrency pattern.
- Show two browser sessions editing the same row.
- Catch `DbUpdateConcurrencyException`, reload current database values, and display a beginner-friendly conflict resolution screen.
- Let students choose between overwrite, cancel, or refresh-and-review.
- Log the generated SQL and point out the concurrency check in the `UPDATE` statement.
- Include docs explaining why optimistic concurrency is preferred for web apps.

Primary teaching outcome: students can explain what a concurrency conflict is, how EF Core detects it, and how to resolve it safely.

#### `02.EfficientLINQ`

Teach how query shape affects SQL and payload size.

Implementation:

- Compare inefficient patterns against improved patterns:
  - materializing too early with `ToList()`
  - selecting full entities when only a few columns are needed
  - missing `AsNoTracking()` for read-only queries
  - missing pagination
- Add pages that show:
  - full entity query
  - projection query with `Select`
  - paged query with `Skip/Take`
  - aggregate query with `GroupBy`
- Surface generated SQL with `ToQueryString()` and logging.
- Include side-by-side explanations: LINQ code, SQL output, and why one version is better.

Primary teaching outcome: students learn to write LINQ that translates into smaller, faster SQL.

#### `03.NPlusOne`

Teach what N+1 is, how it happens, and how to fix it.

Implementation:

- Use `Orders` with related `OrderItems` and `Products`.
- Provide one page that intentionally triggers repeated related-data queries.
- Provide improved versions using `Include`, projection, or batched loading depending on the scenario.
- Count/log the number of SQL commands executed during each approach.
- Explain eager loading vs projection and when loading whole graphs is unnecessary.

Primary teaching outcome: students can recognize N+1 symptoms and choose an appropriate fix rather than blindly adding `Include` everywhere.

#### `04.RawSQL-Dapper`

Teach when Dapper is appropriate and how to mix it with EF Core responsibly.

Implementation:

- Add Dapper alongside EF Core in the same Razor Pages app.
- Use EF Core for normal writes and entity-centric operations.
- Use Dapper for read-heavy reporting queries, such as sales summaries or dashboard projections.
- Show parameterized SQL only; no string concatenation.
- Demonstrate transaction sharing across EF Core and Dapper using EF Core’s current connection and transaction.
- Compare the same reporting scenario implemented with EF Core projection vs Dapper SQL.

Primary teaching outcome: students understand Dapper as a targeted tool for performance/control, not a replacement for EF Core.

#### `05.PerformanceOptimization`

Tie the earlier lessons together into a practical optimization lab.

Implementation:

- Start with a deliberately slow page or report.
- Improve it in stages:
  - `AsNoTracking`
  - projection instead of full entity load
  - pagination
  - reducing includes
  - compiled queries where appropriate
  - index-aware filtering/sorting discussion
  - optional Dapper rewrite for a reporting hotspot
- Teach students to inspect SQL, execution plans, and row counts in pgAdmin or Azure Data Studio.
- Include a simple benchmarking or timing display inside the app so students can compare versions.

Primary teaching outcome: students learn a repeatable workflow for diagnosing and improving EF Core data access.

### Shared Docker/database tooling

Use `docker-compose.yml` with:

- `postgres:16-alpine`
- named volume for persistent data
- exposed port `5432`
- `pgadmin` service with exposed web UI
- environment variables documented in `.env.example`

Documentation should teach:

- `docker compose up -d`
- how to connect from EF Core
- how to inspect tables/data in pgAdmin
- how to connect with Azure Data Studio to run SQL manually
- how to use `EXPLAIN ANALYZE` on selected queries
- how to reset data safely without deleting lesson code

## Public APIs / Interfaces / Packages

Add these shared technical choices across the module:

- EF Core PostgreSQL provider: `Npgsql.EntityFrameworkCore.PostgreSQL`
- Dapper package: `Dapper`
- SQL logging via EF Core `LogTo(...)` configuration
- Optional query inspection helpers that expose generated SQL strings for teaching pages
- Playwright automation implemented as Node scripts under each lesson’s `scripts/`

Each lesson should expose a small set of demo pages, typically:

- `/` overview page
- `/Products` or `/Orders` operational page
- `/Diagnostics` or `/QueryLab` teaching page that displays query behavior and SQL

## Test Plan

For each lesson include:

- unit/integration tests for the core service or query behavior
- at least one test validating the main advanced concept
- a Playwright-driven demo capture flow that:
  - starts from the lesson home page
  - performs the key scenario
  - saves at least one screenshot
  - saves a video into `artifacts/`

Topic-specific checks:

- Concurrency: conflict is detected and friendly resolution UI appears.
- Efficient LINQ: projection and paging return expected data.
- N+1: improved path executes fewer queries than the naive path.
- Dapper: parameterized raw SQL returns expected report rows.
- Performance: optimized path shows measurable improvement or reduced SQL work compared with baseline.

## Assumptions and Defaults

- Use standalone lesson apps, not one evolving app.
- Use one shared Docker/Postgres foundation at module root.
- Use PostgreSQL for every lesson, not InMemory or SQLite.
- Use Razor Pages throughout, to match the rest of the repository.
- Include `README.md`, `QUICKSTART.md`, and `FRD.md` in every lesson folder.
- Start implementation with `01.ConcurrencyConflicts`, then proceed in numeric order.
- `PLAN.md` should contain this plan once implementation mode allows file writes.
