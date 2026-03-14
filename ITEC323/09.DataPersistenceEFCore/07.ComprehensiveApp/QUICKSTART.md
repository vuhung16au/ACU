# 07.ComprehensiveApp Quick Start

Follow these steps to run the comprehensive Week 9 sample with PostgreSQL, secure configuration, and seeded store data.

## 1. Prerequisites

- .NET SDK 10 or later
- Docker Desktop
- EF Core CLI tools available as `dotnet ef`

## 2. Move into the Project Folder

```bash
cd 09.DataPersistenceEFCore/07.ComprehensiveApp
```

## 3. Create Local Docker Environment Values

```bash
cp .env.example .env
```

The default `.env.example` already uses port `5434` to avoid conflicts with the earlier PostgreSQL exercises.

## 4. Start PostgreSQL

```bash
docker compose up -d
```

## 5. Store the Connection String Securely

```bash
dotnet user-secrets set "ConnectionStrings:DefaultConnection" "Host=localhost;Port=5434;Database=comprehensive_app_db;Username=comprehensive_app_user;Password=change_me_in_local_secrets"
```

## 6. Apply Migrations

```bash
dotnet ef database update
```

## 7. Run the App

```bash
dotnet run
```

## 8. Verify the Main Flows

- Open `/` for the dashboard summary.
- Open `/Products` and `/Categories` to inspect related data.
- Open `/Orders/Create` to place a new order and reduce stock.
- Open `/Reports` to review LINQ-based analytics.

## 9. Optional Production-Style Override

```bash
export ConnectionStrings__DefaultConnection="Host=prod-db;Port=5432;Database=comprehensive_app_db;Username=prod_user;Password=prod_secret;SslMode=Require"
```

## Troubleshooting

- If the app says the connection string is missing, rerun the `dotnet user-secrets set ...` command.
- If Docker reports a port conflict, change `POSTGRES_PORT` in `.env` and update the connection string secret to match.
- If the database contains stale tables from a previous run, recreate the container volume and rerun migrations.
- If category or product deletes are blocked, remove dependent data first. The app intentionally enforces relational integrity.
