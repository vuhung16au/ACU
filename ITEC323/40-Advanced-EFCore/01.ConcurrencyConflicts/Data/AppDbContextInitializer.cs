using ConcurrencyConflicts.Models;
using Microsoft.EntityFrameworkCore;

namespace ConcurrencyConflicts.Data;

/// <summary>
/// Creates and seeds the lesson database.
/// </summary>
public class AppDbContextInitializer
{
    private readonly AppDbContext _context;
    private readonly ILogger<AppDbContextInitializer> _logger;

    /// <summary>
    /// Initializes a new instance of the <see cref="AppDbContextInitializer"/> class.
    /// </summary>
    /// <param name="context">The database context.</param>
    /// <param name="logger">The logger.</param>
    public AppDbContextInitializer(AppDbContext context, ILogger<AppDbContextInitializer> logger)
    {
        _context = context;
        _logger = logger;
    }

    /// <summary>
    /// Ensures the database exists and is seeded.
    /// </summary>
    /// <returns>A task that represents the asynchronous initialization operation.</returns>
    public async Task InitializeAsync()
    {
        await _context.Database.EnsureCreatedAsync();

        if (await _context.Products.AnyAsync())
        {
            return;
        }

        _context.Products.AddRange(
            new Product
            {
                Name = "Concurrency Drill Product",
                Price = 129.99m,
                QuantityInStock = 24,
                Notes = "Use this row to trigger a conflict with two browser tabs."
            },
            new Product
            {
                Name = "Warehouse Scanner",
                Price = 249.50m,
                QuantityInStock = 8,
                Notes = "Good example of a shared operational record."
            },
            new Product
            {
                Name = "Teaching Tablet",
                Price = 399.00m,
                QuantityInStock = 15,
                Notes = "Updated often during class stock checks."
            });

        await _context.SaveChangesAsync();
        _logger.LogInformation("Seeded the concurrency lesson database with demo products.");
    }
}
