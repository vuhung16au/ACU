using Microsoft.EntityFrameworkCore;
using RazorPagesAdvanced.Models;

namespace RazorPagesAdvanced.Data;

/// <summary>
/// Database context for the application.
/// DbContext is the bridge between your C# code and the database.
/// </summary>
public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
    {
    }
    
    /// <summary>
    /// Users table in the database.
    /// DbSet represents a collection of entities (rows) in the database.
    /// </summary>
    public DbSet<User> Users { get; set; }
    
    /// <summary>
    /// Seeds initial data into the database.
    /// This method is called when the database is created.
    /// </summary>
    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);
        
        // Seed sample data
        modelBuilder.Entity<User>().HasData(
            new User { Id = 1, Name = "Alice Johnson", Email = "alice@example.com", Country = "USA", CreatedDate = new DateTime(2024, 1, 15) },
            new User { Id = 2, Name = "Bob Smith", Email = "bob@example.com", Country = "Canada", CreatedDate = new DateTime(2024, 2, 20) },
            new User { Id = 3, Name = "田中太郎", Email = "tanaka@example.jp", Country = "Japan", CreatedDate = new DateTime(2024, 3, 10) },
            new User { Id = 4, Name = "Maria Garcia", Email = "maria@example.es", Country = "Spain", CreatedDate = new DateTime(2024, 4, 5) },
            new User { Id = 5, Name = "李明", Email = "li@example.cn", Country = "China", CreatedDate = new DateTime(2024, 5, 18) }
        );
    }
}
