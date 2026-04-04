# Quick Start

## Prerequisites

- Shared PostgreSQL services running from [`40-Advanced-EFCore`](/Users/vuhung/00.Work/02.ACU/github/ITEC323/40-Advanced-EFCore)
- .NET 10 SDK

## Run The Project

```bash
cd 40-Advanced-EFCore/01.ConcurrencyConflicts
dotnet restore
dotnet run
```

The app creates and seeds its lesson database automatically on startup.

## Demo Steps

1. Open `/Products`.
2. Open the same product edit page in two browser tabs.
3. Save changes in the first tab.
4. Save different changes in the second tab.
5. Review the conflict screen.
6. Try `Refresh Current Values`, `Overwrite Database Values`, and `Cancel`.

## Inspect SQL

Open `/Diagnostics` and compare:

- the query used to load editable products
- the update shape EF Core generates
- the `WHERE` clause that includes the original `Version`

You can also watch the console logs while saving edits.

## Troubleshooting

### PostgreSQL not running

```bash
cd 40-Advanced-EFCore
docker compose ps
docker compose logs postgres
```

### Reset the lesson database

Connect with pgAdmin or Azure Data Studio and drop the `advanced_efcore_concurrency` database, then restart the app.
