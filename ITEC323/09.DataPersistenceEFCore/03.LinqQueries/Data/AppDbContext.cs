using LinqQueries.Models;
using Microsoft.EntityFrameworkCore;

namespace LinqQueries.Data;

/// <summary>
/// Database context for the LINQ queries learning project.
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
    /// Gets or sets product reviews for Include query examples.
    /// </summary>
    public DbSet<ProductReview> ProductReviews { get; set; } = default!;
}
