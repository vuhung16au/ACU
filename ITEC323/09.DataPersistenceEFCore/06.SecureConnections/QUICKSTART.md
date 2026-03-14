# Quick Start

## Prerequisites

- .NET 10 SDK installed
- Docker Desktop (or Docker Engine + Compose plugin)
- `dotnet ef` tool installed

Install EF CLI tool if needed:

```bash
dotnet tool install --global dotnet-ef
```

## Secure Setup

```bash
cd 09.DataPersistenceEFCore/06.SecureConnections

# 1. Create a local .env file for Docker
cp .env.example .env
# cat .env # Check values, especially POSTGRES_PASSWORD

# 2. Start PostgreSQL with values from .env
docker compose up -d

# 3. Initialize user secrets (safe for development)
dotnet user-secrets init

# 4. Store the app connection string outside the repo
dotnet user-secrets set "ConnectionStrings:DefaultConnection" "Host=localhost;Port=5433;Database=secure_connections_db;Username=secure_connections_user;Password=change_me_in_local_secrets"

# 5. Restore, migrate, and run
dotnet restore
dotnet ef database update
dotnet run
```

Open the URL shown in the terminal (typically `http://localhost:5124`).

## Production-Style Override

```bash
export ConnectionStrings__DefaultConnection="Host=prod-db;Port=5432;Database=secure_connections_db;Username=prod_user;Password=prod_secret;SslMode=Require"
```

## Pages To Check

- `/` module home page and security notes
- `/Products` CRUD pages using secure config
- `/Privacy` summary of User Secrets and env vars

## Build Check

```bash
dotnet build
```

## Troubleshooting

### Missing connection string at startup

```bash
dotnet user-secrets list
```

### PostgreSQL container not running

```bash
docker compose ps
docker compose logs postgres
```

### Reset secrets

```bash
dotnet user-secrets clear
```
