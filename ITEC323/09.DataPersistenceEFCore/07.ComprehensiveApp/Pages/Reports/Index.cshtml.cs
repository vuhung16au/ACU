using ComprehensiveApp.Data;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;

namespace ComprehensiveApp.Pages.Reports;

/// <summary>
/// Displays LINQ-powered analytics for the comprehensive app.
/// </summary>
public class IndexModel : PageModel
{
    private readonly AppDbContext _context;

    public IndexModel(AppDbContext context)
    {
        _context = context;
    }

    public decimal AverageOrderValue { get; private set; }

    public bool HasPendingOrders { get; private set; }

    public IList<CategoryRevenueSummary> RevenueByCategory { get; private set; } = new List<CategoryRevenueSummary>();

    public IList<ProductPerformanceSummary> TopProducts { get; private set; } = new List<ProductPerformanceSummary>();

    public IList<InventoryAlertSummary> InventoryAlerts { get; private set; } = new List<InventoryAlertSummary>();

    public IList<CustomerOrderSummary> TopCustomers { get; private set; } = new List<CustomerOrderSummary>();

    public async Task OnGetAsync()
    {
        var orderTotals = await _context.Orders
            .AsNoTracking()
            .Select(order => new
            {
                order.Id,
                Total = order.OrderItems.Sum(orderItem => orderItem.UnitPrice * orderItem.Quantity)
            })
            .ToListAsync();

        AverageOrderValue = orderTotals.Any()
            ? orderTotals.Average(order => order.Total)
            : 0;

        HasPendingOrders = await _context.Orders
            .AsNoTracking()
            .AnyAsync(order => order.Status == "Pending");

        var revenueByCategoryRows = await _context.OrderItems
            .AsNoTracking()
            .GroupBy(orderItem => orderItem.Product!.Category!.Name)
            .Select(group => new
            {
                CategoryName = group.Key,
                QuantitySold = group.Sum(orderItem => orderItem.Quantity),
                Revenue = group.Sum(orderItem => orderItem.UnitPrice * orderItem.Quantity)
            })
            .OrderByDescending(row => row.Revenue)
            .ToListAsync();

        RevenueByCategory = revenueByCategoryRows
            .Select(row => new CategoryRevenueSummary(row.CategoryName, row.QuantitySold, row.Revenue))
            .ToList();

        var topProductRows = await _context.OrderItems
            .AsNoTracking()
            .GroupBy(orderItem => orderItem.Product!.Name)
            .Select(group => new
            {
                ProductName = group.Key,
                QuantitySold = group.Sum(orderItem => orderItem.Quantity),
                Revenue = group.Sum(orderItem => orderItem.UnitPrice * orderItem.Quantity)
            })
            .OrderByDescending(row => row.QuantitySold)
            .ThenBy(row => row.ProductName)
            .Take(5)
            .ToListAsync();

        TopProducts = topProductRows
            .Select(row => new ProductPerformanceSummary(row.ProductName, row.QuantitySold, row.Revenue))
            .ToList();

        InventoryAlerts = await _context.Products
            .AsNoTracking()
            .Where(product => product.StockQuantity <= 10)
            .OrderBy(product => product.StockQuantity)
            .ThenBy(product => product.Name)
            .Select(product => new InventoryAlertSummary(product.Name, product.Category!.Name, product.StockQuantity))
            .ToListAsync();

        var topCustomerRows = await _context.Orders
            .AsNoTracking()
            .GroupBy(order => order.Customer!.FullName)
            .Select(group => new
            {
                CustomerName = group.Key,
                OrderCount = group.Count(),
                TotalSpend = group.Sum(order => order.OrderItems.Sum(orderItem => orderItem.UnitPrice * orderItem.Quantity))
            })
            .OrderByDescending(row => row.OrderCount)
            .ThenBy(row => row.CustomerName)
            .Take(5)
            .ToListAsync();

        TopCustomers = topCustomerRows
            .Select(row => new CustomerOrderSummary(row.CustomerName, row.OrderCount, row.TotalSpend))
            .ToList();
    }

    public record CategoryRevenueSummary(string CategoryName, int QuantitySold, decimal Revenue);

    public record ProductPerformanceSummary(string ProductName, int QuantitySold, decimal Revenue);

    public record InventoryAlertSummary(string ProductName, string CategoryName, int StockQuantity);

    public record CustomerOrderSummary(string CustomerName, int OrderCount, decimal TotalSpend);
}