namespace ComprehensiveMinimalApiDemo.Models;

/// <summary>
/// Represents a small summary of task counts.
/// </summary>
public class TaskSummary
{
    /// <summary>
    /// Gets or sets the total number of tasks.
    /// </summary>
    public int Total { get; set; }

    /// <summary>
    /// Gets or sets the number of completed tasks.
    /// </summary>
    public int Completed { get; set; }

    /// <summary>
    /// Gets or sets the number of pending tasks.
    /// </summary>
    public int Pending { get; set; }
}
