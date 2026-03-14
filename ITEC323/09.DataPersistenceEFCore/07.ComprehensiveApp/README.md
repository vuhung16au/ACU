# 07.ComprehensiveApp

Advanced ASP.NET Core Razor Pages sample that integrates the Week 9 concepts into one PostgreSQL-backed store management app.

## Demo

![Comprehensive App demo](images/comprehensive-app.gif)


## Learning Objectives

- Combine secure PostgreSQL configuration with a realistic EF Core app.
- Model one-to-many relationships across categories, products, customers, orders, and order items.
- Use Razor Pages CRUD, tag helpers, and model binding in a larger workflow.
- Demonstrate change tracking by creating orders and updating stock in one save operation.
- Use LINQ for reporting, grouping, aggregations, and operational dashboards.

## What This Project Demonstrates

- EF Core 10 with PostgreSQL via Npgsql
- `DbContext` registered with dependency injection
- Secure connection strings through User Secrets and environment variables
- Related data loading with `Include()` and `ThenInclude()`
- Tag-helper forms for products, categories, and orders
- Seeded relational data for immediate exploration
- LINQ reports for revenue, top products, top customers, and stock alerts

## Application Areas

- `/Products`: scaffold-style CRUD for products with category dropdowns
- `/Categories`: manage lookup data and inspect related products
- `/Orders`: create and review orders while decrementing stock
- `/Reports`: grouped analytics driven by LINQ queries

## Project Structure

```text
07.ComprehensiveApp/
├── Data/
│   ├── AppDbContext.cs
│   └── DemoDataSeeder.cs
├── Models/
│   ├── Category.cs
│   ├── Customer.cs
│   ├── Order.cs
│   ├── OrderItem.cs
│   └── Product.cs
├── Migrations/
├── Pages/
│   ├── Categories/
│   ├── Orders/
│   ├── Products/
│   ├── Reports/
│   ├── Index.cshtml
│   ├── Privacy.cshtml
│   └── Shared/
├── docs/
│   └── Key-Takeaways.md
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
# From 09.DataPersistenceEFCore/07.ComprehensiveApp

docker compose up -d
dotnet user-secrets set "ConnectionStrings:DefaultConnection" "Host=localhost;Port=5434;Database=comprehensive_app_db;Username=comprehensive_app_user;Password=change_me_in_local_secrets"
dotnet ef database update
dotnet run
```

## Why This Matters

This project shows what a small but complete EF Core application looks like when the earlier concepts are combined instead of taught in isolation.
