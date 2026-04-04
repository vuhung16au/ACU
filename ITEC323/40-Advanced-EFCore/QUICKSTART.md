# Quick Start

## Prerequisites

- .NET 10 SDK
- Docker Desktop or Docker Engine with Compose
- Optional: `dotnet ef` CLI tool
- Optional: Node.js if you want to run the JavaScript Playwright capture scripts

Install the EF Core CLI if needed:

```bash
dotnet tool install --global dotnet-ef
```

## Start Shared Services

```bash
cd 40-Advanced-EFCore
cp .env.example .env
docker compose up -d
```

Verify the containers:

```bash
docker compose ps
docker compose logs postgres
```

## Connect With Tools

### pgAdmin

- Open `http://localhost:5050`
- Sign in with the values in `.env`
- Add a new PostgreSQL server:
  - Host: `postgres` if using pgAdmin in Docker
  - Or `localhost` if using a desktop client
  - Port: `5432`
  - Username and password from `.env`

### Azure Data Studio

Use a PostgreSQL connection with:

- Server: `localhost`
- Port: `5432`
- User: value from `.env`
- Password: value from `.env`

## Run A Lesson

Example for the concurrency lesson:

```bash
cd 40-Advanced-EFCore/01.ConcurrencyConflicts
dotnet restore
dotnet run
```

Then open the URL printed by the app.

## Inspect Query Performance

In pgAdmin or Azure Data Studio, students should try:

```sql
EXPLAIN ANALYZE
SELECT * FROM "Products"
ORDER BY "Name";
```

Compare that output with the SQL shown by the lesson pages and console logs.

## Reset Data

To reset the shared PostgreSQL volume:

```bash
cd 40-Advanced-EFCore
docker compose down -v
docker compose up -d
```

This removes persisted container data but leaves the lesson code untouched.
