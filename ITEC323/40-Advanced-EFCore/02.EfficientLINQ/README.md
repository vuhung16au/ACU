# 02.EfficientLINQ

This lesson teaches students how LINQ query shape changes the SQL sent to PostgreSQL and why those changes matter for performance.

## Learning Objectives

- Spot inefficient query patterns before they become performance problems.
- Compare tracked full-entity queries with lightweight projections.
- Avoid filtering and sorting in memory after early materialization.
- Apply `AsNoTracking`, server-side filtering, and pagination deliberately.
- Read generated SQL and connect it back to the LINQ that produced it.

## What This Project Demonstrates

- PostgreSQL-backed Razor Pages app
- Seeded catalog with categories and products
- Side-by-side query examples on `/QueryLab`
- A paged product list on `/Products`
- SQL inspection using `ToQueryString()` and console logging
- Concrete comparisons for:
  - full entity loading vs projection
  - client-side filtering vs server-side filtering
  - unpaged reads vs paged reads
  - client grouping vs translated SQL aggregation

## Key Pages

- `/` lesson overview
- `/QueryLab` efficient LINQ comparison page
- `/Products` paged product list using projection and `AsNoTracking`

## Why This Matters

Many slow EF Core apps are not slow because EF Core is “bad”. They are slow because the query asks for too much data, tracks objects unnecessarily, or moves work from the database into application memory. This lesson makes those tradeoffs visible.
