using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using PerformanceOptimization.Models;
using PerformanceOptimization.Services;

namespace PerformanceOptimization.Pages.OptimizationLab;

/// <summary>
/// Represents the benchmark comparison page.
/// </summary>
public class IndexModel : PageModel
{
    private readonly PerformanceLabService _performanceLabService;

    /// <summary>
    /// Initializes a new instance of the <see cref="IndexModel"/> class.
    /// </summary>
    /// <param name="performanceLabService">The service that builds the lab page.</param>
    public IndexModel(PerformanceLabService performanceLabService)
    {
        _performanceLabService = performanceLabService;
    }

    /// <summary>
    /// Gets the built lab page.
    /// </summary>
    [BindProperty(SupportsGet = true)]
    public OptimizationLabPageViewModel LabPage { get; private set; } = new();

    /// <summary>
    /// Handles GET requests for the benchmark page.
    /// </summary>
    /// <param name="minimumRevenue">The minimum revenue filter.</param>
    public async Task OnGetAsync(decimal minimumRevenue = 3500m)
    {
        LabPage = await _performanceLabService.BuildPageAsync(minimumRevenue, 8);
    }
}
