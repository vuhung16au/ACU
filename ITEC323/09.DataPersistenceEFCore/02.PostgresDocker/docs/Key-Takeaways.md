# Key Takeaways

- EF Core maps C# entities to relational tables in PostgreSQL.
- `DbContext` is registered in dependency injection and used by Razor PageModels.
- Docker Compose provides a repeatable local PostgreSQL environment.
- Migrations are the source-controlled history of schema changes.
- Async methods such as `ToListAsync()` and `SaveChangesAsync()` support web scalability.
- Data annotations (for example, `[Required]`, `[MaxLength]`, `[Range]`) drive validation and schema constraints.
- Connection strings should be stored in User Secrets (development) and environment variables (production).
