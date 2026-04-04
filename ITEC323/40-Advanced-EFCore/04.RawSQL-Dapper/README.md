# 04.RawSQL-Dapper

This lesson shows when EF Core is a comfortable fit and when a SQL-first reporting query through Dapper is a clearer and lighter tool.

## Learning Objectives

- Compare an EF Core reporting projection with a parameterized Dapper SQL query.
- Understand why Dapper is useful for focused read-heavy scenarios.
- Share a connection and transaction across EF Core and Dapper.
- Keep raw SQL parameterized and readable.

## What This Project Demonstrates

- PostgreSQL-backed Razor Pages app
- Seeded products, sales records, and audit entries
- A report comparison page at `/Reports`
- A shared-transaction demo at `/Transactions`
- EF Core reporting projection and the equivalent Dapper SQL
- A realistic inventory page that still uses EF Core comfortably

## Key Pages

- `/` lesson overview
- `/Reports` EF Core vs Dapper reporting comparison
- `/Transactions` shared transaction demo
- `/Products` EF Core-friendly product maintenance view

## Why This Matters

EF Core is productive for entity-centric work, but some reporting queries are easier to reason about in SQL. This lesson teaches students to choose the right level of abstraction instead of forcing every query through the same tool.
