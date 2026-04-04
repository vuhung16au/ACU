using Microsoft.EntityFrameworkCore;
using RawSQLDapper.Models;

namespace RawSQLDapper.Data;

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

        var products = new[]
        {
            new Product { Name = "Wireless Presenter", CategoryName = "Office Gear", QuantityInStock = 42, Price = 69m },
            new Product { Name = "USB-C Dock", CategoryName = "Accessories", QuantityInStock = 30, Price = 159m },
            new Product { Name = "Drawing Tablet", CategoryName = "Classroom Devices", QuantityInStock = 16, Price = 249m },
            new Product { Name = "Portable Projector", CategoryName = "Office Gear", QuantityInStock = 9, Price = 499m }
        };

        _context.Products.AddRange(products);
        await _context.SaveChangesAsync();

        _context.SaleRecords.AddRange(
            new SaleRecord { ProductId = products[0].Id, QuantitySold = 5, SoldAtUtc = DateTime.UtcNow.AddDays(-5) },
            new SaleRecord { ProductId = products[0].Id, QuantitySold = 3, SoldAtUtc = DateTime.UtcNow.AddDays(-2) },
            new SaleRecord { ProductId = products[1].Id, QuantitySold = 4, SoldAtUtc = DateTime.UtcNow.AddDays(-4) },
            new SaleRecord { ProductId = products[1].Id, QuantitySold = 2, SoldAtUtc = DateTime.UtcNow.AddDays(-1) },
            new SaleRecord { ProductId = products[2].Id, QuantitySold = 2, SoldAtUtc = DateTime.UtcNow.AddDays(-3) },
            new SaleRecord { ProductId = products[3].Id, QuantitySold = 1, SoldAtUtc = DateTime.UtcNow.AddDays(-1) });

        _context.AuditEntries.Add(new AuditEntry
        {
            EventName = "SeedData",
            Details = "Initial reporting data created for the Dapper lesson.",
            CreatedAtUtc = DateTime.UtcNow
        });

        await _context.SaveChangesAsync();
        _logger.LogInformation("Seeded the Raw SQL with Dapper lesson database.");
    }
}
