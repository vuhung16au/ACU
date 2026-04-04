using EfficientLINQ.Models;
using Microsoft.EntityFrameworkCore;

namespace EfficientLINQ.Data;

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

        var categories = new[]
        {
            new Category { Name = "Accessories" },
            new Category { Name = "Classroom Devices" },
            new Category { Name = "Office Gear" }
        };

        _context.Categories.AddRange(categories);
        await _context.SaveChangesAsync();

        var products = new[]
        {
            CreateProduct("Wireless Presenter", 69.00m, 48, "Office Gear", "Compact presenter used in weekly lectures."),
            CreateProduct("Laptop Stand", 89.00m, 120, "Accessories", "Adjustable stand used in laptop setup demos."),
            CreateProduct("USB-C Dock", 159.00m, 36, "Accessories", "Popular with students who connect multiple displays."),
            CreateProduct("Drawing Tablet", 249.00m, 18, "Classroom Devices", "Used in design and annotation exercises."),
            CreateProduct("Classroom Tablet Mini", 199.00m, 42, "Classroom Devices", "Small teaching tablet for shared activities."),
            CreateProduct("Portable Projector", 499.00m, 8, "Office Gear", "Used in collaborative presentation sessions."),
            CreateProduct("Noise Cancelling Headset", 179.00m, 65, "Accessories", "Useful in media production tasks."),
            CreateProduct("Document Camera", 329.00m, 11, "Classroom Devices", "Captures sketches and paper prototypes."),
            CreateProduct("Standing Desk Converter", 219.00m, 15, "Office Gear", "Improves ergonomics for long lab sessions."),
            CreateProduct("Mechanical Keyboard", 139.00m, 54, "Accessories", "A frequent student accessory request."),
            CreateProduct("Reference Monitor", 589.00m, 7, "Office Gear", "Color-accurate monitor for design reviews."),
            CreateProduct("Student Chromebook Cart", 899.00m, 5, "Classroom Devices", "Stores and charges a class set of devices.")
        };

        _context.Products.AddRange(products);
        await _context.SaveChangesAsync();
        _logger.LogInformation("Seeded the Efficient LINQ lesson database.");

        Product CreateProduct(string name, decimal price, int stock, string categoryName, string description)
        {
            return new Product
            {
                Name = name,
                Price = price,
                QuantityInStock = stock,
                Description = description,
                CategoryId = categories.Single(category => category.Name == categoryName).Id
            };
        }
    }
}
