using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using PerformanceOptimization.Models;
using PerformanceOptimization.Services;

namespace PerformanceOptimization.Pages.Products;

/// <summary>
/// Represents the optimized leaderboard page.
/// </summary>
public class IndexModel : PageModel
{
    private readonly PerformanceLabService _performanceLabService;

    /// <summary>
    /// Initializes a new instance of the <see cref="IndexModel"/> class.
    /// </summary>
    /// <param name="performanceLabService">The service that builds the optimized leaderboard.</param>
    public IndexModel(PerformanceLabService performanceLabService)
    {
        _performanceLabService = performanceLabService;
    }

    /// <summary>
    /// Gets the current page number.
    /// </summary>
    [BindProperty(SupportsGet = true)]
    public int PageNumber { get; set; } = 1;

    /// <summary>
    /// Gets the rendered rows.
    /// </summary>
    public IReadOnlyList<ProductLeaderboardRow> Rows { get; private set; } = [];

    /// <summary>
    /// Gets the SQL shown to the student.
    /// </summary>
    public string GeneratedSql { get; private set; } = string.Empty;

    /// <summary>
    /// Handles GET requests for the optimized leaderboard page.
    /// </summary>
    public async Task OnGetAsync()
    {
        PageNumber = Math.Max(PageNumber, 1);
        Rows = await _performanceLabService.GetOptimizedLeaderboardAsync(PageNumber, 10, 3500m);
        GeneratedSql = _performanceLabService.GetOptimizedSql(3500m, PageNumber, 10);
    }
}
