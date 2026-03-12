namespace BasicFetchAPI.Models;

/// <summary>
/// Represents a small summary shown above the task table.
/// </summary>
public class StudyTaskSummary
{
    /// <summary>
    /// Gets or sets the total number of tasks in memory.
    /// </summary>
    public int TotalTasks { get; set; }

    /// <summary>
    /// Gets or sets the number of completed tasks.
    /// </summary>
    public int CompletedTasks { get; set; }

    /// <summary>
    /// Gets or sets the number of tasks still in progress.
    /// </summary>
    public int PendingTasks { get; set; }

    /// <summary>
    /// Gets or sets the time the summary was generated.
    /// </summary>
    public DateTime GeneratedAt { get; set; }
}
