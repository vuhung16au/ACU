using Microsoft.EntityFrameworkCore;
using NPlusOne.Data;
using NPlusOne.Models;

namespace NPlusOne.Services;

/// <summary>
/// Builds the N+1 comparison scenarios.
/// </summary>
public class NPlusOneLabService
{
    private readonly AppDbContext _context;
    private readonly QueryCaptureInterceptor _queryCaptureInterceptor;

    /// <summary>
    /// Initializes a new instance of the <see cref="NPlusOneLabService"/> class.
    /// </summary>
    /// <param name="context">The database context.</param>
    /// <param name="queryCaptureInterceptor">The query capture interceptor.</param>
    public NPlusOneLabService(AppDbContext context, QueryCaptureInterceptor queryCaptureInterceptor)
    {
        _context = context;
        _queryCaptureInterceptor = queryCaptureInterceptor;
    }

    /// <summary>
    /// Builds the side-by-side N+1 comparison.
    /// </summary>
    /// <returns>The comparison view model.</returns>
    public async Task<NPlusOneLabViewModel> BuildLabAsync()
    {
        var naive = await RunNaiveScenarioAsync();
        var improved = await RunImprovedScenarioAsync();

        return new NPlusOneLabViewModel
        {
            Naive = naive,
            Improved = improved
        };
    }

    /// <summary>
    /// Gets the improved order summaries for the realistic orders page.
    /// </summary>
    /// <returns>The projected orders and SQL.</returns>
    public async Task<(IReadOnlyList<OrderSummaryViewModel> Orders, string Sql)> GetImprovedOrdersPageAsync()
    {
        var query = _context.Orders
            .AsNoTracking()
            .Include(order => order.Items)
            .ThenInclude(item => item.Product)
            .OrderByDescending(order => order.OrderedAtUtc)
            .AsSingleQuery();

        var orders = await query.ToListAsync();
        var projectedOrders = orders.Select(MapOrderSummary).ToList();

        return (projectedOrders, query.ToQueryString());
    }

    private async Task<QueryCaptureResult> RunNaiveScenarioAsync()
    {
        var sessionId = _queryCaptureInterceptor.BeginCapture();
        var orders = await _context.Orders
            .AsNoTracking()
            .OrderByDescending(order => order.OrderedAtUtc)
            .ToListAsync();

        var orderSummaries = new List<OrderSummaryViewModel>();

        foreach (var order in orders)
        {
            var items = await _context.OrderItems
                .AsNoTracking()
                .Where(item => item.OrderId == order.Id)
                .ToListAsync();

            var productNames = new List<string>();
            decimal totalValue = 0m;

            foreach (var item in items)
            {
                var product = await _context.Products
                    .AsNoTracking()
                    .FirstAsync(product => product.Id == item.ProductId);

                productNames.Add(product.Name);
                totalValue += item.Quantity * product.Price;
            }

            orderSummaries.Add(new OrderSummaryViewModel
            {
                OrderId = order.Id,
                CustomerName = order.CustomerName,
                OrderedAtUtc = order.OrderedAtUtc,
                ItemCount = items.Sum(item => item.Quantity),
                TotalValue = totalValue,
                ProductNames = productNames
            });
        }

        var sql = _queryCaptureInterceptor.EndCapture(sessionId);

        return new QueryCaptureResult
        {
            Title = "Naive repeated-query workflow",
            Explanation = "This version loads the orders first, then queries order items for each order, then queries each product inside another loop. The page looks simple, but the database receives many extra commands.",
            QueryCount = sql.Count,
            SqlStatements = sql,
            Orders = orderSummaries
        };
    }

    private async Task<QueryCaptureResult> RunImprovedScenarioAsync()
    {
        var sessionId = _queryCaptureInterceptor.BeginCapture();

        var orders = await _context.Orders
            .AsNoTracking()
            .Include(order => order.Items)
            .ThenInclude(item => item.Product)
            .OrderByDescending(order => order.OrderedAtUtc)
            .AsSingleQuery()
            .ToListAsync();

        var sql = _queryCaptureInterceptor.EndCapture(sessionId);

        return new QueryCaptureResult
        {
            Title = "Improved projection workflow",
            Explanation = "This version asks PostgreSQL for the shape the UI actually needs. EF Core translates the summary into far fewer SQL commands, which removes the repeated per-order and per-item lookups.",
            QueryCount = sql.Count,
            SqlStatements = sql,
            Orders = orders.Select(MapOrderSummary).ToList()
        };
    }

    private static OrderSummaryViewModel MapOrderSummary(Order order)
    {
        return new OrderSummaryViewModel
        {
            OrderId = order.Id,
            CustomerName = order.CustomerName,
            OrderedAtUtc = order.OrderedAtUtc,
            ItemCount = order.Items.Sum(item => item.Quantity),
            TotalValue = order.Items.Sum(item => item.Quantity * item.Product.Price),
            ProductNames = order.Items
                .OrderBy(item => item.Product.Name)
                .Select(item => item.Product.Name)
                .ToList()
        };
    }
}
