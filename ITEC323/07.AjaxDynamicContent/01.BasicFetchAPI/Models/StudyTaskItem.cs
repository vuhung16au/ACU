namespace BasicFetchAPI.Models;

/// <summary>
/// Represents one task stored by the in-memory service and returned to the browser as JSON.
/// </summary>
public class StudyTaskItem
{
    /// <summary>
    /// Gets or sets the unique task identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the task title.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the task category.
    /// </summary>
    public string Category { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets an optional due date.
    /// </summary>
    public DateTime? DueDate { get; set; }

    /// <summary>
    /// Gets or sets optional notes for the task.
    /// </summary>
    public string? Notes { get; set; }

    /// <summary>
    /// Gets or sets a value indicating whether the task has been completed.
    /// </summary>
    public bool IsCompleted { get; set; }

    /// <summary>
    /// Gets or sets the last time the task was changed.
    /// </summary>
    public DateTime LastUpdated { get; set; }
}
