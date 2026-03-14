using ScaffoldingCRUD.Models;
using Microsoft.EntityFrameworkCore;

namespace ScaffoldingCRUD.Data;

/// <summary>
/// Database context for the scaffolding CRUD learning project.
/// </summary>
public class AppDbContext : DbContext
{
    /// <summary>
    /// Initializes a new instance of the <see cref="AppDbContext"/> class.
    /// </summary>
    /// <param name="options">Database context options.</param>
    public AppDbContext(DbContextOptions<AppDbContext> options)
        : base(options)
    {
    }

    /// <summary>
    /// Gets or sets products in the catalog.
    /// </summary>
    public DbSet<Product> Products { get; set; } = default!;

    /// <summary>
    /// Gets or sets categories used by products.
    /// </summary>
    public DbSet<Category> Categories { get; set; } = default!;

    /// <summary>
    /// Configures model relationships and seed values for demo scaffolding.
    /// </summary>
    /// <param name="modelBuilder">Model builder instance.</param>
    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        modelBuilder.Entity<Category>().HasData(
            new Category { Id = 1, Name = "Hardware" },
            new Category { Id = 2, Name = "Office" },
            new Category { Id = 3, Name = "Accessories" }
        );
    }
}
