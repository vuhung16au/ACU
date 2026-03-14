# 05.ScaffoldingCRUD

Beginner-friendly ASP.NET Core Razor Pages project that demonstrates scaffolded CRUD generation and customization.

## Demo

![Scaffolding CRUD Demo](images/scaffolding-CRUD.gif)

## Learning Objectives

- Install and run `dotnet-aspnet-codegenerator`.
- Generate Razor Pages CRUD screens from entity models.
- Understand scaffolded PageModel and view structure.
- Customize generated pages for related data (Product -> Category).
- Use `Include()` for eager loading of related records.

## What This Project Demonstrates

- EF Core 10 + SQLite provider
- Scaffolding package setup in project dependencies
- Product and Category relationship (one category to many products)
- Category dropdown binding in Create/Edit pages
- Related data display in Index/Details/Delete pages

## Project Structure

```text
05.ScaffoldingCRUD/
├── Data/
│   └── AppDbContext.cs
├── Models/
│   ├── Category.cs
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
├── Program.cs
├── appsettings.json
├── QUICKSTART.md
└── README.md
```

## Key Commands

```bash
# From 09.DataPersistenceEFCore/05.ScaffoldingCRUD

dotnet restore
dotnet ef database update
dotnet run
```

## Why This Matters

Scaffolding accelerates CRUD delivery and gives students a concrete baseline they can inspect and improve.
