# 06.SecureConnections

Beginner-friendly ASP.NET Core Razor Pages project that demonstrates secure database configuration using User Secrets and environment variables.

## Demo 

![Secure Connections demo](images/secure-connections-small.gif)




## Learning Objectives

- Configure PostgreSQL without committing connection strings.
- Initialize and use User Secrets in development.
- Use environment variables for production-style configuration.
- Understand ASP.NET Core configuration precedence.
- Keep the same EF Core CRUD workflow while improving security.

## What This Project Demonstrates

- EF Core 10 + Npgsql provider
- PostgreSQL in Docker using `.env` variables
- `UserSecretsId` configured in project file
- Secure runtime lookup of `ConnectionStrings:DefaultConnection`
- Existing CRUD pages working without secrets in `appsettings.json`


## Project Structure

```text
06.SecureConnections/
├── Data/
│   └── AppDbContext.cs
├── Models/
│   └── Product.cs
├── Migrations/
├── Pages/
│   ├── Products/
│   ├── Index.cshtml
│   ├── Privacy.cshtml
│   └── Shared/
├── docs/
│   └── Key-Takeaways.md
├── wwwroot/
├── .env.example
├── .gitignore
├── docker-compose.yml
├── Program.cs
├── appsettings.json
├── QUICKSTART.md
└── README.md
```

## Key Commands

```bash
# From 09.DataPersistenceEFCore/06.SecureConnections

docker compose up -d
dotnet user-secrets set "ConnectionStrings:DefaultConnection" "Host=localhost;Port=5433;Database=secure_connections_db;Username=secure_connections_user;Password=change_me_in_local_secrets"
dotnet ef database update
dotnet run
```

## Why This Matters

This project teaches the correct habit: source code should define structure, while secrets live outside the repository.
