using Microsoft.EntityFrameworkCore;
using BlazorPagesAdvanced.Models;

namespace BlazorPagesAdvanced.Data;

/// <summary>
/// Database context for the application. DbContext is the bridge between C# and the database.
/// </summary>
public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
    {
    }

    public DbSet<User> Users { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);
        modelBuilder.Entity<User>().HasData(
            new User { Id = 1, Name = "Alice Johnson", Email = "alice@example.com", Country = "USA", CreatedDate = new DateTime(2024, 1, 15) },
            new User { Id = 2, Name = "Bob Smith", Email = "bob@example.com", Country = "Canada", CreatedDate = new DateTime(2024, 2, 20) },
            new User { Id = 3, Name = "田中太郎", Email = "tanaka@example.jp", Country = "Japan", CreatedDate = new DateTime(2024, 3, 10) },
            new User { Id = 4, Name = "Maria Garcia", Email = "maria@example.es", Country = "Spain", CreatedDate = new DateTime(2024, 4, 5) },
            new User { Id = 5, Name = "李明", Email = "li@example.cn", Country = "China", CreatedDate = new DateTime(2024, 5, 18) }
        );
    }
}
