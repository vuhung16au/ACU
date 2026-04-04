using Microsoft.EntityFrameworkCore;
using RawSQLDapper.Models;

namespace RawSQLDapper.Data;

/// <summary>
/// Represents the EF Core database context for the Dapper lesson.
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
    /// Gets or sets the products.
    /// </summary>
    public DbSet<Product> Products => Set<Product>();

    /// <summary>
    /// Gets or sets the sales records.
    /// </summary>
    public DbSet<SaleRecord> SaleRecords => Set<SaleRecord>();

    /// <summary>
    /// Gets or sets the audit entries.
    /// </summary>
    public DbSet<AuditEntry> AuditEntries => Set<AuditEntry>();

    /// <summary>
    /// Configures the model.
    /// </summary>
    /// <param name="modelBuilder">The model builder.</param>
    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Product>(entity =>
        {
            entity.Property(product => product.Name).HasMaxLength(120);
            entity.Property(product => product.CategoryName).HasMaxLength(80);
            entity.Property(product => product.Price).HasPrecision(10, 2);
        });

        modelBuilder.Entity<AuditEntry>(entity =>
        {
            entity.Property(entry => entry.EventName).HasMaxLength(120);
            entity.Property(entry => entry.Details).HasMaxLength(500);
        });
    }
}
