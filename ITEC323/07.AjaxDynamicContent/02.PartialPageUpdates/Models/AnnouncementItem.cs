namespace PartialPageUpdates.Models;

/// <summary>
/// Represents one announcement displayed in the updates panel.
/// </summary>
public class AnnouncementItem
{
    /// <summary>
    /// Gets or sets the unique announcement identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the announcement title.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the announcement message.
    /// </summary>
    public string Message { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the name of the week or topic.
    /// </summary>
    public string Topic { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the publish time.
    /// </summary>
    public DateTime PublishedAt { get; set; }
}
