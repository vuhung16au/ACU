namespace PerformanceOptimization.Models;

/// <summary>
/// Represents one stage in the performance comparison lab.
/// </summary>
public class BenchmarkScenarioResult
{
    /// <summary>
    /// Gets or sets the stage title.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets a short description of the scenario.
    /// </summary>
    public string Summary { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the measured duration in milliseconds.
    /// </summary>
    public long DurationMilliseconds { get; set; }

    /// <summary>
    /// Gets or sets the number of rows produced for the page.
    /// </summary>
    public int VisibleRows { get; set; }

    /// <summary>
    /// Gets or sets the estimated number of rows materialized from the database.
    /// </summary>
    public int EstimatedRowsTouched { get; set; }

    /// <summary>
    /// Gets or sets the generated SQL shown to students.
    /// </summary>
    public string Sql { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the teaching note for why this stage matters.
    /// </summary>
    public string WhatImproved { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets an example LINQ snippet for the stage.
    /// </summary>
    public string CodeSnippet { get; set; } = string.Empty;

    /// <summary>
    /// Calculates the percentage reduction in rows touched compared with the baseline.
    /// </summary>
    /// <param name="baselineRowsTouched">The baseline number of touched rows.</param>
    /// <returns>The rounded percentage reduction.</returns>
    public int CalculateRowReductionPercent(int baselineRowsTouched)
    {
        if (baselineRowsTouched <= 0 || EstimatedRowsTouched >= baselineRowsTouched)
        {
            return 0;
        }

        var reduction = 1m - (EstimatedRowsTouched / (decimal)baselineRowsTouched);
        return (int)Math.Round(reduction * 100m, MidpointRounding.AwayFromZero);
    }
}
