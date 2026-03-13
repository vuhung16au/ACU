# Key Takeaways

- EF Core maps C# classes (entities) to database tables.
- `DbContext` is the main gateway for querying and saving data.
- [Migrations](../../../00.DotnetOverview/DatabaseMigrations.md) keep database schema changes versioned and repeatable.
- Async methods like `ToListAsync()` and `SaveChangesAsync()` improve web app scalability.
- Data annotations (for example, `[Required]` and `[MaxLength]`) drive both validation and schema constraints.
- Razor PageModels can receive `AppDbContext` through dependency injection.
- For read-only list pages, `.AsNoTracking()` is a useful performance habit.
