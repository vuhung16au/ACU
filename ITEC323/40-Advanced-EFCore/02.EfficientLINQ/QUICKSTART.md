# Quick Start

## Prerequisites

- Shared PostgreSQL services running from [`40-Advanced-EFCore`](../)
- .NET 10 SDK

## Run The Project

```bash
cd 40-Advanced-EFCore/02.EfficientLINQ
dotnet restore
dotnet run
```

The app creates and seeds its lesson database automatically on startup.

## Demo Flow

1. Open `/QueryLab`.
2. Read each inefficient LINQ pattern.
3. Compare it with the improved version and the generated SQL.
4. Open `/Products` to see the paged projection query used in a more realistic screen.

## Inspect SQL

- Read the SQL snippets on `/QueryLab`
- Watch the terminal logs while loading the pages
- Use pgAdmin or Azure Data Studio to inspect the `Products` and `Categories` tables

## Troubleshooting

### PostgreSQL is not running

```bash
cd 40-Advanced-EFCore
docker compose ps
docker compose logs postgres
```

### Local port is different

If you changed the Docker host port in `.env`, pass a matching connection string:

```bash
ConnectionStrings__DefaultConnection="Host=localhost;Port=55432;Database=advanced_efcore_linq;Username=advanced_efcore_user;Password=advanced_efcore_password" dotnet run
```
