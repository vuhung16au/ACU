namespace HtmxPartialRendering.Models;

/// <summary>
/// Represents one reflection message appended to the activity feed.
/// </summary>
public class CheckInMessage
{
    /// <summary>
    /// Gets or sets the entry identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the task the student completed.
    /// </summary>
    public string TaskName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the student reflection text.
    /// </summary>
    public string Reflection { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the creation time.
    /// </summary>
    public DateTime CreatedAt { get; set; }
}
