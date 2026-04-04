namespace NPlusOne.Models;

/// <summary>
/// Holds captured SQL statements for a comparison run.
/// </summary>
public class QueryCaptureResult
{
    /// <summary>
    /// Gets or sets the title.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the explanation.
    /// </summary>
    public string Explanation { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the command count.
    /// </summary>
    public int QueryCount { get; set; }

    /// <summary>
    /// Gets or sets the captured SQL statements.
    /// </summary>
    public IReadOnlyList<string> SqlStatements { get; set; } = [];

    /// <summary>
    /// Gets or sets the preview rows.
    /// </summary>
    public IReadOnlyList<OrderSummaryViewModel> Orders { get; set; } = [];
}
