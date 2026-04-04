using Dapper;
using Microsoft.EntityFrameworkCore;
using RawSQLDapper.Data;
using RawSQLDapper.Models;

namespace RawSQLDapper.Services;

/// <summary>
/// Builds the reporting comparison between EF Core and Dapper.
/// </summary>
public class ReportingLabService
{
    private const int DefaultDaysLookback = 30;
    private readonly AppDbContext _context;

    /// <summary>
    /// Initializes a new instance of the <see cref="ReportingLabService"/> class.
    /// </summary>
    /// <param name="context">The database context.</param>
    public ReportingLabService(AppDbContext context)
    {
        _context = context;
    }

    /// <summary>
    /// Builds the reports page view model.
    /// </summary>
    /// <returns>The reports page content.</returns>
    public async Task<ReportsPageViewModel> BuildReportsPageAsync()
    {
        var cutoffDate = DateTime.UtcNow.AddDays(-DefaultDaysLookback);

        var efQuery = _context.SaleRecords
            .AsNoTracking()
            .Where(record => record.SoldAtUtc >= cutoffDate)
            .GroupBy(record => new
            {
                record.Product.Name,
                record.Product.CategoryName,
                record.Product.Price
            })
            .Select(group => new SalesReportRow
            {
                ProductName = group.Key.Name,
                CategoryName = group.Key.CategoryName,
                UnitsSold = group.Sum(record => record.QuantitySold),
                Revenue = group.Sum(record => record.QuantitySold) * group.Key.Price
            })
            .OrderByDescending(row => row.Revenue)
            .ThenBy(row => row.ProductName);

        var dapperSql = """
SELECT
    p."Name" AS "ProductName",
    p."CategoryName" AS "CategoryName",
    SUM(sr."QuantitySold") AS "UnitsSold",
    SUM(sr."QuantitySold" * p."Price") AS "Revenue"
FROM "SaleRecords" sr
INNER JOIN "Products" p ON sr."ProductId" = p."Id"
WHERE sr."SoldAtUtc" >= @CutoffDate
GROUP BY p."Name", p."CategoryName", p."Price"
ORDER BY "Revenue" DESC, "ProductName";
""";

        var efRows = await efQuery.ToListAsync();

        await _context.Database.OpenConnectionAsync();
        try
        {
            var dapperRows = (await _context.Database
                .GetDbConnection()
                .QueryAsync<SalesReportRow>(dapperSql, new { CutoffDate = cutoffDate }))
                .ToList();

            return new ReportsPageViewModel
            {
                EfCoreRows = efRows,
                DapperRows = dapperRows,
                EfCoreSql = efQuery.ToQueryString(),
                DapperSql = dapperSql
            };
        }
        finally
        {
            await _context.Database.CloseConnectionAsync();
        }
    }

    /// <summary>
    /// Gets a simple product list for the EF Core page.
    /// </summary>
    /// <returns>The products and SQL text.</returns>
    public async Task<(IReadOnlyList<Product> Products, string Sql)> GetProductsAsync()
    {
        var query = _context.Products
            .AsNoTracking()
            .OrderBy(product => product.Name);

        return (await query.ToListAsync(), query.ToQueryString());
    }
}
