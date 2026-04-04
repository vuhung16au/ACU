using EfficientLINQ.Models;
using Microsoft.EntityFrameworkCore;

namespace EfficientLINQ.Data;

/// <summary>
/// Represents the EF Core database context for the Efficient LINQ lesson.
/// </summary>
public class AppDbContext : DbContext
{
    /// <summary>
    /// Initializes a new instance of the <see cref="AppDbContext"/> class.
    /// </summary>
    /// <param name="options">The database context options.</param>
    public AppDbContext(DbContextOptions<AppDbContext> options)
        : base(options)
    {
    }

    /// <summary>
    /// Gets or sets the categories.
    /// </summary>
    public DbSet<Category> Categories => Set<Category>();

    /// <summary>
    /// Gets or sets the products.
    /// </summary>
    public DbSet<Product> Products => Set<Product>();

    /// <summary>
    /// Configures the model.
    /// </summary>
    /// <param name="modelBuilder">The model builder.</param>
    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Category>(entity =>
        {
            entity.Property(category => category.Name).HasMaxLength(80);
        });

        modelBuilder.Entity<Product>(entity =>
        {
            entity.Property(product => product.Name).HasMaxLength(120);
            entity.Property(product => product.Description).HasMaxLength(500);
            entity.Property(product => product.Price).HasPrecision(10, 2);
            entity.HasOne(product => product.Category)
                .WithMany(category => category.Products)
                .HasForeignKey(product => product.CategoryId);
        });
    }
}
