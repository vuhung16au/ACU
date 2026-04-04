namespace PerformanceOptimization.Models;

/// <summary>
/// Represents the data shown on the optimization lab page.
/// </summary>
public class OptimizationLabPageViewModel
{
    /// <summary>
    /// Gets or sets the recent sales window start date.
    /// </summary>
    public DateTime RecentWindowUtc { get; set; }

    /// <summary>
    /// Gets or sets the minimum revenue filter used by the benchmark.
    /// </summary>
    public decimal MinimumRevenue { get; set; }

    /// <summary>
    /// Gets or sets the requested page size.
    /// </summary>
    public int PageSize { get; set; }

    /// <summary>
    /// Gets or sets the benchmark scenarios shown to students.
    /// </summary>
    public IReadOnlyList<BenchmarkScenarioResult> Scenarios { get; set; } = [];

    /// <summary>
    /// Gets or sets the optimized sample rows.
    /// </summary>
    public IReadOnlyList<ProductLeaderboardRow> OptimizedPreview { get; set; } = [];

    /// <summary>
    /// Gets or sets the index discussion note.
    /// </summary>
    public string IndexDiscussion { get; set; } = string.Empty;

    /// <summary>
    /// Gets the baseline scenario if it exists.
    /// </summary>
    public BenchmarkScenarioResult? BaselineScenario => Scenarios.FirstOrDefault();
}
