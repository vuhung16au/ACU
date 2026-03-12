namespace HtmxPartialRendering.Models;

/// <summary>
/// Represents a highlighted HTMX learning tip.
/// </summary>
public class CourseTip
{
    /// <summary>
    /// Gets or sets the tip title.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the tip explanation.
    /// </summary>
    public string Description { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the recommended next action.
    /// </summary>
    public string NextAction { get; set; } = string.Empty;
}
