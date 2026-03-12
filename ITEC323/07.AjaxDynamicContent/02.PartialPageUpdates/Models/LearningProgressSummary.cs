namespace PartialPageUpdates.Models;

/// <summary>
/// Represents the summary cards shown on the dashboard.
/// </summary>
public class LearningProgressSummary
{
    /// <summary>
    /// Gets or sets the number of completed activities.
    /// </summary>
    public int CompletedActivities { get; set; }

    /// <summary>
    /// Gets or sets the number of activities still waiting.
    /// </summary>
    public int RemainingActivities { get; set; }

    /// <summary>
    /// Gets or sets the next lesson focus area.
    /// </summary>
    public string NextFocus { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the last refresh time.
    /// </summary>
    public DateTime RefreshedAt { get; set; }
}
