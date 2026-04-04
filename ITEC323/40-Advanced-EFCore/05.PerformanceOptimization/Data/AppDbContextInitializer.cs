using Microsoft.EntityFrameworkCore;
using PerformanceOptimization.Models;

namespace PerformanceOptimization.Data;

/// <summary>
/// Creates and seeds the PostgreSQL database for the performance optimization lesson.
/// </summary>
public class AppDbContextInitializer
{
    private readonly AppDbContext _dbContext;
    private readonly ILogger<AppDbContextInitializer> _logger;

    /// <summary>
    /// Initializes a new instance of the <see cref="AppDbContextInitializer"/> class.
    /// </summary>
    /// <param name="dbContext">The lesson database context.</param>
    /// <param name="logger">The logger used during setup.</param>
    public AppDbContextInitializer(AppDbContext dbContext, ILogger<AppDbContextInitializer> logger)
    {
        _dbContext = dbContext;
        _logger = logger;
    }

    /// <summary>
    /// Ensures the database exists and contains seeded demo data.
    /// </summary>
    /// <returns>A task that represents the asynchronous initialization work.</returns>
    public async Task InitializeAsync()
    {
        await _dbContext.Database.EnsureCreatedAsync();

        if (await _dbContext.Products.AnyAsync())
        {
            return;
        }

        var categories = new[]
        {
            "Classroom Devices",
            "Warehousing",
            "Retail Displays",
            "Lab Sensors"
        };

        var products = new List<Product>();
        var random = new Random(323);

        for (var productNumber = 1; productNumber <= 48; productNumber++)
        {
            var product = new Product
            {
                Name = $"Performance Demo Product {productNumber:00}",
                CategoryName = categories[(productNumber - 1) % categories.Length],
                Price = 59m + (productNumber * 7.5m),
                QuantityInStock = 15 + (productNumber % 9) * 4
            };

            products.Add(product);
        }

        _dbContext.Products.AddRange(products);
        await _dbContext.SaveChangesAsync();

        var now = DateTime.UtcNow;
        var sales = new List<SaleRecord>();

        foreach (var product in products)
        {
            for (var dayOffset = 0; dayOffset < 36; dayOffset++)
            {
                sales.Add(new SaleRecord
                {
                    ProductId = product.Id,
                    QuantitySold = 1 + random.Next(1, 6),
                    SoldAtUtc = now.AddDays(-dayOffset).AddMinutes(-(product.Id * 3))
                });
            }
        }

        _dbContext.SaleRecords.AddRange(sales);
        await _dbContext.SaveChangesAsync();

        _logger.LogInformation(
            "Seeded the performance optimization lesson with {ProductCount} products and {SaleCount} sales.",
            products.Count,
            sales.Count);
    }
}
