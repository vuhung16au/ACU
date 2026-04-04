using ConcurrencyConflicts.Models;
using Microsoft.EntityFrameworkCore;

namespace ConcurrencyConflicts.Data;

/// <summary>
/// Represents the EF Core database context for the concurrency lesson.
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
    /// Gets or sets the products used in the demo.
    /// </summary>
    public DbSet<Product> Products => Set<Product>();

    /// <summary>
    /// Configures the model.
    /// </summary>
    /// <param name="modelBuilder">The model builder.</param>
    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Product>(entity =>
        {
            entity.Property(product => product.Price).HasPrecision(10, 2);
            entity.Property(product => product.Version).IsConcurrencyToken();
            entity.Property(product => product.Name).HasMaxLength(80);
            entity.Property(product => product.Notes).HasMaxLength(300);
        });
    }

    /// <summary>
    /// Saves changes and updates the version number for modified products.
    /// </summary>
    /// <returns>The number of state entries written to the database.</returns>
    public override int SaveChanges()
    {
        ApplyVersioningRules();
        return base.SaveChanges();
    }

    /// <summary>
    /// Saves changes asynchronously and updates the version number for modified products.
    /// </summary>
    /// <param name="cancellationToken">The cancellation token.</param>
    /// <returns>The number of state entries written to the database.</returns>
    public override Task<int> SaveChangesAsync(CancellationToken cancellationToken = default)
    {
        ApplyVersioningRules();
        return base.SaveChangesAsync(cancellationToken);
    }

    private void ApplyVersioningRules()
    {
        foreach (var entry in ChangeTracker.Entries<Product>())
        {
            if (entry.State == EntityState.Added)
            {
                entry.Entity.Version = 1;
                entry.Entity.LastModifiedUtc = DateTime.UtcNow;
                continue;
            }

            if (entry.State == EntityState.Modified)
            {
                entry.Entity.Version += 1;
                entry.Entity.LastModifiedUtc = DateTime.UtcNow;
            }
        }
    }
}
