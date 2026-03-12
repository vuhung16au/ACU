namespace ViewComponentsDemo.Models;

/// <summary>
/// Represents a recent activity item shown in the recent items view component.
/// </summary>
public class RecentItem
{
    /// <summary>
    /// Gets or sets the user identifier linked to the activity.
    /// </summary>
    public int UserId { get; set; }

    /// <summary>
    /// Gets or sets the activity title.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the activity category.
    /// </summary>
    public string Category { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the number of hours ago the activity happened.
    /// </summary>
    public int HoursAgo { get; set; }
}
