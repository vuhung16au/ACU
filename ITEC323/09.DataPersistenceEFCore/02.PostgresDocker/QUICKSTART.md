# Quick Start

## Prerequisites

- .NET 10 SDK installed
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) (or Docker Engine + Compose plugin)
- `dotnet ef` tool installed

Install EF CLI tool if needed:

```bash
dotnet tool install --global dotnet-ef
```

## Run The Project

```bash
cd 09.DataPersistenceEFCore/02.PostgresDocker

# Start PostgreSQL container
docker compose up -d

# Restore packages and apply migrations
dotnet restore
dotnet ef database update

# Run web app
dotnet run
```

Open the URL shown in the terminal (typically `http://localhost:5124`).

## Optional: Store Connection String In User Secrets

```bash
dotnet user-secrets init
dotnet user-secrets set "ConnectionStrings:DefaultConnection" "Host=localhost;Port=5432;Database=postgres_docker_db;Username=postgres_docker_user;Password=postgres_docker_password"
```

## Pages To Check

- `/` module home page and learning goals
- `/Products` product list (read)
- `/Products/Create` create form
- `/Products/Edit/{id}` update form
- `/Products/Details/{id}` record details
- `/Products/Delete/{id}` delete confirmation

## Build Check

```bash
dotnet build
```

## Migration Workflow Reminder

When you change `Models/Product.cs`:

```bash
dotnet ef migrations add DescribeYourChange
dotnet ef database update
```

## Troubleshooting

### `dotnet ef` not found

```bash
export PATH="$PATH:$HOME/.dotnet/tools"
dotnet ef --version
```

### PostgreSQL container not running

```bash
docker compose ps
docker compose logs postgres
```

### Clean reset

```bash
docker compose down -v
docker compose up -d
dotnet ef database update
```
