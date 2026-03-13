# 03.LinqQueries

Beginner-friendly ASP.NET Core Razor Pages project focused on practical LINQ query patterns with EF Core.

## Demo

![LINQ Queries Demo](images/linq-queries.gif)


## Learning Objectives

- Practice LINQ filtering, sorting, and projection patterns.
- Use `Include` to load related data.
- Build aggregate queries with `GroupBy`, `Count`, `Sum`, and `Average`.
- Apply pagination with `Skip` and `Take`.
- Understand why LINQ is type-safe and easier to refactor than SQL strings.

## What This Project Demonstrates

- EF Core 10 with `Microsoft.EntityFrameworkCore.InMemory`
- Seeded demo data for repeatable query exercises
- Razor Pages CRUD screens for products
- Dedicated LINQ Explorer page at `/LinqQueries`
- Query patterns: `Where`, `OrderBy`, `Include`, `Select`, `GroupBy`, `Any`, `Skip`, `Take`

## Project Structure

```text
03.LinqQueries/
├── Data/
│   ├── AppDbContext.cs
│   └── DemoDataSeeder.cs
├── Models/
│   ├── Product.cs
│   └── ProductReview.cs
├── Pages/
│   ├── LinqQueries/
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
# From 09.DataPersistenceEFCore/03.LinqQueries

dotnet restore
dotnet run
```

## Why This Matters

This project lets you focus on query thinking first. Because data is in-memory, there is no Docker or database setup overhead, so the learning loop is fast.
