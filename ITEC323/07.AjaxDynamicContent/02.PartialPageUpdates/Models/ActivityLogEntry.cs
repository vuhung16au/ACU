namespace PartialPageUpdates.Models;

/// <summary>
/// Represents one item appended to the activity log panel.
/// </summary>
public class ActivityLogEntry
{
    /// <summary>
    /// Gets or sets the entry identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the activity name.
    /// </summary>
    public string ActivityName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the student reflection message.
    /// </summary>
    public string Reflection { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the time the entry was created.
    /// </summary>
    public DateTime CreatedAt { get; set; }
}
