# ORM Basics Guide

## What is an ORM?

**ORM** = **Object-Relational Mapper**

A software layer that translates between object-oriented code (C#, Java, Python) and relational databases (SQL).

### The Problem ORMs Solve

**Without ORM (Raw SQL):**
```csharp
var command = "SELECT * FROM Products WHERE Price < 50";
// Problems:
// ❌ Typos not caught until runtime
// ❌ SQL injection vulnerabilities
// ❌ No IntelliSense/autocomplete
// ❌ Manual type conversion
```

**With ORM (EF Core):**
```csharp
var products = _context.Products
    .Where(p => p.Price < 50)
    .ToList();
// Benefits:
// ✅ Compile-time type checking
// ✅ IntelliSense support
// ✅ SQL injection prevention
// ✅ Automatic type conversion
```

---

## How ORMs Work

### 1. Code-First Workflow

```
C# Classes → ORM (EF Core) → Database Tables
   ↓              ↓                ↓
Product.cs → Migrations → Products table
```

### 2. The Translation Process

**C# LINQ Query:**
```csharp
var result = _context.Products
    .Where(p => p.Price < 100)
    .OrderBy(p => p.Name)
    .Take(10);
```

**Generated SQL:**
```sql
SELECT TOP(10) *
FROM Products
WHERE Price < 100
ORDER BY Name;
```

EF Core automatically translates LINQ to optimized SQL!

---

## Core EF Core Concepts

### DbContext

The "bridge" between your C# code and the database.

```csharp
public class AppDbContext : DbContext
{
    public DbSet<Product> Products { get; set; }
    public DbSet<Category> Categories { get; set; }
}
```

**Responsibilities:**
- Tracks entity changes
- Generates SQL commands
- Manages database connections
- Handles transactions

### DbSet<T>

Represents a database table as a C# collection.

```csharp
public DbSet<Product> Products { get; set; }
// Think of it as: List<Product> but connected to database
```

**Operations:**
- `Add()` → INSERT
- `Update()` → UPDATE
- `Remove()` → DELETE
- Query with LINQ → SELECT

### Entities (Models)

C# classes that map to database tables.

```csharp
public class Product  // → Products table
{
    public int Id { get; set; }           // → Id column (Primary Key)
    public string Name { get; set; }      // → Name column (nvarchar)
    public decimal Price { get; set; }    // → Price column (decimal)
    public DateTime Created { get; set; } // → Created column (datetime2)
}
```

---

## Key Benefits of ORMs

### 1. Type Safety

```csharp
// ❌ Raw SQL - error not caught until runtime
var sql = "SELECT * FROM Prodcuts";  // Typo!

// ✅ EF Core - caught at compile time
var products = _context.Prodcuts;  // Won't compile!
```

### 2. SQL Injection Prevention

```csharp
// ❌ Raw SQL - vulnerable to injection
var sql = $"SELECT * FROM Users WHERE Name = '{userName}'";
// If userName = "'; DROP TABLE Users; --" → disaster!

// ✅ EF Core - automatically parameterized
var user = _context.Users.FirstOrDefault(u => u.Name == userName);
// Safe! EF Core uses parameterized queries
```

### 3. Productivity

```csharp
// Instead of writing:
// - Connection management
// - SQL query strings
// - Manual data readers
// - Type conversions

// Just write:
var products = await _context.Products.ToListAsync();
```

### 4. Database Portability

Change database providers with minimal code changes:

```csharp
// SQLite
options.UseSqlite(connectionString);

// PostgreSQL
options.UseNpgsql(connectionString);

// SQL Server
options.UseSqlServer(connectionString);

// Same C# code works with all!
```

---

## Change Tracking

EF Core automatically tracks changes to entities.

```csharp
// 1. Fetch entity
var product = await _context.Products.FindAsync(1);

// 2. Modify it
product.Price = 99.99m;

// 3. Save - EF Core knows it changed!
await _context.SaveChangesAsync();
// Generates: UPDATE Products SET Price = 99.99 WHERE Id = 1
```

**Entity States:**
- `Unchanged` - Fetched but not modified
- `Added` - New entity, will INSERT
- `Modified` - Changed properties, will UPDATE
- `Deleted` - Marked for deletion, will DELETE

---

## Common C# to SQL Mappings

| C# Type | SQL Type (SQL Server) | SQL Type (PostgreSQL) |
|---------|----------------------|----------------------|
| `int` | `int` | `integer` |
| `string` | `nvarchar(max)` | `text` |
| `decimal` | `decimal(18,2)` | `numeric(18,2)` |
| `bool` | `bit` | `boolean` |
| `DateTime` | `datetime2` | `timestamp` |
| `Guid` | `uniqueidentifier` | `uuid` |

**Control with annotations:**
```csharp
[MaxLength(100)]
public string Name { get; set; }
// → nvarchar(100) instead of max
```

---

## Primary Keys

EF Core automatically detects primary keys:

```csharp
public class Product
{
    // Option 1: Property named "Id"
    public int Id { get; set; }  // ✅ Auto-detected
    
    // Option 2: Property named "<ClassName>Id"
    public int ProductId { get; set; }  // ✅ Auto-detected
    
    // Option 3: Explicit [Key] attribute
    [Key]
    public int ProductCode { get; set; }  // ✅ Explicit
}
```

**Auto-increment:**
- `int` primary keys automatically become IDENTITY in SQL

---

## Relationships

### One-to-Many

```csharp
public class Category
{
    public int Id { get; set; }
    public string Name { get; set; }
    
    // Navigation property (one category has many products)
    public List<Product> Products { get; set; }
}

public class Product
{
    public int Id { get; set; }
    public string Name { get; set; }
    
    // Foreign key
    public int CategoryId { get; set; }
    
    // Navigation property (one product belongs to one category)
    public Category Category { get; set; }
}
```

### Loading Related Data

```csharp
// Eager Loading - load related data immediately
var products = _context.Products
    .Include(p => p.Category)
    .ToList();

// Without Include - Category will be null!
var products = _context.Products.ToList();
```

---

## When to Use ORMs

### ✅ Use ORMs When:
- Building CRUD applications
- Need rapid development
- Team familiar with OOP
- Database schema changes frequently
- Need database portability
- Want compile-time safety

### ⚠️ Consider Raw SQL When:
- Complex reporting queries
- Stored procedures required
- Performance-critical operations
- Database-specific features needed
- Simple scripts or utilities

**Note:** EF Core *allows* raw SQL when needed:
```csharp
var products = _context.Products
    .FromSqlRaw("SELECT * FROM Products WHERE Price < {0}", 50)
    .ToList();
```

---

## Performance Considerations

### Avoid N+1 Queries

```csharp
// ❌ Bad - executes 1 + N queries
var products = _context.Products.ToList();
foreach (var p in products)
{
    var categoryName = p.Category.Name; // Separate query for each!
}

// ✅ Good - executes 1 query
var products = _context.Products
    .Include(p => p.Category)
    .ToList();
```

### Use AsNoTracking for Read-Only

```csharp
// ✅ Faster for read-only scenarios
var products = _context.Products
    .AsNoTracking()
    .ToList();
// No change tracking overhead
```

### Projection

```csharp
// ❌ Fetches all columns
var products = _context.Products.ToList();

// ✅ Fetches only needed columns
var productNames = _context.Products
    .Select(p => new { p.Id, p.Name })
    .ToList();
```

---

## Popular .NET ORMs

| ORM | Pros | Cons |
|-----|------|------|
| **Entity Framework Core** | Full-featured, Microsoft-backed, great tooling | Can be complex, learning curve |
| **Dapper** | Very fast, lightweight, simple | No change tracking, manual mapping |
| **NHibernate** | Mature, powerful | Complex configuration, older API |

**In this course, we use EF Core** - the standard ORM for modern .NET applications.

---

## Next Steps

- Read [Migrations Guide](migrations-guide.md) to learn schema management
- Check [LINQ Cheatsheet](linq-cheatsheet.md) for query patterns
- Practice with [01.BasicEFCore](../01.BasicEFCore/) project

---

## Key Takeaways

✅ ORMs translate C# code to SQL  
✅ EF Core provides type safety and SQL injection protection  
✅ DbContext manages database connection and change tracking  
✅ Entities (C# classes) map to database tables  
✅ Use `.Include()` to load related data  
✅ Migrations manage schema changes  

**Remember:** ORMs make database operations safer and more productive!
