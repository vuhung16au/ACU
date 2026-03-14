# 04.MongoDBDocker

Beginner-friendly ASP.NET Core Razor Pages project that demonstrates NoSQL persistence using MongoDB in Docker.

## Demo

![MongoDB Docker Demo](images/mongodb-docker.gif)


## Learning Objectives

- Run [MongoDB](https://hub.docker.com/_/mongo/) 7 with Docker Compose.
- Configure and use the MongoDB C# Driver in ASP.NET Core.
- Perform async CRUD operations on MongoDB documents.
- Understand the NoSQL document approach compared to relational storage.
- Apply connection-string security practices with User Secrets.

## What This Project Demonstrates

- `MongoDB.Driver` package integration
- Product document model with ObjectId mapping
- Repository pattern for MongoDB access
- Razor Pages CRUD workflow for `/Products`
- Docker Compose MongoDB service with persistent volume

## Project Structure

```text
04.MongoDBDocker/
├── Data/
│   ├── IProductRepository.cs
│   ├── MongoDbSettings.cs
│   └── MongoProductRepository.cs
├── Models/
│   └── Product.cs
├── Pages/
│   ├── Products/
│   ├── Index.cshtml
│   ├── Privacy.cshtml
│   └── Shared/
├── docs/
│   └── Key-Takeaways.md
├── wwwroot/
├── docker-compose.yml
├── Program.cs
├── appsettings.json
├── QUICKSTART.md
└── README.md
```

## Key Commands

```bash
# From 09.DataPersistenceEFCore/04.MongoDBDocker

docker compose up -d
dotnet restore
dotnet run
```

## Why This Matters

This project gives you hands-on practice with document databases and highlights how CRUD workflows differ from EF Core relational patterns.
