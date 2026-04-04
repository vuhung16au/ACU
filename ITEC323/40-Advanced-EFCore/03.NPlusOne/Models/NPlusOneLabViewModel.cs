namespace NPlusOne.Models;

/// <summary>
/// Holds the N+1 comparison page content.
/// </summary>
public class NPlusOneLabViewModel
{
    /// <summary>
    /// Gets or sets the naive comparison result.
    /// </summary>
    public QueryCaptureResult Naive { get; set; } = new();

    /// <summary>
    /// Gets or sets the improved comparison result.
    /// </summary>
    public QueryCaptureResult Improved { get; set; } = new();
}
