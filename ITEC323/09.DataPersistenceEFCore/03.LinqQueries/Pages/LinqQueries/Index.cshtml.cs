using LinqQueries.Data;
using LinqQueries.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;

namespace LinqQueries.Pages.LinqQueries;

/// <summary>
/// Demonstrates common LINQ query patterns using EF Core.
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
    /// Gets products filtered with Where and OrderBy.
    /// </summary>
    public IList<Product> AffordableProducts { get; private set; } = new List<Product>();

    /// <summary>
    /// Gets products loaded with related reviews using Include.
    /// </summary>
    public IList<Product> ProductsWithReviews { get; private set; } = new List<Product>();

    /// <summary>
    /// Gets projected results produced with Select.
    /// </summary>
    public IList<ProductProjection> ProjectedProducts { get; private set; } = new List<ProductProjection>();

    /// <summary>
    /// Gets grouped aggregates produced with GroupBy.
    /// </summary>
    public IList<StockBandSummary> StockBands { get; private set; } = new List<StockBandSummary>();

    /// <summary>
    /// Gets paged products generated via Skip and Take.
    /// </summary>
    public IList<Product> PagedProducts { get; private set; } = new List<Product>();

    /// <summary>
    /// Gets whether the catalog has premium products.
    /// </summary>
    public bool HasPremiumProducts { get; private set; }

    /// <summary>
    /// Gets total item count in the catalog.
    /// </summary>
    public int TotalProducts { get; private set; }

    /// <summary>
    /// Gets total inventory value using Sum.
    /// </summary>
    public decimal TotalInventoryValue { get; private set; }

    /// <summary>
    /// Gets average product price.
    /// </summary>
    public decimal AveragePrice { get; private set; }

    /// <summary>
    /// Gets current page index for pagination.
    /// </summary>
    public int PageNumber { get; private set; }

    /// <summary>
    /// Gets the fixed page size.
    /// </summary>
    public int PageSize => 5;

    /// <summary>
    /// Handles GET requests for the LINQ explorer page.
    /// </summary>
    /// <param name="pageNumber">Optional page number for Skip/Take demo.</param>
    public async Task OnGetAsync([FromQuery] int pageNumber = 1)
    {
        PageNumber = pageNumber < 1 ? 1 : pageNumber;

        AffordableProducts = await _context.Products
            .AsNoTracking()
            .Where(p => p.Price <= 80)
            .OrderBy(p => p.Price)
            .ThenBy(p => p.Name)
            .ToListAsync();

        ProductsWithReviews = await _context.Products
            .AsNoTracking()
            .Include(p => p.Reviews)
            .OrderBy(p => p.Name)
            .Take(5)
            .ToListAsync();

        ProjectedProducts = await _context.Products
            .AsNoTracking()
            .OrderByDescending(p => p.StockQuantity)
            .Take(6)
            .Select(p => new ProductProjection
            {
                Name = p.Name,
                Price = p.Price,
                StockQuantity = p.StockQuantity,
                InventoryValue = p.Price * p.StockQuantity
            })
            .ToListAsync();

        StockBands = await _context.Products
            .AsNoTracking()
            .GroupBy(p => p.StockQuantity >= 60 ? "High Stock (>= 60)" : "Low Stock (< 60)")
            .Select(g => new StockBandSummary
            {
                Band = g.Key,
                ProductCount = g.Count(),
                AveragePrice = Math.Round(g.Average(x => x.Price), 2)
            })
            .OrderByDescending(x => x.ProductCount)
            .ToListAsync();

        PagedProducts = await _context.Products
            .AsNoTracking()
            .OrderBy(p => p.Name)
            .Skip((PageNumber - 1) * PageSize)
            .Take(PageSize)
            .ToListAsync();

        TotalProducts = await _context.Products.AsNoTracking().CountAsync();
        HasPremiumProducts = await _context.Products.AsNoTracking().AnyAsync(p => p.Price > 200);
        AveragePrice = Math.Round(await _context.Products.AsNoTracking().AverageAsync(p => p.Price), 2);
        TotalInventoryValue = Math.Round(await _context.Products.AsNoTracking().SumAsync(p => p.Price * p.StockQuantity), 2);
    }

    /// <summary>
    /// Represents a projected LINQ Select result.
    /// </summary>
    public class ProductProjection
    {
        public string Name { get; set; } = string.Empty;

        public decimal Price { get; set; }

        public int StockQuantity { get; set; }

        public decimal InventoryValue { get; set; }
    }

    /// <summary>
    /// Represents grouped aggregate results.
    /// </summary>
    public class StockBandSummary
    {
        public string Band { get; set; } = string.Empty;

        public int ProductCount { get; set; }

        public decimal AveragePrice { get; set; }
    }
}
