# Quick Start

```bash
cd 40-Advanced-EFCore/05.PerformanceOptimization
dotnet restore
dotnet run
```

The next implementation pass will add baseline and optimized query comparisons with timing output.
﻿# Quickstart

## Prerequisites

- .NET 10 SDK
- Docker Desktop
- Shared PostgreSQL services from [`../docker-compose.yml`](../docker-compose.yml)
- Optional: pgAdmin or Azure Data Studio

## Start PostgreSQL

From [`../`](../):

```bash
docker compose up -d
```

If your machine already uses port `5432`, keep the custom value from [`../.env`](../.env). This module expects the shared setup on port `55432`.

## Run the app

```bash
dotnet run --project 05.PerformanceOptimization/PerformanceOptimization.csproj
```

Open:

- `http://127.0.0.1:5000` if you use the default launch profile
- or your custom `ASPNETCORE_URLS` port if you override it

## What to try

1. Open `/OptimizationLab`.
2. Compare the slow baseline to the optimized stages.
3. Read the generated SQL and the benchmark notes.
4. Open `/Products` to see the paged optimized leaderboard.

## Inspect the database

Connect pgAdmin or Azure Data Studio with:

- Host: `localhost`
- Port: `55432`
- Database: `advanced_efcore_performance`
- Username: `advanced_efcore_user`
- Password: `advanced_efcore_password`

Run `EXPLAIN ANALYZE` by pasting the SQL shown in the lab page.

## Run tests

```bash
dotnet test 05.PerformanceOptimization/tests/PerformanceOptimization.Tests/PerformanceOptimization.Tests.csproj
```

## Run the Playwright capture

Start the app first, then run:

```bash
node 05.PerformanceOptimization/scripts/playwright-performance-optimization.js
```

Artifacts are written to [`artifacts/`](./artifacts/).
