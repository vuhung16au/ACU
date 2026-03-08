# Week 9: Data Persistence & Entity Framework Core

## Overview

Learn how to bridge the gap between C# code and databases using Object-Relational Mappers (ORMs). Move beyond raw SQL to write type-safe, compiler-checked database queries with Entity Framework Core.

**Key Goal:** Master database operations using C# code instead of SQL strings, ensuring compile-time safety and leveraging LINQ for powerful queries.

## Learning Objectives

By the end of this module, you will:

✅ Understand what ORMs are and why they matter  
✅ Define data models in C# that map to database tables  
✅ Use DbContext to interact with databases  
✅ Perform migrations to manage schema changes  
✅ Query data using LINQ instead of raw SQL  
✅ Implement CRUD operations with EF Core  
✅ Work with PostgreSQL using Docker  
✅ Work with MongoDB (NoSQL) using Docker  
✅ Secure connection strings (secrets.json, environment variables)  
✅ Use scaffolding tools for rapid CRUD development  
✅ Apply dependency injection in Razor Pages  
✅ Bind data to UI with tag helpers  

## Essential Questions

1. **What is an ORM and how does it protect against SQL injection?**
2. **How do database migrations work with `dotnet ef` commands?**
3. **How do we query data using LINQ instead of raw SQL?**
4. **Why should we never commit connection strings to version control?**

## Technology Stack

- **Backend:** ASP.NET Core 10.0 Razor Pages, Web API
- **Language:** C# 14
- **ORM:** Entity Framework Core 10
- **Databases:** PostgreSQL 16, MongoDB 7
- **Query Language:** LINQ (Language Integrated Query)
- **Container Platform:** Docker & Docker Compose
- **Scaffolding:** dotnet-aspnet-codegenerator
- **Security:** User Secrets, Environment Variables
- **Styling:** Bootstrap 5

## Projects

| Project | Focus | Difficulty |
|---------|-------|-----------|
| [01.BasicEFCore](01.BasicEFCore/) | EF Core intro, first migrations | ⭐ Beginner |
| [02.PostgresDocker](02.PostgresDocker/) | PostgreSQL with Docker | ⭐⭐ Easy |
| [03.LinqQueries](03.LinqQueries/) | Advanced LINQ operations | ⭐⭐⭐ Medium |
| [04.MongoDBDocker](04.MongoDBDocker/) | NoSQL with MongoDB | ⭐⭐ Easy |
| [05.ScaffoldingCRUD](05.ScaffoldingCRUD/) | Code generation tools | ⭐⭐ Easy |
| [06.SecureConnections](06.SecureConnections/) | Connection string security | ⭐⭐ Easy |
| [07.ComprehensiveApp](07.ComprehensiveApp/) | Full e-commerce system | ⭐⭐⭐⭐ Advanced |

## Documentation

Detailed guides in [docs/](docs/):

- [ORM Basics](docs/orm-basics.md) - What are ORMs and why use them
- [Migrations Guide](docs/migrations-guide.md) - Database schema version control
- [LINQ Cheatsheet](docs/linq-cheatsheet.md) - Common LINQ query patterns
- [Docker Setup](docs/docker-setup.md) - PostgreSQL and MongoDB with Docker
- [Security Best Practices](docs/security-best-practices.md) - Protecting sensitive data

## Quick Start

See [QUICKSTART.md](QUICKSTART.md) for setup instructions.

## Prerequisites

- Completed Week 1-8 modules
- .NET SDK 10.0 or later
- Docker Desktop installed
- Basic understanding of databases and SQL
- Familiarity with Razor Pages

## Key Concepts

### From Raw SQL to ORM

**Before (Raw SQL):**
```csharp
var command = "SELECT * FROM Products WHERE Price < 50";
// Typos not caught until runtime! 😱
```

**After (EF Core + LINQ):**
```csharp
var products = _context.Products
    .Where(p => p.Price < 50)
    .ToList();
// Compiler checks everything! ✅
```

### The Migration Workflow

```bash
# 1. Define models in C#
# 2. Create migration
dotnet ef migrations add InitialCreate

# 3. Apply to database
dotnet ef database update

# Database schema now matches your C# models!
```

### LINQ Query Examples

```csharp
// Filter
var cheapProducts = _context.Products.Where(p => p.Price < 50);

// Sort
var sorted = _context.Products.OrderBy(p => p.Name);

// Join (Include)
var productsWithCategories = _context.Products.Include(p => p.Category);

// Aggregate
var totalValue = _context.Products.Sum(p => p.Price * p.Stock);
```

## Common Patterns

### Dependency Injection Pattern

```csharp
public class IndexModel : PageModel
{
    private readonly AppDbContext _context;
    
    public IndexModel(AppDbContext context)
    {
        _context = context; // Injected automatically!
    }
    
    public async Task OnGetAsync()
    {
        Products = await _context.Products.ToListAsync();
    }
}
```

### Tag Helper Binding

```html
<!-- Tag helpers create proper HTML from C# models -->
<input asp-for="Product.Name" class="form-control" />
<span asp-validation-for="Product.Name"></span>
```

## Module Progression

1. **Start simple:** Basic EF Core with SQLite/LocalDB
2. **Add containers:** PostgreSQL and MongoDB with Docker
3. **Master queries:** Complex LINQ operations
4. **Secure data:** Proper connection string handling
5. **Accelerate development:** Scaffolding tools
6. **Build comprehensive:** Full application with all concepts

## Assessment Criteria

Students will be evaluated on:

- Correct EF Core setup and configuration
- Proper migration workflow
- Effective LINQ query writing
- Secure connection string management
- Clean code with dependency injection
- Proper error handling
- Documentation quality

---

**Next Steps:** Review [QUICKSTART.md](QUICKSTART.md) to set up your development environment, then start with [01.BasicEFCore](01.BasicEFCore/).
