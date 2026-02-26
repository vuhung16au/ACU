using HelloWorldData.Models;
using Microsoft.EntityFrameworkCore;

namespace HelloWorldData.Data;

/// <summary>
/// Provides EF Core access to the local SQLite database.
/// </summary>
public class AppDbContext : DbContext
{
    /// <summary>
    /// Represents the Students table in the database.
    /// </summary>
    public DbSet<Student> Students => Set<Student>();

    /// <summary>
    /// Configures the SQLite database connection.
    /// </summary>
    /// <param name="optionsBuilder">Build options used by EF Core.</param>
    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        if (!optionsBuilder.IsConfigured)
        {
            // For this beginner project, the connection string is intentionally simple and local.
            optionsBuilder.UseSqlite("Data Source=app.db");
        }
    }
}
