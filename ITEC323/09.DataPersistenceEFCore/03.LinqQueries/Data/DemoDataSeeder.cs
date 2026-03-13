using LinqQueries.Models;

namespace LinqQueries.Data;

/// <summary>
/// Seeds in-memory demo data used by LINQ examples.
/// </summary>
public static class DemoDataSeeder
{
    /// <summary>
    /// Seeds products and reviews if the store is empty.
    /// </summary>
    /// <param name="context">Application database context.</param>
    public static void Seed(AppDbContext context)
    {
        if (context.Products.Any())
        {
            return;
        }

        var products = new List<Product>
        {
            new() { Name = "Wireless Mouse", Description = "Ergonomic 2.4GHz mouse", Price = 24.90m, StockQuantity = 120 },
            new() { Name = "Mechanical Keyboard", Description = "Blue switch full-size keyboard", Price = 79.00m, StockQuantity = 45 },
            new() { Name = "USB-C Hub", Description = "6-in-1 travel hub", Price = 49.50m, StockQuantity = 80 },
            new() { Name = "Noise-Canceling Headphones", Description = "Over-ear Bluetooth model", Price = 149.00m, StockQuantity = 30 },
            new() { Name = "4K Monitor", Description = "27-inch IPS display", Price = 329.00m, StockQuantity = 18 },
            new() { Name = "Laptop Stand", Description = "Adjustable aluminum stand", Price = 39.90m, StockQuantity = 95 },
            new() { Name = "Webcam", Description = "1080p USB webcam", Price = 59.00m, StockQuantity = 67 },
            new() { Name = "Portable SSD", Description = "1TB high-speed storage", Price = 119.00m, StockQuantity = 52 },
            new() { Name = "Office Chair", Description = "Breathable mesh back", Price = 189.00m, StockQuantity = 22 },
            new() { Name = "Desk Lamp", Description = "LED lamp with touch controls", Price = 34.50m, StockQuantity = 140 }
        };

        context.Products.AddRange(products);
        context.SaveChanges();

        var reviews = new List<ProductReview>
        {
            new() { ProductId = products[0].Id, Rating = 5, Comment = "Great value and comfort." },
            new() { ProductId = products[1].Id, Rating = 4, Comment = "Excellent typing experience." },
            new() { ProductId = products[3].Id, Rating = 5, Comment = "Impressive noise cancellation." },
            new() { ProductId = products[5].Id, Rating = 4, Comment = "Stable and easy to adjust." },
            new() { ProductId = products[7].Id, Rating = 5, Comment = "Fast and compact." },
            new() { ProductId = products[8].Id, Rating = 3, Comment = "Comfortable but assembly takes time." }
        };

        context.ProductReviews.AddRange(reviews);
        context.SaveChanges();
    }
}
