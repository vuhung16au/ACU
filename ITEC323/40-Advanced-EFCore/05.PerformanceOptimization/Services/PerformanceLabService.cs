using System.Diagnostics;
using Microsoft.EntityFrameworkCore;
using PerformanceOptimization.Data;
using PerformanceOptimization.Models;

namespace PerformanceOptimization.Services;

/// <summary>
/// Builds the optimization lab scenarios and the paged leaderboard.
/// </summary>
public class PerformanceLabService
{
    private static readonly Func<AppDbContext, DateTime, decimal, int, int, IAsyncEnumerable<ProductLeaderboardRow>> CompiledLeaderboardQuery =
        EF.CompileAsyncQuery(
            (AppDbContext dbContext, DateTime recentWindowUtc, decimal minimumRevenue, int skip, int take) =>
                dbContext.Products
                    .AsNoTracking()
                    .Select(product => new
                    {
                        product.Id,
                        product.Name,
                        product.CategoryName,
                        product.Price,
                        product.QuantityInStock,
                        UnitsSoldLast30Days = product.SaleRecords
                            .Where(record => record.SoldAtUtc >= recentWindowUtc)
                            .Sum(record => (int?)record.QuantitySold) ?? 0
                    })
                    .Select(product => new ProductLeaderboardRow
                    {
                        ProductId = product.Id,
                        ProductName = product.Name,
                        CategoryName = product.CategoryName,
                        Price = product.Price,
                        QuantityInStock = product.QuantityInStock,
                        UnitsSoldLast30Days = product.UnitsSoldLast30Days,
                        RevenueLast30Days = product.Price * product.UnitsSoldLast30Days
                    })
                    .Where(product => product.RevenueLast30Days >= minimumRevenue)
                    .OrderByDescending(product => product.RevenueLast30Days)
                    .ThenBy(product => product.ProductName)
                    .Skip(skip)
                    .Take(take));

    private readonly AppDbContext _dbContext;

    /// <summary>
    /// Initializes a new instance of the <see cref="PerformanceLabService"/> class.
    /// </summary>
    /// <param name="dbContext">The lesson database context.</param>
    public PerformanceLabService(AppDbContext dbContext)
    {
        _dbContext = dbContext;
    }

    /// <summary>
    /// Builds the optimization lab page.
    /// </summary>
    /// <param name="minimumRevenue">The minimum revenue filter.</param>
    /// <param name="pageSize">The preview page size.</param>
    /// <returns>The populated page view model.</returns>
    public async Task<OptimizationLabPageViewModel> BuildPageAsync(decimal minimumRevenue, int pageSize)
    {
        var recentWindowUtc = DateTime.UtcNow.AddDays(-30);

        var baseline = await BuildBaselineScenarioAsync(recentWindowUtc, minimumRevenue, pageSize);
        var projected = await BuildProjectedScenarioAsync(recentWindowUtc, minimumRevenue, pageSize);
        var paged = await BuildPagedScenarioAsync(recentWindowUtc, minimumRevenue, pageSize);
        var compiled = await BuildCompiledScenarioAsync(recentWindowUtc, minimumRevenue, pageSize);

        return new OptimizationLabPageViewModel
        {
            RecentWindowUtc = recentWindowUtc,
            MinimumRevenue = minimumRevenue,
            PageSize = pageSize,
            Scenarios = [baseline, projected, paged, compiled],
            OptimizedPreview = compiledRows(compiled, paged),
            IndexDiscussion = "The database index on (ProductId, SoldAtUtc) helps the recent-sales filter, while the product index on (CategoryName, Price) supports common browsing filters. Even with indexes, you still want smaller projections and pagination so PostgreSQL reads fewer rows and returns less data."
        };

        static IReadOnlyList<ProductLeaderboardRow> compiledRows(BenchmarkScenarioResult compiledStage, BenchmarkScenarioResult pagedStage)
            => compiledStage is ScenarioWithRows compiledRowsStage
                ? compiledRowsStage.Rows
                : pagedStage is ScenarioWithRows pagedRowsStage
                    ? pagedRowsStage.Rows
                    : [];
    }

    /// <summary>
    /// Gets a paged optimized leaderboard for the products page.
    /// </summary>
    /// <param name="pageNumber">The requested page number.</param>
    /// <param name="pageSize">The number of rows per page.</param>
    /// <param name="minimumRevenue">The minimum revenue filter.</param>
    /// <returns>The optimized leaderboard rows.</returns>
    public async Task<IReadOnlyList<ProductLeaderboardRow>> GetOptimizedLeaderboardAsync(int pageNumber, int pageSize, decimal minimumRevenue)
    {
        var recentWindowUtc = DateTime.UtcNow.AddDays(-30);
        var skip = Math.Max(pageNumber - 1, 0) * pageSize;
        return await CompiledLeaderboardQuery(_dbContext, recentWindowUtc, minimumRevenue, skip, pageSize).ToListAsync();
    }

    /// <summary>
    /// Builds the SQL string for the optimized leaderboard query.
    /// </summary>
    /// <param name="minimumRevenue">The minimum revenue filter.</param>
    /// <param name="pageNumber">The requested page number.</param>
    /// <param name="pageSize">The number of rows per page.</param>
    /// <returns>The generated SQL string.</returns>
    public string GetOptimizedSql(decimal minimumRevenue, int pageNumber, int pageSize)
    {
        var recentWindowUtc = DateTime.UtcNow.AddDays(-30);
        var skip = Math.Max(pageNumber - 1, 0) * pageSize;

        return BuildOptimizedQuery(recentWindowUtc, minimumRevenue)
            .Skip(skip)
            .Take(pageSize)
            .ToQueryString();
    }

    private async Task<BenchmarkScenarioResult> BuildBaselineScenarioAsync(DateTime recentWindowUtc, decimal minimumRevenue, int pageSize)
    {
        var query = _dbContext.Products
            .Include(product => product.SaleRecords);

        var stopwatch = Stopwatch.StartNew();
        var materializedProducts = await query.ToListAsync();
        var rows = materializedProducts
            .Select(product => new ProductLeaderboardRow
            {
                ProductId = product.Id,
                ProductName = product.Name,
                CategoryName = product.CategoryName,
                Price = product.Price,
                QuantityInStock = product.QuantityInStock,
                UnitsSoldLast30Days = product.SaleRecords
                    .Where(record => record.SoldAtUtc >= recentWindowUtc)
                    .Sum(record => record.QuantitySold),
                RevenueLast30Days = product.Price * product.SaleRecords
                    .Where(record => record.SoldAtUtc >= recentWindowUtc)
                    .Sum(record => record.QuantitySold)
            })
            .Where(product => product.RevenueLast30Days >= minimumRevenue)
            .OrderByDescending(product => product.RevenueLast30Days)
            .ThenBy(product => product.ProductName)
            .Take(pageSize)
            .ToList();
        stopwatch.Stop();

        return new ScenarioWithRows
        {
            Title = "Stage 1: Slow baseline",
            Summary = "Loads full product entities plus every related sale record, then filters and sorts in memory.",
            DurationMilliseconds = stopwatch.ElapsedMilliseconds,
            VisibleRows = rows.Count,
            EstimatedRowsTouched = materializedProducts.Count + materializedProducts.Sum(product => product.SaleRecords.Count),
            Sql = query.ToQueryString(),
            WhatImproved = "Nothing yet. This stage exists so students can see why loading an entire object graph is expensive for a reporting page.",
            CodeSnippet =
                """
                var products = await _dbContext.Products
                    .Include(product => product.SaleRecords)
                    .ToListAsync();

                var ranked = products
                    .Select(product => ...)
                    .Where(product => product.RevenueLast30Days >= minimumRevenue)
                    .OrderByDescending(product => product.RevenueLast30Days)
                    .Take(pageSize)
                    .ToList();
                """,
            Rows = rows
        };
    }

    private async Task<BenchmarkScenarioResult> BuildProjectedScenarioAsync(DateTime recentWindowUtc, decimal minimumRevenue, int pageSize)
    {
        var query = BuildProjectedQuery(recentWindowUtc, minimumRevenue);
        var stopwatch = Stopwatch.StartNew();
        var rows = await query.Take(pageSize).ToListAsync();
        stopwatch.Stop();

        return new ScenarioWithRows
        {
            Title = "Stage 2: Projection + AsNoTracking",
            Summary = "Moves filtering and aggregation into SQL, selects only leaderboard columns, and disables change tracking.",
            DurationMilliseconds = stopwatch.ElapsedMilliseconds,
            VisibleRows = rows.Count,
            EstimatedRowsTouched = rows.Count * 2,
            Sql = query.Take(pageSize).ToQueryString(),
            WhatImproved = "The payload is smaller because PostgreSQL returns only the fields needed for the leaderboard. EF Core also skips tracking work for read-only results.",
            CodeSnippet =
                """
                var query = _dbContext.Products
                    .AsNoTracking()
                    .Select(product => new ProductLeaderboardRow
                    {
                        ProductId = product.Id,
                        ProductName = product.Name,
                        UnitsSoldLast30Days = product.SaleRecords
                            .Where(record => record.SoldAtUtc >= recentWindowUtc)
                            .Sum(record => (int?)record.QuantitySold) ?? 0
                    });
                """,
            Rows = rows
        };
    }

    private async Task<BenchmarkScenarioResult> BuildPagedScenarioAsync(DateTime recentWindowUtc, decimal minimumRevenue, int pageSize)
    {
        var query = BuildOptimizedQuery(recentWindowUtc, minimumRevenue).Take(pageSize);
        var stopwatch = Stopwatch.StartNew();
        var rows = await query.ToListAsync();
        stopwatch.Stop();

        return new ScenarioWithRows
        {
            Title = "Stage 3: Add pagination",
            Summary = "Keeps the optimized projection, then limits the leaderboard to the rows the student can actually see.",
            DurationMilliseconds = stopwatch.ElapsedMilliseconds,
            VisibleRows = rows.Count,
            EstimatedRowsTouched = rows.Count,
            Sql = query.ToQueryString(),
            WhatImproved = "Pagination prevents the database, network, and browser from processing unseen rows. This matters immediately as the dataset grows.",
            CodeSnippet =
                """
                var query = BuildOptimizedQuery(recentWindowUtc, minimumRevenue)
                    .OrderByDescending(product => product.RevenueLast30Days)
                    .Skip((pageNumber - 1) * pageSize)
                    .Take(pageSize);
                """,
            Rows = rows
        };
    }

    private async Task<BenchmarkScenarioResult> BuildCompiledScenarioAsync(DateTime recentWindowUtc, decimal minimumRevenue, int pageSize)
    {
        var skip = 0;
        var stopwatch = Stopwatch.StartNew();
        var rows = await CompiledLeaderboardQuery(_dbContext, recentWindowUtc, minimumRevenue, skip, pageSize).ToListAsync();
        stopwatch.Stop();

        return new ScenarioWithRows
        {
            Title = "Stage 4: Compiled query for a hot path",
            Summary = "Reuses the same optimized query shape with `EF.CompileAsyncQuery` so EF Core does less query compilation work on repeated requests.",
            DurationMilliseconds = stopwatch.ElapsedMilliseconds,
            VisibleRows = rows.Count,
            EstimatedRowsTouched = rows.Count,
            Sql = BuildOptimizedQuery(recentWindowUtc, minimumRevenue).Skip(skip).Take(pageSize).ToQueryString(),
            WhatImproved = "Compiled queries are a final polish for hot paths. They are not a substitute for fixing the bigger issues first.",
            CodeSnippet =
                """
                private static readonly Func<AppDbContext, DateTime, decimal, int, int, IAsyncEnumerable<ProductLeaderboardRow>>
                    CompiledLeaderboardQuery =
                        EF.CompileAsyncQuery((AppDbContext dbContext, DateTime recentWindowUtc, decimal minimumRevenue, int skip, int take) =>
                            BuildOptimizedProjection(dbContext, recentWindowUtc, minimumRevenue)
                                .Skip(skip)
                                .Take(take));
                """,
            Rows = rows
        };
    }

    private IQueryable<ProductLeaderboardRow> BuildProjectedQuery(DateTime recentWindowUtc, decimal minimumRevenue)
    {
        return _dbContext.Products
            .AsNoTracking()
            .Select(product => new
            {
                product.Id,
                product.Name,
                product.CategoryName,
                product.Price,
                product.QuantityInStock,
                UnitsSoldLast30Days = product.SaleRecords
                    .Where(record => record.SoldAtUtc >= recentWindowUtc)
                    .Sum(record => (int?)record.QuantitySold) ?? 0
            })
            .Select(product => new ProductLeaderboardRow
            {
                ProductId = product.Id,
                ProductName = product.Name,
                CategoryName = product.CategoryName,
                Price = product.Price,
                QuantityInStock = product.QuantityInStock,
                UnitsSoldLast30Days = product.UnitsSoldLast30Days,
                RevenueLast30Days = product.Price * product.UnitsSoldLast30Days
            })
            .Where(product => product.RevenueLast30Days >= minimumRevenue)
            .OrderByDescending(product => product.RevenueLast30Days)
            .ThenBy(product => product.ProductName);
    }

    private IQueryable<ProductLeaderboardRow> BuildOptimizedQuery(DateTime recentWindowUtc, decimal minimumRevenue)
    {
        return BuildProjectedQuery(recentWindowUtc, minimumRevenue);
    }

    private sealed class ScenarioWithRows : BenchmarkScenarioResult
    {
        public IReadOnlyList<ProductLeaderboardRow> Rows { get; init; } = [];
    }
}
