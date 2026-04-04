using EfficientLINQ.Data;
using EfficientLINQ.Models;
using Microsoft.EntityFrameworkCore;

namespace EfficientLINQ.Services;

/// <summary>
/// Builds teaching examples for the Efficient LINQ lesson.
/// </summary>
public class EfficientQueryLabService
{
    private readonly AppDbContext _context;

    /// <summary>
    /// Initializes a new instance of the <see cref="EfficientQueryLabService"/> class.
    /// </summary>
    /// <param name="context">The application database context.</param>
    public EfficientQueryLabService(AppDbContext context)
    {
        _context = context;
    }

    /// <summary>
    /// Builds the query comparison page content.
    /// </summary>
    /// <returns>The populated query lab view model.</returns>
    public async Task<QueryLabViewModel> BuildQueryLabAsync()
    {
        var allProducts = await _context.Products
            .Include(product => product.Category)
            .OrderBy(product => product.Name)
            .ToListAsync();

        var productCount = allProducts.Count;

        var trackedEntityQuery = _context.Products
            .Include(product => product.Category)
            .OrderBy(product => product.Name);

        var projectionQuery = _context.Products
            .AsNoTracking()
            .OrderBy(product => product.Name)
            .Select(product => new ProductListItemViewModel
            {
                Name = product.Name,
                CategoryName = product.Category.Name,
                Price = product.Price,
                QuantityInStock = product.QuantityInStock
            });

        var filteredInMemory = allProducts
            .Where(product => product.Price >= 180m)
            .OrderByDescending(product => product.Price)
            .Select(product => $"{product.Name} | {product.Price:C}")
            .ToList();

        var filteredServerQuery = _context.Products
            .AsNoTracking()
            .Where(product => product.Price >= 180m)
            .OrderByDescending(product => product.Price)
            .Select(product => new
            {
                product.Name,
                product.Price
            });

        var pagedQuery = _context.Products
            .AsNoTracking()
            .OrderBy(product => product.Name)
            .Select(product => new ProductListItemViewModel
            {
                Name = product.Name,
                CategoryName = product.Category.Name,
                Price = product.Price,
                QuantityInStock = product.QuantityInStock
            })
            .Skip(0)
            .Take(5);

        var aggregateQuery = _context.Products
            .AsNoTracking()
            .GroupBy(product => product.Category.Name)
            .Select(group => new
            {
                CategoryName = group.Key,
                ProductCount = group.Count(),
                AveragePrice = group.Average(product => product.Price)
            })
            .OrderBy(result => result.CategoryName);

        var examples = new List<QueryComparisonExample>
        {
            new()
            {
                Title = "Load only the columns the UI needs",
                Problem = "Loading full entities with tracking is heavier than necessary when the page only needs a small summary card or table.",
                WhyBetter = "Projection with AsNoTracking selects fewer columns and avoids change-tracking work for a read-only page.",
                BadCode = """
var products = await _context.Products
    .Include(product => product.Category)
    .OrderBy(product => product.Name)
    .ToListAsync();
""",
                GoodCode = """
var products = await _context.Products
    .AsNoTracking()
    .OrderBy(product => product.Name)
    .Select(product => new ProductListItemViewModel
    {
        Name = product.Name,
        CategoryName = product.Category.Name,
        Price = product.Price,
        QuantityInStock = product.QuantityInStock
    })
    .ToListAsync();
""",
                BadSql = trackedEntityQuery.ToQueryString(),
                GoodSql = projectionQuery.ToQueryString(),
                BadMetric = $"Estimated payload cells: {QueryMetrics.EstimatePayloadCells(productCount, 8)}",
                GoodMetric = $"Estimated payload cells: {QueryMetrics.EstimatePayloadCells(productCount, 4)}",
                PreviewRows = allProducts.Take(3).Select(product => $"{product.Name} | {product.Category.Name} | {product.Price:C}").ToList()
            },
            new()
            {
                Title = "Filter on the server, not after ToList()",
                Problem = "If you call ToList() too early, the database sends every row before the application filters anything.",
                WhyBetter = "Server-side filtering reduces rows transferred over the network and lets PostgreSQL perform the sort and filter work.",
                BadCode = """
var allProducts = await _context.Products.ToListAsync();
var expensiveProducts = allProducts
    .Where(product => product.Price >= 180m)
    .OrderByDescending(product => product.Price)
    .ToList();
""",
                GoodCode = """
var expensiveProducts = await _context.Products
    .AsNoTracking()
    .Where(product => product.Price >= 180m)
    .OrderByDescending(product => product.Price)
    .Select(product => new { product.Name, product.Price })
    .ToListAsync();
""",
                BadSql = _context.Products.OrderBy(product => product.Name).ToQueryString(),
                GoodSql = filteredServerQuery.ToQueryString(),
                BadMetric = $"Rows loaded before filtering: {productCount}",
                GoodMetric = $"Rows loaded after filtering: {await filteredServerQuery.CountAsync()}",
                PreviewRows = filteredInMemory.Take(3).ToList()
            },
            new()
            {
                Title = "Paginate in SQL",
                Problem = "An unpaged list keeps growing as data grows, which means more rows, more memory, and slower rendering.",
                WhyBetter = "Skip/Take limits the database result set and keeps the page fast and predictable.",
                BadCode = """
var products = await _context.Products
    .AsNoTracking()
    .OrderBy(product => product.Name)
    .ToListAsync();
""",
                GoodCode = """
var products = await _context.Products
    .AsNoTracking()
    .OrderBy(product => product.Name)
    .Select(product => new ProductListItemViewModel
    {
        Name = product.Name,
        CategoryName = product.Category.Name,
        Price = product.Price,
        QuantityInStock = product.QuantityInStock
    })
    .Skip(0)
    .Take(5)
    .ToListAsync();
""",
                BadSql = _context.Products.AsNoTracking().OrderBy(product => product.Name).ToQueryString(),
                GoodSql = pagedQuery.ToQueryString(),
                BadMetric = $"Rows returned: {productCount}",
                GoodMetric = "Rows returned: 5",
                PreviewRows = (await pagedQuery.ToListAsync()).Select(product => $"{product.Name} | {product.CategoryName} | {product.Price:C}").ToList()
            },
            new()
            {
                Title = "Let PostgreSQL aggregate for you",
                Problem = "Loading all rows into memory just to group and average them is unnecessary when SQL can do the aggregation directly.",
                WhyBetter = "Translated aggregate queries push the counting and averaging work into PostgreSQL and return a small result set.",
                BadCode = """
var grouped = (await _context.Products
        .Include(product => product.Category)
        .ToListAsync())
    .GroupBy(product => product.Category.Name)
    .Select(group => new
    {
        CategoryName = group.Key,
        ProductCount = group.Count(),
        AveragePrice = group.Average(product => product.Price)
    });
""",
                GoodCode = """
var grouped = await _context.Products
    .AsNoTracking()
    .GroupBy(product => product.Category.Name)
    .Select(group => new
    {
        CategoryName = group.Key,
        ProductCount = group.Count(),
        AveragePrice = group.Average(product => product.Price)
    })
    .ToListAsync();
""",
                BadSql = _context.Products.Include(product => product.Category).ToQueryString(),
                GoodSql = aggregateQuery.ToQueryString(),
                BadMetric = $"Rows loaded into memory: {productCount}",
                GoodMetric = $"Rows returned by SQL: {await aggregateQuery.CountAsync()}",
                PreviewRows = (await aggregateQuery.ToListAsync()).Select(result => $"{result.CategoryName} | {result.ProductCount} products | avg {result.AveragePrice:C}").ToList()
            }
        };

        return new QueryLabViewModel
        {
            Comparisons = examples
        };
    }

    /// <summary>
    /// Gets a paged product list used by the realistic products page.
    /// </summary>
    /// <param name="pageNumber">The requested page number.</param>
    /// <param name="pageSize">The number of items per page.</param>
    /// <returns>The projected products and SQL text.</returns>
    public async Task<(IReadOnlyList<ProductListItemViewModel> Products, string Sql, int TotalCount)> GetPagedProductsAsync(int pageNumber, int pageSize)
    {
        var totalCount = await _context.Products.CountAsync();

        var query = _context.Products
            .AsNoTracking()
            .OrderBy(product => product.Name)
            .Select(product => new ProductListItemViewModel
            {
                Name = product.Name,
                CategoryName = product.Category.Name,
                Price = product.Price,
                QuantityInStock = product.QuantityInStock
            })
            .Skip((pageNumber - 1) * pageSize)
            .Take(pageSize);

        return (await query.ToListAsync(), query.ToQueryString(), totalCount);
    }
}
