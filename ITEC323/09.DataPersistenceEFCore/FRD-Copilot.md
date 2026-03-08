# Copilot Instructions for Week 9: Data Persistence & Entity Framework Core

## Overview

You are helping create educational materials for ITEC323 Week 9, which teaches students about Entity Framework Core, ORMs, LINQ, and database operations in ASP.NET Core Razor Pages. The target audience is first-time learners of data persistence and database operations in .NET.

## Project Context

- **Module:** Week 9 - Data Persistence & Entity Framework Core
- **Framework:** ASP.NET Core 10.0 Razor Pages
- **Language:** C# 14
- **ORM:** Entity Framework Core 10
- **Databases:** PostgreSQL 16 (Docker), MongoDB 7 (Docker), SQLite
- **Target Audience:** Students learning EF Core and database operations for the first time
- **Teaching Approach:** Code-First workflow with migrations

## Key Technologies

### Core Stack
- Entity Framework Core 10
- LINQ (Language Integrated Query)
- ASP.NET Core 10.0 Razor Pages
- PostgreSQL with Npgsql provider
- MongoDB with MongoDB.Driver
- Docker & Docker Compose
- User Secrets for security

### NuGet Packages
```xml
<PackageReference Include="Microsoft.EntityFrameworkCore" Version="10.0.*" />
<PackageReference Include="Microsoft.EntityFrameworkCore.Design" Version="10.0.*" />
<PackageReference Include="Microsoft.EntityFrameworkCore.Tools" Version="10.0.*" />
<PackageReference Include="Npgsql.EntityFrameworkCore.PostgreSQL" Version="10.0.*" />
<PackageReference Include="MongoDB.Driver" Version="2.25.*" />
<PackageReference Include="Microsoft.EntityFrameworkCore.Sqlite" Version="10.0.*" />
```

## Code Generation Guidelines

### 1. Entity Classes (Data Models)

**Always:**
- Use PascalCase for class and property names
- Include `Id` property as primary key (auto-detected by EF)
- Use appropriate C# data types (int, string, decimal, DateTime, bool)
- Add navigation properties for relationships
- Include XML documentation comments

**Example:**
```csharp
/// <summary>
/// Represents a product in the catalog
/// </summary>
public class Product
{
    /// <summary>
    /// Unique identifier for the product
    /// </summary>
    public int Id { get; set; }
    
    /// <summary>
    /// Product name
    /// </summary>
    [Required]
    [MaxLength(100)]
    public string Name { get; set; } = string.Empty;
    
    /// <summary>
    /// Product price in dollars
    /// </summary>
    [Range(0.01, 10000)]
    public decimal Price { get; set; }
    
    /// <summary>
    /// Foreign key to Category
    /// </summary>
    public int CategoryId { get; set; }
    
    /// <summary>
    /// Navigation property to Category
    /// </summary>
    public Category? Category { get; set; }
}
```

### 2. DbContext Classes

**Always:**
- Inherit from `DbContext`
- Include DbSet properties for each entity
- Add constructor accepting DbContextOptions
- Keep simple for beginners (no OnModelCreating unless necessary)

**Example:**
```csharp
/// <summary>
/// Database context for the application
/// </summary>
public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options)
        : base(options)
    {
    }
    
    public DbSet<Product> Products { get; set; }
    public DbSet<Category> Categories { get; set; }
}
```

### 3. Razor PageModel Classes

**Always:**
- Inject DbContext via constructor
- Use async methods (OnGetAsync, OnPostAsync)
- Store query results in public properties for view access
- Include proper error handling
- Use `[BindProperty]` for form data

**Example:**
```csharp
public class IndexModel : PageModel
{
    private readonly AppDbContext _context;
    
    public IndexModel(AppDbContext context)
    {
        _context = context;
    }
    
    public List<Product> Products { get; set; } = new();
    
    public async Task OnGetAsync()
    {
        Products = await _context.Products
            .Include(p => p.Category)
            .OrderBy(p => p.Name)
            .ToListAsync();
    }
}
```

### 4. LINQ Queries

**Teach progressively:**
- Start with simple `.ToList()`
- Add `.Where()` for filtering
- Show `.OrderBy()` for sorting
- Demonstrate `.Include()` for related data
- Introduce `.Select()`, `.GroupBy()`, aggregations

**Always use async methods:**
```csharp
// Simple query
var products = await _context.Products.ToListAsync();

// Filtering
var cheap = await _context.Products
    .Where(p => p.Price < 50)
    .ToListAsync();

// Sorting
var sorted = await _context.Products
    .OrderBy(p => p.Name)
    .ToListAsync();

// Related data
var withCategories = await _context.Products
    .Include(p => p.Category)
    .ToListAsync();

// Complex query
var results = await _context.Products
    .Where(p => p.Price < 100)
    .Include(p => p.Category)
    .OrderByDescending(p => p.Price)
    .Skip(10)
    .Take(10)
    .ToListAsync();
```

### 5. Docker Compose Files

**PostgreSQL template:**
```yaml
services:
  postgres:
    image: postgres:16-alpine
    container_name: projectname_postgres
    environment:
      POSTGRES_DB: dbname
      POSTGRES_USER: username
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - app_network

volumes:
  postgres_data:

networks:
  app_network:
```

**MongoDB template:**
```yaml
services:
  mongodb:
    image: mongo:7
    container_name: projectname_mongo
    environment:
      MONGO_INITDB_ROOT_USERNAME: admin
      MONGO_INITDB_ROOT_PASSWORD: admin123
      MONGO_INITDB_DATABASE: dbname
    ports:
      - "27017:27017"
    volumes:
      - mongodb_data:/data/db
    networks:
      - app_network

volumes:
  mongodb_data:

networks:
  app_network:
```

### 6. Connection Strings

**In appsettings.json (no real credentials):**
```json
{
  "ConnectionStrings": {
    "DefaultConnection": "Host=localhost;Database=appdb;Username=user;Password=STORED_IN_SECRETS"
  }
}
```

**Using User Secrets (teach this!):**
```bash
dotnet user-secrets init
dotnet user-secrets set "ConnectionStrings:DefaultConnection" "Host=localhost;Database=appdb;Username=user;Password=realpassword"
```

### 7. Program.cs Configuration

**Always include:**
```csharp
var builder = WebApplication.CreateBuilder(args);

// Add services
builder.Services.AddRazorPages();

// Configure DbContext
builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

var app = builder.Build();

// Configure middleware
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();
app.UseRouting();
app.UseAuthorization();
app.MapRazorPages();

app.Run();
```

## Documentation Standards

### Each Project Must Include:

1. **README.md**
   - Learning objectives
   - What the project demonstrates
   - Prerequisites
   - Key concepts
   - Code examples

2. **QUICKSTART.md** (for complex projects)
   - Step-by-step setup
   - Docker commands
   - Migration commands
   - Running instructions
   - Troubleshooting

3. **Code Comments**
   - XML docs on all public classes/methods
   - Inline comments explaining "why", not "what"
   - LINQ query explanations

## Common Patterns

### CRUD Operations

**Create:**
```csharp
var product = new Product { Name = "Widget", Price = 19.99m };
_context.Products.Add(product);
await _context.SaveChangesAsync();
```

**Read:**
```csharp
var product = await _context.Products.FindAsync(id);
var all = await _context.Products.ToListAsync();
```

**Update:**
```csharp
var product = await _context.Products.FindAsync(id);
if (product != null)
{
    product.Name = "Updated Name";
    await _context.SaveChangesAsync();
}
```

**Delete:**
```csharp
var product = await _context.Products.FindAsync(id);
if (product != null)
{
    _context.Products.Remove(product);
    await _context.SaveChangesAsync();
}
```

## Security Requirements

**Never:**
- Include real passwords in code or config files
- Commit connection strings to Git
- Use connection strings with production credentials in examples

**Always:**
- Use User Secrets for development
- Show environment variable configuration
- Include `.gitignore` entries for sensitive files
- Explain security in documentation

## Migration Workflow

**Teach this sequence:**
```bash
# 1. Create migration
dotnet ef migrations add InitialCreate

# 2. Review generated migration
# Check Migrations folder

# 3. Apply to database
dotnet ef database update

# 4. Verify in database
# Use pgAdmin, DBeaver, etc.
```

## Educational Approach

### Progressive Complexity:
1. **Project 01:** Basic EF Core with SQLite
2. **Project 02:** PostgreSQL with Docker
3. **Project 03:** Complex LINQ queries
4. **Project 04:** NoSQL comparison with MongoDB
5. **Project 05:** Scaffolding tools
6. **Project 06:** Security focus
7. **Project 07:** Comprehensive integration

### Explain Concepts:
- Why ORMs over raw SQL
- How LINQ prevents SQL injection
- Change tracking mechanism
- Difference between Add and Attach
- Async benefits for database operations

## Common Student Questions

Address these in documentation:

1. **"Why async/await for database calls?"**
   - Improves scalability
   - Prevents thread blocking
   - Standard practice in modern .NET

2. **"When to use Include vs separate queries?"**
   - Use Include for related data needed immediately
   - Avoid N+1 query problem

3. **"Why SaveChanges vs SaveChangesAsync?"**
   - Async is better for web applications
   - Allows server to handle more requests

4. **"What is change tracking?"**
   - EF tracks entity state (Added, Modified, Deleted)
   - Automatic UPDATE generation

## Testing

**Include examples of:**
- Data seeding for testing
- In-memory database for unit tests
- Integration tests with test containers (optional for advanced)

## Bootstrap UI

**Use Bootstrap 5 classes:**
```html
<table class="table table-striped table-hover">
    <thead>
        <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        @foreach (var product in Model.Products)
        {
            <tr>
                <td>@product.Name</td>
                <td>@product.Price.ToString("C")</td>
                <td>
                    <a asp-page="./Edit" asp-route-id="@product.Id" 
                       class="btn btn-sm btn-primary">Edit</a>
                </td>
            </tr>
        }
    </tbody>
</table>
```

## Remember

- **Simplicity first:** Don't over-engineer for beginners
- **Explain why:** Not just how
- **Real-world scenarios:** Use relatable examples (products, orders, users)
- **Error handling:** Show proper try-catch patterns
- **Validation:** Both client and server side
- **Performance:** Teach `.AsNoTracking()` for read-only queries

---

Follow these guidelines to create clear, educational, and beginner-friendly EF Core examples that help students master data persistence in .NET.
