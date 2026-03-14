using ComprehensiveApp.Data;
using ComprehensiveApp.Models;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;

namespace ComprehensiveApp.Pages;

/// <summary>
/// Displays the module home page and a quick operational summary.
/// </summary>
public class IndexModel : PageModel
{
    private readonly AppDbContext _context;

    /// <summary>
    /// Initializes a new instance of the <see cref="IndexModel"/> class.
    /// </summary>
    /// <param name="context">Application database context.</param>
    public IndexModel(AppDbContext context)
    {
        _context = context;
    }

    /// <summary>
    /// Gets the number of products currently stored in the database.
    /// </summary>
    public int ProductCount { get; private set; }

    /// <summary>
    /// Gets the number of categories.
    /// </summary>
    public int CategoryCount { get; private set; }

    /// <summary>
    /// Gets the number of customers.
    /// </summary>
    public int CustomerCount { get; private set; }

    /// <summary>
    /// Gets the number of orders.
    /// </summary>
    public int OrderCount { get; private set; }

    /// <summary>
    /// Gets the total seeded and created revenue.
    /// </summary>
    public decimal TotalRevenue { get; private set; }

    /// <summary>
    /// Gets products that need replenishment.
    /// </summary>
    public IList<Product> LowStockProducts { get; private set; } = new List<Product>();

    /// <summary>
    /// Gets recent orders displayed on the home page.
    /// </summary>
    public IList<Order> RecentOrders { get; private set; } = new List<Order>();

    /// <summary>
    /// Handles GET requests for the home page.
    /// </summary>
    /// <returns>A task that represents the asynchronous operation.</returns>
    public async Task OnGetAsync()
    {
        ProductCount = await _context.Products.AsNoTracking().CountAsync();
        CategoryCount = await _context.Categories.AsNoTracking().CountAsync();
        CustomerCount = await _context.Customers.AsNoTracking().CountAsync();
        OrderCount = await _context.Orders.AsNoTracking().CountAsync();

        TotalRevenue = await _context.OrderItems
            .AsNoTracking()
            .SumAsync(orderItem => orderItem.UnitPrice * orderItem.Quantity);

        LowStockProducts = await _context.Products
            .AsNoTracking()
            .Include(product => product.Category)
            .Where(product => product.StockQuantity <= 10)
            .OrderBy(product => product.StockQuantity)
            .ThenBy(product => product.Name)
            .ToListAsync();

        RecentOrders = await _context.Orders
            .AsNoTracking()
            .Include(order => order.Customer)
            .Include(order => order.OrderItems)
                .ThenInclude(orderItem => orderItem.Product)
            .OrderByDescending(order => order.OrderDateUtc)
            .Take(5)
            .ToListAsync();
    }
}
