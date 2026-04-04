# Functional Requirements Document (FRD)
## Module 40: Advanced EF Core

## 1. Purpose

This module teaches advanced EF Core skills for scalable .NET applications. Students move beyond basic CRUD and begin evaluating the cost, correctness, and tradeoffs of their data access code.

## 2. In Scope

- Optimistic concurrency handling
- Query-shape optimization
- N+1 query diagnosis and fixes
- Raw SQL with Dapper
- Shared EF Core and Dapper transactions
- PostgreSQL inspection with Docker, pgAdmin, and Azure Data Studio
- Generated SQL analysis
- Practical performance optimization workflow

## 3. Out Of Scope

- Distributed caching
- Sharding or replication
- Complex microservice data architectures
- Production APM tooling

## 4. Learning Objectives

Students will be able to:

1. Explain optimistic concurrency and resolve update conflicts safely.
2. Write LINQ queries that reduce payload size and unnecessary tracking.
3. Detect N+1 query problems and choose an appropriate fix.
4. Use Dapper for targeted reporting scenarios while keeping SQL parameterized.
5. Inspect SQL, execution plans, and measured timings to improve EF Core performance.

## 5. Acceptance Criteria

- Each lesson runs as a standalone Razor Pages sample.
- PostgreSQL is used for every lesson.
- Each lesson includes documentation and at least one automated artifact capture script.
- At least one lesson fully demonstrates conflict resolution, SQL inspection, and performance discussion end to end.
