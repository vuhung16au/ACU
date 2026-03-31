namespace _02_LiveNotificationsDashboard.Models;

/// <summary>
/// Represents one dashboard notification shown to connected clients.
/// </summary>
public class NotificationItem
{
    /// <summary>
    /// Gets or sets the notification identifier.
    /// </summary>
    public Guid Id { get; set; }

    /// <summary>
    /// Gets or sets the short notification title.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the notification detail text.
    /// </summary>
    public string Message { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the notification severity.
    /// </summary>
    public string Severity { get; set; } = "info";

    /// <summary>
    /// Gets or sets the notification source label.
    /// </summary>
    public string Source { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the time the notification was created.
    /// </summary>
    public DateTime CreatedAtUtc { get; set; }
}
