# Quick Start

## Prerequisites

- .NET 10 SDK installed
- `dotnet ef` tool installed

## Run The Project

```bash
cd 09.DataPersistenceEFCore/03.LinqQueries
dotnet restore
dotnet run
```

Open the URL shown in the terminal (typically `http://localhost:52xx`).

## Pages To Check

- `/` module home page and learning goals
- `/Products` CRUD pages
- `/LinqQueries` LINQ operator demonstrations

## Build Check

```bash
dotnet build
```

## Notes About In-Memory Provider

- Data is seeded automatically on startup.
- Data resets each time the app restarts.
- Migrations are not required for this project.
- Focus is on LINQ query patterns, not database setup or persistence.