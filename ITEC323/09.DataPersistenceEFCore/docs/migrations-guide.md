# Database Migrations Guide

## What are Migrations?

Migrations are **version control for your database schema**. Just like Git tracks code changes, migrations track database structure changes.

### The Problem They Solve

```
Developer A: Adds "Email" column to Users table
Developer B: Pulls code - app crashes!
           : Database doesn't have Email column yet
```

**Solution:** Migrations = reproducible, trackable database changes

---

## How Migrations Work

### The Workflow

```
1. Change C# model → 2. Create migration → 3. Apply to database
   
   Product.cs           Migration file           Database updated
   + Stock field        + AddStockColumn         + Stock column added
```

### Migration Files

Located in `Migrations/` folder:

```
Migrations/
├── 20260308120000_InitialCreate.cs        # Up/Down methods
├── 20260308120000_InitialCreate.Designer.cs
├── 20260309140000_AddStockToProduct.cs
└── AppDbContextModelSnapshot.cs            # Current schema
```

---

## Essential Commands

### 1. Create Migration

```bash
dotnet ef migrations add MigrationName

# Examples:
dotnet ef migrations add InitialCreate
dotnet ef migrations add AddEmailToUser
dotnet ef migrations add CreateOrdersTable
```

**Naming convention:** Use descriptive PascalCase names

### 2. Apply Migration

```bash
dotnet ef database update

# Apply specific migration:
dotnet ef database update MigrationName

# Rollback to previous:
dotnet ef database update PreviousMigration
```

### 3. Remove Last Migration

```bash
dotnet ef migrations remove

# Use when:
# - Migration not yet applied
# - Made a mistake in migration
# - Want to recreate it
```

### 4. View Migrations

```bash
# List all migrations
dotnet ef migrations list

# Generate SQL script
dotnet ef migrations script

# Generate SQL for specific range
dotnet ef migrations script FromMigration ToMigration
```

### 5. Drop Database

```bash
dotnet ef database drop

# Force drop without confirmation
dotnet ef database drop --force
```

---

## Step-by-Step: First Migration

### Example Scenario

Creating an initial database with Products and Categories.

**Step 1: Define Models**

```csharp
// Models/Product.cs
public class Product
{
    public int Id { get; set; }
    public string Name { get; set; } = string.Empty;
    public decimal Price { get; set; }
    public int CategoryId { get; set; }
    public Category? Category { get; set; }
}

// Models/Category.cs
public class Category
{
    public int Id { get; set; }
    public string Name { get; set; } = string.Empty;
    public List<Product> Products { get; set; } = new();
}
```

**Step 2: Create DbContext**

```csharp
// Data/AppDbContext.cs
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

**Step 3: Configure in Program.cs**

```csharp
builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseSqlite("Data Source=app.db"));
```

**Step 4: Create Migration**

```bash
cd ProjectFolder
dotnet ef migrations add InitialCreate
```

**Output:**
```
Build started...
Build succeeded.
Done. To apply the migration, use 'dotnet ef database update'.
```

**Step 5: Review Migration**

Check `Migrations/YYYYMMDDHHMMSS_InitialCreate.cs`:

```csharp
public partial class InitialCreate : Migration
{
    protected override void Up(MigrationBuilder migrationBuilder)
    {
        migrationBuilder.CreateTable(
            name: "Categories",
            columns: table => new
            {
                Id = table.Column<int>(nullable: false)
                    .Annotation("Sqlite:Autoincrement", true),
                Name = table.Column<string>(nullable: false)
            },
            constraints: table =>
            {
                table.PrimaryKey("PK_Categories", x => x.Id);
            });

        migrationBuilder.CreateTable(
            name: "Products",
            columns: table => new
            {
                Id = table.Column<int>(nullable: false)
                    .Annotation("Sqlite:Autoincrement", true),
                Name = table.Column<string>(nullable: false),
                Price = table.Column<decimal>(nullable: false),
                CategoryId = table.Column<int>(nullable: false)
            },
            constraints: table =>
            {
                table.PrimaryKey("PK_Products", x => x.Id);
                table.ForeignKey(
                    name: "FK_Products_Categories_CategoryId",
                    column: x => x.CategoryId,
                    principalTable: "Categories",
                    principalColumn: "Id",
                    onDelete: ReferentialAction.Cascade);
            });
    }

    protected override void Down(MigrationBuilder migrationBuilder)
    {
        migrationBuilder.DropTable(name: "Products");
        migrationBuilder.DropTable(name: "Categories");
    }
}
```

**Step 6: Apply Migration**

```bash
dotnet ef database update
```

**Output:**
```
Build started...
Build succeeded.
Applying migration '20260308120000_InitialCreate'.
Done.
```

**Result:** Database created with Products and Categories tables!

---

## Adding Columns (Schema Evolution)

### Scenario: Add Stock field to Product

**Step 1: Update Model**

```csharp
public class Product
{
    public int Id { get; set; }
    public string Name { get; set; } = string.Empty;
    public decimal Price { get; set; }
    public int CategoryId { get; set; }
    public Category? Category { get; set; }
    
    // New field
    public int Stock { get; set; }
}
```

**Step 2: Create Migration**

```bash
dotnet ef migrations add AddStockToProduct
```

**Step 3: Review Generated Code**

```csharp
public partial class AddStockToProduct : Migration
{
    protected override void Up(MigrationBuilder migrationBuilder)
    {
        migrationBuilder.AddColumn<int>(
            name: "Stock",
            table: "Products",
            nullable: false,
            defaultValue: 0);
    }

    protected override void Down(MigrationBuilder migrationBuilder)
    {
        migrationBuilder.DropColumn(
            name: "Stock",
            table: "Products");
    }
}
```

**Step 4: Apply**

```bash
dotnet ef database update
```

**Result:** Stock column added to Products table!

---

## Common Migration Scenarios

### Add New Table

```csharp
// 1. Create new entity
public class Order
{
    public int Id { get; set; }
    public DateTime OrderDate { get; set; }
    public decimal Total { get; set; }
}

// 2. Add to DbContext
public DbSet<Order> Orders { get; set; }

// 3. Create migration
dotnet ef migrations add CreateOrdersTable
```

### Rename Column

```csharp
// In migration file:
protected override void Up(MigrationBuilder migrationBuilder)
{
    migrationBuilder.RenameColumn(
        name: "Name",
        table: "Products",
        newName: "ProductName");
}
```

### Add Foreign Key

```csharp
// 1. Add navigation property
public int SupplierId { get; set; }
public Supplier? Supplier { get; set; }

// 2. Migration will create FK automatically
dotnet ef migrations add AddSupplierToProduct
```

### Add Index

```csharp
// In OnModelCreating or migration:
migrationBuilder.CreateIndex(
    name: "IX_Products_Name",
    table: "Products",
    column: "Name");
```

---

## Rollback Strategy

### Revert Last Migration

```bash
# Remove migration (if not applied)
dotnet ef migrations remove

# Rollback database (if applied)
dotnet ef database update PreviousMigration
dotnet ef migrations remove
```

### Rollback to Specific Point

```bash
# List migrations
dotnet ef migrations list
# Output:
# 20260308120000_InitialCreate
# 20260309140000_AddStockToProduct
# 20260310160000_CreateOrdersTable

# Rollback to AddStockToProduct
dotnet ef database update AddStockToProduct
# This undoes CreateOrdersTable
```

---

## Team Collaboration

### Best Practices

1. **Commit migrations to Git**
   ```bash
   git add Migrations/
   git commit -m "Add Stock column to Product"
   ```

2. **Pull before creating new migrations**
   ```bash
   git pull
   dotnet ef database update  # Apply teammates' migrations
   dotnet ef migrations add YourNewMigration
   ```

3. **Never edit applied migrations**
   - Create new migration instead
   - Old migrations should be immutable

4. **Coordinate with team**
   - Communicate schema changes
   - Resolve migration conflicts quickly

### Merge Conflicts

If two developers create migrations simultaneously:

```bash
# Both create migrations at same time
Developer A: 20260310140000_AddEmail.cs
Developer B: 20260310140030_AddPhone.cs

# After merge, recreate snapshot:
dotnet ef migrations remove  # Remove yours
git pull                     # Get theirs
dotnet ef database update    # Apply theirs
dotnet ef migrations add AddPhone  # Recreate yours
```

---

## Production Deployment

### Generate SQL Script

Instead of running `dotnet ef database update` on production:

```bash
# Generate SQL script
dotnet ef migrations script --output migrate.sql

# Generate from specific migration
dotnet ef migrations script LastAppliedMigration --output update.sql

# Idempotent script (safe to run multiple times)
dotnet ef migrations script --idempotent --output migrate.sql
```

**Then:**
1. Review SQL script
2. Test on staging environment
3. Apply to production database using DBA tools

---

## Troubleshooting

### "No migrations found"

```bash
# Ensure you're in project directory
cd YourProject/

# Check project file has EF Core packages
dotnet list package
```

### "Build failed"

```bash
# Fix compilation errors first
dotnet build

# Then retry migration
dotnet ef migrations add MigrationName
```

### "Unable to create migration"

```bash
# Clean and rebuild
dotnet clean
dotnet build
dotnet ef migrations add MigrationName
```

### "Pending model changes"

```bash
# You have model changes not in migrations
dotnet ef migrations add CaptureModelChanges
```

### "Migration already applied"

```bash
# Can't remove applied migration
# Create new migration to revert changes instead
dotnet ef migrations add RevertPreviousChange
```

---

## Migration Naming Convention

### Good Names ✅
- `InitialCreate`
- `AddEmailToUser`
- `CreateOrdersTable`
- `AddIndexToProductName`
- `RenameCustomerToClient`

### Bad Names ❌
- `Migration1`
- `Update`
- `Fix`
- `Changes`

**Rule:** Name should describe what the migration does

---

## Data Seeding in Migrations

```csharp
protected override void Up(MigrationBuilder migrationBuilder)
{
    // Create table
    migrationBuilder.CreateTable(...);
    
    // Seed initial data
    migrationBuilder.InsertData(
        table: "Categories",
        columns: new[] { "Id", "Name" },
        values: new object[,]
        {
            { 1, "Electronics" },
            { 2, "Books" },
            { 3, "Clothing" }
        });
}
```

---

## Quick Reference

| Task | Command |
|------|---------|
| Create migration | `dotnet ef migrations add Name` |
| Apply migrations | `dotnet ef database update` |
| Remove last migration | `dotnet ef migrations remove` |
| List migrations | `dotnet ef migrations list` |
| Generate SQL | `dotnet ef migrations script` |
| Drop database | `dotnet ef database drop` |
| Rollback | `dotnet ef database update PreviousMigration` |

---

## Next Steps

- Practice with [01.BasicEFCore](../01.BasicEFCore/) project
- Learn LINQ queries in [LINQ Cheatsheet](linq-cheatsheet.md)
- Set up PostgreSQL with [Docker Setup Guide](docker-setup.md)

---

## Key Takeaways

✅ Migrations = version control for database schema  
✅ Always create migration after model changes  
✅ Apply migrations with `dotnet ef database update`  
✅ Commit migrations to Git for team collaboration  
✅ Use descriptive migration names  
✅ Generate SQL scripts for production deployment  

**Remember:** Migrations keep your database synchronized with your C# models!
