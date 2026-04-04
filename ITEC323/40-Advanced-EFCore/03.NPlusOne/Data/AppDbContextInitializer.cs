using Microsoft.EntityFrameworkCore;
using NPlusOne.Models;

namespace NPlusOne.Data;

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

        if (await _context.Orders.AnyAsync())
        {
            return;
        }

        var categories = new[]
        {
            new Category { Name = "Classroom Devices" },
            new Category { Name = "Accessories" },
            new Category { Name = "Office Gear" }
        };

        _context.Categories.AddRange(categories);
        await _context.SaveChangesAsync();

        var products = new[]
        {
            new Product { Name = "Wireless Presenter", Price = 69m, CategoryId = categories[2].Id },
            new Product { Name = "USB-C Dock", Price = 159m, CategoryId = categories[1].Id },
            new Product { Name = "Drawing Tablet", Price = 249m, CategoryId = categories[0].Id },
            new Product { Name = "Portable Projector", Price = 499m, CategoryId = categories[2].Id },
            new Product { Name = "Document Camera", Price = 329m, CategoryId = categories[0].Id },
            new Product { Name = "Mechanical Keyboard", Price = 139m, CategoryId = categories[1].Id }
        };

        _context.Products.AddRange(products);
        await _context.SaveChangesAsync();

        var orders = new[]
        {
            new Order { CustomerName = "Ada Lovelace", OrderedAtUtc = DateTime.UtcNow.AddDays(-4) },
            new Order { CustomerName = "Grace Hopper", OrderedAtUtc = DateTime.UtcNow.AddDays(-3) },
            new Order { CustomerName = "Alan Turing", OrderedAtUtc = DateTime.UtcNow.AddDays(-2) },
            new Order { CustomerName = "Katherine Johnson", OrderedAtUtc = DateTime.UtcNow.AddDays(-1) }
        };

        _context.Orders.AddRange(orders);
        await _context.SaveChangesAsync();

        _context.OrderItems.AddRange(
            new OrderItem { OrderId = orders[0].Id, ProductId = products[0].Id, Quantity = 2 },
            new OrderItem { OrderId = orders[0].Id, ProductId = products[1].Id, Quantity = 1 },
            new OrderItem { OrderId = orders[1].Id, ProductId = products[2].Id, Quantity = 1 },
            new OrderItem { OrderId = orders[1].Id, ProductId = products[4].Id, Quantity = 1 },
            new OrderItem { OrderId = orders[2].Id, ProductId = products[3].Id, Quantity = 1 },
            new OrderItem { OrderId = orders[2].Id, ProductId = products[5].Id, Quantity = 3 },
            new OrderItem { OrderId = orders[3].Id, ProductId = products[1].Id, Quantity = 2 },
            new OrderItem { OrderId = orders[3].Id, ProductId = products[2].Id, Quantity = 1 });

        await _context.SaveChangesAsync();
        _logger.LogInformation("Seeded the N+1 lesson database.");
    }
}
