# Quick Start

## Prerequisites

- .NET 10 SDK installed
- Docker Desktop (or Docker Engine + Compose plugin)

## Run The Project

```bash
cd 09.DataPersistenceEFCore/04.MongoDBDocker

# Start MongoDB container
docker compose up -d

# Restore dependencies and run web app
dotnet restore
dotnet run
```

Open the URL shown in the terminal (typically `http://localhost:5124`).

## Optional: Store Connection In User Secrets

```bash
dotnet user-secrets init
dotnet user-secrets set "MongoDb:ConnectionString" "mongodb://mongodb_docker_user:mongodb_docker_password@localhost:27017/mongodb_docker_db?authSource=admin"
```

## Pages To Check

- `/` module home page and learning goals
- `/Products` product list
- `/Products/Create` insert document
- `/Products/Edit/{id}` update document
- `/Products/Details/{id}` read one document
- `/Products/Delete/{id}` delete document

## Build Check

```bash
dotnet build
```

## Troubleshooting

### MongoDB container not running

```bash
docker compose ps
docker compose logs mongodb
```

### Clean reset

```bash
docker compose down -v
docker compose up -d
```

### Connect with mongosh

```bash
mongosh "mongodb://mongodb_docker_user:mongodb_docker_password@localhost:27017/?authSource=admin"
```
