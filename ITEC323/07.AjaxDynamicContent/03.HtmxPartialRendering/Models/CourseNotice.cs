namespace HtmxPartialRendering.Models;

/// <summary>
/// Represents one course notice rendered as an HTML fragment.
/// </summary>
public class CourseNotice
{
    /// <summary>
    /// Gets or sets the notice title.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the notice message.
    /// </summary>
    public string Message { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the notice category.
    /// </summary>
    public string Category { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the publish time.
    /// </summary>
    public DateTime PublishedAt { get; set; }
}
