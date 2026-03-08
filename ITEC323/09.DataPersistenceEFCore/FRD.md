# Functional Requirements Document (FRD)
## Week 9: Data Persistence & Entity Framework Core

---

## 1. Purpose Statement

This module teaches students to persist application data using Entity Framework Core, the standard ORM for .NET. Students will learn to define data models in C#, manage database schemas through migrations, query data using LINQ, and work with both relational (PostgreSQL) and NoSQL (MongoDB) databases using Docker containers. The module emphasizes security best practices for connection strings and modern development workflows.

---

## 2. Scope

### In Scope
- Object-Relational Mapper (ORM) concepts
- Entity Framework Core 10 setup and configuration
- Defining C# data models (entities)
- DbContext and DbSet
- Database migrations (add, update, remove)
- CRUD operations with EF Core
- LINQ queries (Where, OrderBy, Include, Select, GroupBy, etc.)
- PostgreSQL setup with Docker Compose
- MongoDB setup with Docker Compose
- Connection string configuration
- User Secrets for development
- Environment Variables for production
- Dependency injection in Razor Pages
- Tag helpers and model binding
- Code scaffolding with dotnet-aspnet-codegenerator
- Change tracking and SaveChanges
- Eager loading (.Include) and lazy loading
- Data annotations ([Required], [MaxLength], [Key])
- One-to-Many and Many-to-Many relationships

### Out of Scope
- Advanced EF Core features (global query filters, value conversions)
- Raw SQL execution (focus on LINQ)
- Stored procedures
- Database-first or model-first approaches (Code-First only)
- Complex database performance tuning
- Production database deployment
- Multi-tenant database architectures
- Database replication or clustering
- Authentication/authorization (covered in other modules)
- Blazor or SPA frameworks (focus on Razor Pages)

---

## 3. Learning Objectives

Students will be able to:

1. **LO1:** Explain what an ORM is and its advantages over raw SQL
2. **LO2:** Define C# data models that map to database tables
3. **LO3:** Create and configure a DbContext class
4. **LO4:** Generate and apply database migrations using `dotnet ef` commands
5. **LO5:** Perform CRUD operations using EF Core methods
6. **LO6:** Write LINQ queries to filter, sort, and join data
7. **LO7:** Set up PostgreSQL using Docker Compose
8. **LO8:** Set up MongoDB using Docker Compose
9. **LO9:** Secure connection strings using User Secrets and Environment Variables
10. **LO10:** Use dependency injection to access DbContext in Razor Pages
11. **LO11:** Bind C# models to HTML forms using tag helpers
12. **LO12:** Generate CRUD pages using scaffolding tools
13. **LO13:** Use `.Include()` to load related data (eager loading)
14. **LO14:** Apply data annotations to enforce validation and constraints
15. **LO15:** Compare relational (SQL) and document (NoSQL) databases

---

## 4. Functional Requirements

### FR1: ORM Introduction
**Priority:** MUST HAVE  
**Learning Objective:** LO1

Students must demonstrate understanding of:
- What an ORM is and why it's used
- Advantages of ORMs (type safety, SQL injection prevention, productivity)
- How EF Core translates LINQ to SQL
- The concept of change tracking

**Acceptance Criteria:**
- Can explain ORM benefits in their own words
- Understand compile-time type checking vs runtime SQL errors
- Recognize how LINQ protects against SQL injection

---

### FR2: Entity Framework Core Setup
**Priority:** MUST HAVE  
**Learning Objectives:** LO2, LO3

Students must demonstrate ability to:
- Install EF Core NuGet packages
- Define entity classes with properties
- Create a DbContext class
- Configure DbContext in Program.cs with dependency injection
- Use proper data types (int, string, DateTime, decimal, bool)

**Acceptance Criteria:**
- DbContext class created with DbSet properties
- Entity classes have appropriate properties and types
- DbContext registered in DI container
- Application builds without errors

**Example:**
```csharp
public class Product
{
    public int Id { get; set; }
    public string Name { get; set; }
    public decimal Price { get; set; }
}

public class AppDbContext : DbContext
{
    public DbSet<Product> Products { get; set; }
}
```

---

### FR3: Database Migrations
**Priority:** MUST HAVE  
**Learning Objective:** LO4

Students must demonstrate ability to:
- Create initial migration: `dotnet ef migrations add InitialCreate`
- Apply migration to database: `dotnet ef database update`
- Remove last migration: `dotnet ef migrations remove`
- Create subsequent migrations after model changes
- Understand migration files (Up/Down methods)

**Acceptance Criteria:**
- Migrations folder created with timestamped files
- Database schema matches C# models
- Can add/remove migrations correctly
- Understand migration version control

---

### FR4: CRUD Operations with EF Core
**Priority:** MUST HAVE  
**Learning Objective:** LO5

Students must demonstrate ability to:
- **Create:** Add new entities with `_context.Add()` and `SaveChanges()`
- **Read:** Query entities with `ToList()`, `FirstOrDefault()`, `FindAsync()`
- **Update:** Modify entities and call `SaveChanges()`
- **Delete:** Remove entities with `Remove()` and `SaveChanges()`
- Use async methods (`ToListAsync()`, `SaveChangesAsync()`)

**Acceptance Criteria:**
- All CRUD operations working in Razor Pages
- Proper use of async/await
- Changes persisted to database
- Error handling for failed operations

**Example:**
```csharp
// Create
var product = new Product { Name = "Widget", Price = 19.99m };
_context.Products.Add(product);
await _context.SaveChangesAsync();

// Read
var products = await _context.Products.ToListAsync();

// Update
var product = await _context.Products.FindAsync(id);
product.Price = 24.99m;
await _context.SaveChangesAsync();

// Delete
_context.Products.Remove(product);
await _context.SaveChangesAsync();
```

---

### FR5: LINQ Queries
**Priority:** MUST HAVE  
**Learning Objective:** LO6

Students must demonstrate ability to:
- Filter with `.Where()`
- Sort with `.OrderBy()` and `.OrderByDescending()`
- Select specific properties with `.Select()`
- Load related data with `.Include()`
- Paginate with `.Skip()` and `.Take()`
- Aggregate with `.Count()`, `.Sum()`, `.Average()`, `.Max()`, `.Min()`
- Group data with `.GroupBy()`
- Check existence with `.Any()`

**Acceptance Criteria:**
- Multiple LINQ operations demonstrated
- Queries return correct results
- Understand deferred execution
- Proper use of `.ToList()` or `.ToListAsync()`

**Example:**
```csharp
var cheapProducts = await _context.Products
    .Where(p => p.Price < 50)
    .OrderBy(p => p.Name)
    .ToListAsync();

var productsWithCategories = await _context.Products
    .Include(p => p.Category)
    .ToListAsync();
```

---

### FR6: PostgreSQL with Docker
**Priority:** MUST HAVE  
**Learning Objectives:** LO7

Students must demonstrate ability to:
- Create `docker-compose.yml` for PostgreSQL
- Configure PostgreSQL image, ports, environment variables
- Define persistent volumes for data
- Install Npgsql.EntityFrameworkCore.PostgreSQL package
- Configure connection string for PostgreSQL
- Apply migrations to PostgreSQL database

**Acceptance Criteria:**
- Docker Compose file runs PostgreSQL successfully
- Application connects to PostgreSQL
- Data persists across container restarts
- Connection string properly configured

**Example docker-compose.yml:**
```yaml
services:
  postgres:
    image: postgres:16-alpine
    environment:
      POSTGRES_DB: myapp
      POSTGRES_USER: appuser
      POSTGRES_PASSWORD: devpassword
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

---

### FR7: MongoDB with Docker
**Priority:** SHOULD HAVE  
**Learning Objective:** LO8

Students must demonstrate ability to:
- Create `docker-compose.yml` for MongoDB
- Install MongoDB.Driver package
- Configure MongoDB connection
- Perform basic CRUD operations with MongoDB C# driver
- Understand document structure (nested objects, arrays)
- Compare NoSQL approach with relational approach

**Acceptance Criteria:**
- Docker Compose file runs MongoDB successfully
- Application connects to MongoDB
- Can insert and query documents
- Understand differences from SQL databases

---

### FR8: Secure Connection Strings
**Priority:** MUST HAVE  
**Learning Objective:** LO9

Students must demonstrate ability to:
- Initialize User Secrets: `dotnet user-secrets init`
- Store connection strings in secrets.json
- Read secrets in Program.cs
- Use environment variables for production
- Understand configuration hierarchy
- Never commit connection strings to Git

**Acceptance Criteria:**
- No connection strings in appsettings.json
- User Secrets properly configured
- Application reads secrets correctly
- .gitignore excludes sensitive files
- Documentation explains security approach

**Example:**
```bash
dotnet user-secrets set "ConnectionStrings:DefaultConnection" "Server=localhost;Database=mydb;User=user;Password=pass"
```

---

### FR9: Dependency Injection in Razor Pages
**Priority:** MUST HAVE  
**Learning Objective:** LO10

Students must demonstrate ability to:
- Request DbContext in PageModel constructor
- Use injected DbContext in OnGet/OnPost handlers
- Understand DI lifetime (Scoped for DbContext)
- Properly dispose of resources (handled by DI)

**Acceptance Criteria:**
- DbContext injected via constructor
- No manual DbContext instantiation
- Follows DI best practices

**Example:**
```csharp
public class IndexModel : PageModel
{
    private readonly AppDbContext _context;
    
    public IndexModel(AppDbContext context)
    {
        _context = context;
    }
    
    public List<Product> Products { get; set; }
    
    public async Task OnGetAsync()
    {
        Products = await _context.Products.ToListAsync();
    }
}
```

---

### FR10: Tag Helpers and Model Binding
**Priority:** MUST HAVE  
**Learning Objective:** LO11

Students must demonstrate ability to:
- Use `asp-for` to bind inputs to model properties
- Use `asp-validation-for` to display validation errors
- Use `asp-items` for select dropdowns
- Use `[BindProperty]` attribute in PageModel
- Handle form submissions with OnPostAsync

**Acceptance Criteria:**
- Forms properly bound to models
- Validation errors display correctly
- Form data posts to PageModel
- Data types correctly inferred (text, number, date, checkbox)

**Example:**
```html
<input asp-for="Product.Name" class="form-control" />
<span asp-validation-for="Product.Name" class="text-danger"></span>

<select asp-for="Product.CategoryId" asp-items="Model.Categories"></select>
```

---

### FR11: Scaffolding CRUD Pages
**Priority:** SHOULD HAVE  
**Learning Objective:** LO12

Students must demonstrate ability to:
- Install dotnet-aspnet-codegenerator tool
- Scaffold Create, Edit, Delete, Details, Index pages
- Understand generated code structure
- Customize scaffolded pages

**Acceptance Criteria:**
- Scaffolding tool installed and working
- CRUD pages generated successfully
- Generated pages are functional
- Student can explain generated code

**Example:**
```bash
dotnet aspnet-codegenerator razorpage -m Product -dc AppDbContext -outDir Pages/Products --useDefaultLayout
```

---

### FR12: Related Data Loading
**Priority:** MUST HAVE  
**Learning Objective:** LO13

Students must demonstrate ability to:
- Define navigation properties in entities
- Configure relationships (one-to-many, many-to-many)
- Use `.Include()` for eager loading
- Use `.ThenInclude()` for nested relationships
- Understand lazy loading vs eager loading

**Acceptance Criteria:**
- Navigation properties defined
- Relationships configured correctly
- Related data loads without N+1 queries
- Understand when to use Include

**Example:**
```csharp
public class Product
{
    public int CategoryId { get; set; }
    public Category Category { get; set; }
}

var products = await _context.Products
    .Include(p => p.Category)
    .ToListAsync();
```

---

### FR13: Data Annotations
**Priority:** MUST HAVE  
**Learning Objective:** LO14

Students must demonstrate ability to:
- Use `[Required]` for non-nullable fields
- Use `[MaxLength]` to limit string length
- Use `[Key]` for custom primary keys
- Use `[Column]` for custom column names
- Use `[ForeignKey]` for relationships
- Understand how annotations affect database schema

**Acceptance Criteria:**
- Appropriate annotations applied to models
- Migrations reflect annotation constraints
- Client and server-side validation working

**Example:**
```csharp
public class Product
{
    public int Id { get; set; }
    
    [Required]
    [MaxLength(100)]
    public string Name { get; set; }
    
    [Range(0.01, 10000)]
    public decimal Price { get; set; }
}
```

---

### FR14: Displaying Data in Razor Views
**Priority:** MUST HAVE  
**Learning Objectives:** LO10, LO11

Students must demonstrate ability to:
- Use `@foreach` to iterate over collections
- Display entity properties with proper formatting
- Use `@Model` to access PageModel data
- Create responsive tables with Bootstrap
- Handle null values safely

**Acceptance Criteria:**
- Data displays correctly in views
- Proper HTML structure
- Responsive design
- No null reference exceptions

**Example:**
```html
@foreach (var product in Model.Products)
{
    <tr>
        <td>@product.Name</td>
        <td>@product.Price.ToString("C")</td>
        <td>@product.Category?.Name</td>
    </tr>
}
```

---

## 5. Non-Functional Requirements

### NFR1: Performance
- Database queries should complete within 2 seconds
- Use async/await for all database operations
- Implement pagination for large datasets
- Use `.AsNoTracking()` for read-only queries

### NFR2: Security
- Connection strings must never be committed to Git
- Use User Secrets for development
- Use Environment Variables or secure vaults for production
- Validate all user input (client and server side)
- Sanitize data to prevent SQL injection (handled by EF Core)

### NFR3: Code Quality
- Follow C# naming conventions (PascalCase, camelCase)
- Add XML documentation to public classes and methods
- Use meaningful variable and class names
- Keep methods focused and small
- Follow SOLID principles where appropriate

### NFR4: Maintainability
- Organize code into logical folders
- Separate concerns (Pages, Models, Data)
- Use repository pattern for complex scenarios (optional)
- Keep configurations in appropriate files
- Document complex LINQ queries

### NFR5: Usability
- Provide clear error messages
- Show loading states during operations
- Validate input before submission
- Use Bootstrap for consistent UI
- Mobile-responsive design

---

## 6. Technical Constraints

- Must use .NET 10.0 or later
- Must use Entity Framework Core 10
- Must use Docker for PostgreSQL and MongoDB
- Must use Razor Pages (not MVC or Blazor)
- Must demonstrate both SQL and NoSQL approaches
- Must apply migrations using dotnet CLI
- Connection strings must be secured

---

## 7. Deliverables

1. Seven functional projects (01-07)
2. Complete documentation (README, QUICKSTART, docs/)
3. Working Docker Compose files
4. Sample data seed scripts
5. Database migration files
6. Comprehensive final application (07.ComprehensiveApp)

---

## 8. Success Criteria

Students successfully complete the module when they can:

✅ Explain ORM concepts and benefits  
✅ Create and apply database migrations  
✅ Perform all CRUD operations with EF Core  
✅ Write complex LINQ queries  
✅ Set up PostgreSQL and MongoDB with Docker  
✅ Secure connection strings properly  
✅ Use dependency injection correctly  
✅ Bind data to UI with tag helpers  
✅ Use scaffolding tools effectively  
✅ Build a comprehensive application integrating all concepts  

