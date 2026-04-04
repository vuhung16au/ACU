using Microsoft.EntityFrameworkCore;
using PerformanceOptimization.Models;

namespace PerformanceOptimization.Data;

/// <summary>
/// Represents the EF Core database context for the performance optimization lesson.
/// </summary>
public class AppDbContext : DbContext
{
    /// <summary>
    /// Initializes a new instance of the <see cref="AppDbContext"/> class.
    /// </summary>
    /// <param name="options">The EF Core options for the PostgreSQL connection.</param>
    public AppDbContext(DbContextOptions<AppDbContext> options)
        : base(options)
    {
    }

    /// <summary>
    /// Gets the products used in the performance demos.
    /// </summary>
    public DbSet<Product> Products => Set<Product>();

    /// <summary>
    /// Gets the sale records used to calculate recent revenue.
    /// </summary>
    public DbSet<SaleRecord> SaleRecords => Set<SaleRecord>();

    /// <inheritdoc />
    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Product>(entity =>
        {
            entity.Property(product => product.Name).HasMaxLength(120);
            entity.Property(product => product.CategoryName).HasMaxLength(80);
            entity.Property(product => product.Price).HasColumnType("numeric(10,2)");
            entity.HasIndex(product => new { product.CategoryName, product.Price });
        });

        modelBuilder.Entity<SaleRecord>(entity =>
        {
            entity.HasIndex(record => new { record.ProductId, record.SoldAtUtc });
            entity.HasOne(record => record.Product)
                .WithMany(product => product.SaleRecords)
                .HasForeignKey(record => record.ProductId)
                .OnDelete(DeleteBehavior.Cascade);
        });
    }
}
