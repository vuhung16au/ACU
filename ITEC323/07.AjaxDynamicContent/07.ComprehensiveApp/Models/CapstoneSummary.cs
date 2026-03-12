namespace ComprehensiveApp.Models;

/// <summary>
/// Represents the dashboard summary shown above the capstone task table.
/// </summary>
public class CapstoneSummary
{
    /// <summary>
    /// Gets or sets the total number of tasks.
    /// </summary>
    public int TotalTasks { get; set; }

    /// <summary>
    /// Gets or sets the number of completed tasks.
    /// </summary>
    public int CompletedTasks { get; set; }

    /// <summary>
    /// Gets or sets the number of pending tasks.
    /// </summary>
    public int PendingTasks { get; set; }

    /// <summary>
    /// Gets or sets the short dashboard status text.
    /// </summary>
    public string StatusText { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the last refresh time.
    /// </summary>
    public DateTime RefreshedAt { get; set; }
}
