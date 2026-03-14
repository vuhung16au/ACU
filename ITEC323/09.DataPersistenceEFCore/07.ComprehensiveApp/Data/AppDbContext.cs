using ComprehensiveApp.Models;
using Microsoft.EntityFrameworkCore;

namespace ComprehensiveApp.Data;

/// <summary>
/// Database context for the comprehensive final application.
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
    /// Gets or sets product categories.
    /// </summary>
    public DbSet<Category> Categories { get; set; } = default!;

    /// <summary>
    /// Gets or sets customers.
    /// </summary>
    public DbSet<Customer> Customers { get; set; } = default!;

    /// <summary>
    /// Gets or sets orders.
    /// </summary>
    public DbSet<Order> Orders { get; set; } = default!;

    /// <summary>
    /// Gets or sets order line items.
    /// </summary>
    public DbSet<OrderItem> OrderItems { get; set; } = default!;

    /// <summary>
    /// Configures relationships and seed data.
    /// </summary>
    /// <param name="modelBuilder">Model builder instance.</param>
    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        modelBuilder.Entity<Product>()
            .Property(product => product.Price)
            .HasPrecision(10, 2);

        modelBuilder.Entity<OrderItem>()
            .Property(orderItem => orderItem.UnitPrice)
            .HasPrecision(10, 2);

        modelBuilder.Entity<Category>()
            .HasMany(category => category.Products)
            .WithOne(product => product.Category)
            .HasForeignKey(product => product.CategoryId)
            .OnDelete(DeleteBehavior.Restrict);

        modelBuilder.Entity<Customer>()
            .HasMany(customer => customer.Orders)
            .WithOne(order => order.Customer)
            .HasForeignKey(order => order.CustomerId)
            .OnDelete(DeleteBehavior.Restrict);

        modelBuilder.Entity<Order>()
            .HasMany(order => order.OrderItems)
            .WithOne(orderItem => orderItem.Order)
            .HasForeignKey(orderItem => orderItem.OrderId)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<Product>()
            .HasMany(product => product.OrderItems)
            .WithOne(orderItem => orderItem.Product)
            .HasForeignKey(orderItem => orderItem.ProductId)
            .OnDelete(DeleteBehavior.Restrict);

        SeedReferenceData(modelBuilder);
    }

    private static void SeedReferenceData(ModelBuilder modelBuilder)
    {
        var jan15 = new DateTime(2026, 1, 15, 8, 30, 0, DateTimeKind.Utc);
        var feb10 = new DateTime(2026, 2, 10, 13, 45, 0, DateTimeKind.Utc);
        var mar01 = new DateTime(2026, 3, 1, 10, 15, 0, DateTimeKind.Utc);

        modelBuilder.Entity<Category>().HasData(
            new Category { Id = 1, Name = "Hardware" },
            new Category { Id = 2, Name = "Office" },
            new Category { Id = 3, Name = "Accessories" },
            new Category { Id = 4, Name = "Networking" }
        );

        modelBuilder.Entity<Customer>().HasData(
            new Customer { Id = 1, FullName = "Alex Johnson", Email = "alex.johnson@example.com", City = "Brisbane", CreatedAtUtc = jan15 },
            new Customer { Id = 2, FullName = "Morgan Lee", Email = "morgan.lee@example.com", City = "Sydney", CreatedAtUtc = feb10 },
            new Customer { Id = 3, FullName = "Taylor Chen", Email = "taylor.chen@example.com", City = "Melbourne", CreatedAtUtc = mar01 }
        );

        modelBuilder.Entity<Product>().HasData(
            new Product { Id = 1, Name = "Mechanical Keyboard", Description = "Hot-swappable keyboard for developers.", Price = 149.00m, StockQuantity = 18, CategoryId = 3, CreatedAtUtc = jan15 },
            new Product { Id = 2, Name = "USB-C Dock", Description = "Docking station with dual display outputs.", Price = 219.00m, StockQuantity = 9, CategoryId = 1, CreatedAtUtc = jan15.AddDays(2) },
            new Product { Id = 3, Name = "Ergonomic Chair", Description = "Comfortable office chair for long work sessions.", Price = 399.00m, StockQuantity = 5, CategoryId = 2, CreatedAtUtc = feb10 },
            new Product { Id = 4, Name = "Wi-Fi 6 Router", Description = "Small-office router with VLAN support.", Price = 279.00m, StockQuantity = 7, CategoryId = 4, CreatedAtUtc = feb10.AddDays(1) },
            new Product { Id = 5, Name = "4K Monitor", Description = "27-inch display for design and analytics.", Price = 499.00m, StockQuantity = 6, CategoryId = 1, CreatedAtUtc = mar01 },
            new Product { Id = 6, Name = "Notebook Set", Description = "Pack of premium notebooks for workshops.", Price = 24.50m, StockQuantity = 42, CategoryId = 2, CreatedAtUtc = mar01.AddDays(1) }
        );

        modelBuilder.Entity<Order>().HasData(
            new Order { Id = 1, CustomerId = 1, OrderDateUtc = feb10, Status = "Paid", Notes = "Deliver to front desk." },
            new Order { Id = 2, CustomerId = 2, OrderDateUtc = mar01, Status = "Pending", Notes = "Bundle items into one shipment." }
        );

        modelBuilder.Entity<OrderItem>().HasData(
            new OrderItem { Id = 1, OrderId = 1, ProductId = 1, Quantity = 1, UnitPrice = 149.00m },
            new OrderItem { Id = 2, OrderId = 1, ProductId = 6, Quantity = 3, UnitPrice = 24.50m },
            new OrderItem { Id = 3, OrderId = 2, ProductId = 2, Quantity = 1, UnitPrice = 219.00m }
        );
    }
}

