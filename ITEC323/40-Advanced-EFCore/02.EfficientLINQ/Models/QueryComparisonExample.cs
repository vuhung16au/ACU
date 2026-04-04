namespace EfficientLINQ.Models;

/// <summary>
/// Represents a side-by-side query comparison shown on the teaching page.
/// </summary>
public class QueryComparisonExample
{
    /// <summary>
    /// Gets or sets the title.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the problem summary.
    /// </summary>
    public string Problem { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the explanation of the improvement.
    /// </summary>
    public string WhyBetter { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the inefficient code snippet.
    /// </summary>
    public string BadCode { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the improved code snippet.
    /// </summary>
    public string GoodCode { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the SQL linked to the inefficient version.
    /// </summary>
    public string BadSql { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the SQL linked to the efficient version.
    /// </summary>
    public string GoodSql { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the inefficient metric summary.
    /// </summary>
    public string BadMetric { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the efficient metric summary.
    /// </summary>
    public string GoodMetric { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets preview rows displayed to the learner.
    /// </summary>
    public IReadOnlyList<string> PreviewRows { get; set; } = [];
}
