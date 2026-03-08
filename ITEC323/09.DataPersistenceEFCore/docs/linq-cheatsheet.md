# LINQ Cheatsheet

## What is LINQ?

**LINQ** = **Language Integrated Query**

Write database queries using C# syntax instead of SQL strings.

```csharp
// SQL way (string)
var sql = "SELECT * FROM Products WHERE Price < 50 ORDER BY Name";

// LINQ way (C# code)
var products = _context.Products
    .Where(p => p.Price < 50)
    .OrderBy(p => p.Name)
    .ToList();
```

---

## Basic Syntax

### Method Syntax (Recommended)

```csharp
var result = _context.Products
    .Where(p => p.Price < 100)
    .OrderBy(p => p.Name)
    .ToList();
```

### Query Syntax (Alternative)

```csharp
var result = from p in _context.Products
             where p.Price < 100
             orderby p.Name
             select p;
```

**In this course, we use Method Syntax** - more flexible and common in industry.

---

## Essential Operations

### Retrieve All Records

```csharp
// Get all products
var products = await _context.Products.ToListAsync();

// SQL: SELECT * FROM Products
```

### Filter with Where

```csharp
// Single condition
var cheap = await _context.Products
    .Where(p => p.Price < 50)
    .ToListAsync();

// Multiple conditions (AND)
var filtered = await _context.Products
    .Where(p => p.Price < 100 && p.Stock > 0)
    .ToListAsync();

// OR condition
var either = await _context.Products
    .Where(p => p.Price < 20 || p.Price > 100)
    .ToListAsync();

// Contains (LIKE)
var matched = await _context.Products
    .Where(p => p.Name.Contains("Widget"))
    .ToListAsync();
// SQL: WHERE Name LIKE '%Widget%'

// StartsWith
var starts = await _context.Products
    .Where(p => p.Name.StartsWith("Pro"))
    .ToListAsync();
// SQL: WHERE Name LIKE 'Pro%'
```

### Sort with OrderBy

```csharp
// Ascending
var sortedAsc = await _context.Products
    .OrderBy(p => p.Price)
    .ToListAsync();

// Descending
var sortedDesc = await _context.Products
    .OrderByDescending(p => p.Price)
    .ToListAsync();

// Multiple sort (ThenBy)
var multiSort = await _context.Products
    .OrderBy(p => p.CategoryId)
    .ThenByDescending(p => p.Price)
    .ToListAsync();
// SQL: ORDER BY CategoryId, Price DESC
```

### Get Single Record

```csharp
// By primary key (fastest)
var product = await _context.Products.FindAsync(5);

// First match (throws if not found)
var first = await _context.Products
    .FirstAsync(p => p.Price < 50);

// First or null
var firstOrNull = await _context.Products
    .FirstOrDefaultAsync(p => p.Price < 50);

// Single (throws if 0 or more than 1)
var single = await _context.Products
    .SingleAsync(p => p.Id == 5);

// Single or null
var singleOrNull = await _context.Products
    .SingleOrDefaultAsync(p => p.Id == 5);
```

**Best Practice:**
- Use `FindAsync()` for primary key lookups
- Use `FirstOrDefaultAsync()` for other queries

---

## Pagination

```csharp
int pageSize = 10;
int pageNumber = 2; // 0-based or 1-based, your choice

var page = await _context.Products
    .OrderBy(p => p.Id)
    .Skip((pageNumber - 1) * pageSize)
    .Take(pageSize)
    .ToListAsync();

// Example: Page 2 of 10 items
// Skip(10).Take(10) → items 11-20
```

**Always use OrderBy before Skip/Take!**

---

## Projection (Select specific columns)

```csharp
// Select all properties (default)
var products = await _context.Products.ToListAsync();

// Select specific columns
var names = await _context.Products
    .Select(p => p.Name)
    .ToListAsync();
// Returns List<string>

// Select multiple columns (anonymous type)
var summary = await _context.Products
    .Select(p => new
    {
        p.Id,
        p.Name,
        p.Price
    })
    .ToListAsync();

// Select to DTO/ViewModel
var viewModels = await _context.Products
    .Select(p => new ProductViewModel
    {
        Name = p.Name,
        Price = p.Price,
        CategoryName = p.Category.Name
    })
    .ToListAsync();
```

**Benefit:** Only fetches needed columns (performance!)

---

## Joins & Related Data

### Eager Loading (Include)

```csharp
// Load products with their categories
var productsWithCategories = await _context.Products
    .Include(p => p.Category)
    .ToListAsync();

// Multiple includes
var full = await _context.Products
    .Include(p => p.Category)
    .Include(p => p.Supplier)
    .ToListAsync();

// Nested includes (ThenInclude)
var orders = await _context.Orders
    .Include(o => o.OrderItems)
        .ThenInclude(oi => oi.Product)
    .ToListAsync();
```

**Without Include, navigation properties are null!**

### Filtering Related Data

```csharp
// Get products from specific category
var electronics = await _context.Products
    .Include(p => p.Category)
    .Where(p => p.Category.Name == "Electronics")
    .ToListAsync();

// Or more efficiently:
var electronics = await _context.Products
    .Where(p => p.CategoryId == electronicsId)
    .ToListAsync();
```

---

## Aggregations

### Count

```csharp
// Count all
var totalCount = await _context.Products.CountAsync();

// Count with condition
var expensiveCount = await _context.Products
    .CountAsync(p => p.Price > 100);
```

### Sum

```csharp
// Sum of prices
var totalValue = await _context.Products
    .SumAsync(p => p.Price);

// Sum with condition
var totalStock = await _context.Products
    .Where(p => p.CategoryId == 1)
    .SumAsync(p => p.Stock);
```

### Average

```csharp
var avgPrice = await _context.Products
    .AverageAsync(p => p.Price);
```

### Min / Max

```csharp
var cheapest = await _context.Products.MinAsync(p => p.Price);
var mostExpensive = await _context.Products.MaxAsync(p => p.Price);
```

### Any (Exists)

```csharp
// Check if any products exist
bool hasProducts = await _context.Products.AnyAsync();

// Check with condition
bool hasExpensive = await _context.Products
    .AnyAsync(p => p.Price > 1000);
```

### All

```csharp
// Check if all products meet condition
bool allInStock = await _context.Products
    .AllAsync(p => p.Stock > 0);
```

---

## Grouping

```csharp
// Group by category
var grouped = await _context.Products
    .GroupBy(p => p.CategoryId)
    .Select(g => new
    {
        CategoryId = g.Key,
        Count = g.Count(),
        AveragePrice = g.Average(p => p.Price),
        TotalValue = g.Sum(p => p.Price * p.Stock)
    })
    .ToListAsync();

// SQL equivalent:
// SELECT CategoryId, 
//        COUNT(*), 
//        AVG(Price), 
//        SUM(Price * Stock)
// FROM Products
// GROUP BY CategoryId
```

---

## Distinct

```csharp
// Get unique category IDs
var categoryIds = await _context.Products
    .Select(p => p.CategoryId)
    .Distinct()
    .ToListAsync();
```

---

## Conditional Queries (Dynamic Filtering)

```csharp
public async Task<List<Product>> SearchProducts(
    string? searchTerm,
    int? categoryId,
    decimal? maxPrice)
{
    var query = _context.Products.AsQueryable();
    
    if (!string.IsNullOrEmpty(searchTerm))
    {
        query = query.Where(p => p.Name.Contains(searchTerm));
    }
    
    if (categoryId.HasValue)
    {
        query = query.Where(p => p.CategoryId == categoryId.Value);
    }
    
    if (maxPrice.HasValue)
    {
        query = query.Where(p => p.Price <= maxPrice.Value);
    }
    
    return await query.OrderBy(p => p.Name).ToListAsync();
}
```

---

## Performance Tips

### Use AsNoTracking for Read-Only

```csharp
// For display-only queries (faster)
var products = await _context.Products
    .AsNoTracking()
    .ToListAsync();

// EF won't track changes (saves memory)
```

### Avoid N+1 Queries

```csharp
// ❌ Bad - N+1 queries
var products = await _context.Products.ToListAsync();
foreach (var p in products)
{
    var categoryName = p.Category.Name; // Separate query each time!
}

// ✅ Good - 1 query
var products = await _context.Products
    .Include(p => p.Category)
    .ToListAsync();
```

### Limit Results Early

```csharp
// ❌ Bad - fetches all then filters
var all = await _context.Products.ToListAsync();
var filtered = all.Where(p => p.Price < 50).ToList();

// ✅ Good - filters in database
var filtered = await _context.Products
    .Where(p => p.Price < 50)
    .ToListAsync();
```

---

## Common Patterns

### Pattern 1: Search & Filter

```csharp
public async Task<List<Product>> GetProducts(
    string? search = null,
    int? categoryId = null,
    string? sortBy = "name",
    bool descending = false)
{
    var query = _context.Products
        .Include(p => p.Category)
        .AsQueryable();
    
    // Filter
    if (!string.IsNullOrEmpty(search))
        query = query.Where(p => p.Name.Contains(search));
    
    if (categoryId.HasValue)
        query = query.Where(p => p.CategoryId == categoryId);
    
    // Sort
    query = sortBy?.ToLower() switch
    {
        "price" => descending ? query.OrderByDescending(p => p.Price)
                              : query.OrderBy(p => p.Price),
        "name" => descending ? query.OrderByDescending(p => p.Name)
                             : query.OrderBy(p => p.Name),
        _ => query.OrderBy(p => p.Name)
    };
    
    return await query.ToListAsync();
}
```

### Pattern 2: Paginated Results

```csharp
public async Task<PagedResult<Product>> GetPagedProducts(
    int page = 1,
    int pageSize = 10)
{
    var query = _context.Products.OrderBy(p => p.Id);
    
    var total = await query.CountAsync();
    var items = await query
        .Skip((page - 1) * pageSize)
        .Take(pageSize)
        .ToListAsync();
    
    return new PagedResult<Product>
    {
        Items = items,
        TotalCount = total,
        Page = page,
        PageSize = pageSize
    };
}
```

### Pattern 3: Exists Check Before Update

```csharp
public async Task<bool> UpdateProductPrice(int id, decimal newPrice)
{
    var product = await _context.Products.FindAsync(id);
    
    if (product == null)
        return false;
    
    product.Price = newPrice;
    await _context.SaveChangesAsync();
    return true;
}
```

---

## SQL to LINQ Translation

| SQL | LINQ |
|-----|------|
| `SELECT *` | `.ToList()` |
| `WHERE` | `.Where()` |
| `ORDER BY` | `.OrderBy()` / `.OrderByDescending()` |
| `TOP N` | `.Take(N)` |
| `DISTINCT` | `.Distinct()` |
| `COUNT(*)` | `.Count()` |
| `SUM` | `.Sum()` |
| `AVG` | `.Average()` |
| `MIN` / `MAX` | `.Min()` / `.Max()` |
| `GROUP BY` | `.GroupBy()` |
| `INNER JOIN` | `.Include()` or `.Join()` |
| `LIKE '%text%'` | `.Contains("text")` |
| `LIKE 'text%'` | `.StartsWith("text")` |
| `OFFSET / FETCH` | `.Skip().Take()` |

---

## Debugging LINQ Queries

### View Generated SQL

```csharp
// Enable logging in Program.cs:
builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseSqlite(connectionString)
           .LogTo(Console.WriteLine, LogLevel.Information));

// Or use ToQueryString():
var query = _context.Products.Where(p => p.Price < 50);
var sql = query.ToQueryString();
Console.WriteLine(sql);
```

---

## Common Mistakes

### ❌ Forgetting ToList/ToListAsync

```csharp
// ❌ Wrong - returns IQueryable, not executed
var products = _context.Products.Where(p => p.Price < 50);

// ✅ Correct - executes query
var products = await _context.Products
    .Where(p => p.Price < 50)
    .ToListAsync();
```

### ❌ Using .Result instead of await

```csharp
// ❌ Bad - blocks thread
var products = _context.Products.ToListAsync().Result;

// ✅ Good - async all the way
var products = await _context.Products.ToListAsync();
```

### ❌ Modifying in foreach (use update pattern)

```csharp
// ❌ Inefficient
foreach (var p in products)
{
    p.Price *= 1.1m;
    await _context.SaveChangesAsync(); // DON'T save in loop!
}

// ✅ Better
foreach (var p in products)
{
    p.Price *= 1.1m;
}
await _context.SaveChangesAsync(); // Save once after all changes
```

---

## Quick Reference Card

```csharp
// Retrieve
_context.Products.ToListAsync()
_context.Products.FindAsync(id)
_context.Products.FirstOrDefaultAsync(p => ...)

// Filter
.Where(p => p.Price < 50)
.Where(p => p.Name.Contains("text"))

// Sort
.OrderBy(p => p.Name)
.OrderByDescending(p => p.Price)

// Pagination
.Skip(10).Take(10)

// Aggregations
.CountAsync()
.SumAsync(p => p.Price)
.AverageAsync(p => p.Price)
.AnyAsync(p => ...)

// Related Data
.Include(p => p.Category)
.ThenInclude(c => c.Parent)

// Performance
.AsNoTracking()
.Select(p => new { ... })

// CRUD
_context.Add(entity)
_context.Update(entity)
_context.Remove(entity)
await _context.SaveChangesAsync()
```

---

## Practice Projects

✅ [01.BasicEFCore](../01.BasicEFCore/) - Simple LINQ queries  
✅ [03.LinqQueries](../03.LinqQueries/) - Advanced LINQ patterns  
✅ [07.ComprehensiveApp](../07.ComprehensiveApp/) - Real-world usage  

---

## Next Steps

- Read [ORM Basics](orm-basics.md) for foundational concepts
- Check [Migrations Guide](migrations-guide.md) for schema management
- Practice queries in [03.LinqQueries](../03.LinqQueries/)

---

## Key Takeaways

✅ LINQ writes queries in C# instead of SQL strings  
✅ Always use `await` with `ToListAsync()`, `FirstOrDefaultAsync()`, etc.  
✅ Use `.Include()` to load related data (avoid N+1)  
✅ Use `.AsNoTracking()` for read-only queries  
✅ Filter in database with `.Where()`, not after `.ToList()`  

**Remember:** LINQ is type-safe, IntelliSense-friendly, and protected from SQL injection!
