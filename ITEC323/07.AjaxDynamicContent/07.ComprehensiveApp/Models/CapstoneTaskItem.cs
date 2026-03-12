namespace ComprehensiveApp.Models;

/// <summary>
/// Represents one capstone task item stored in the local application.
/// </summary>
public class CapstoneTaskItem
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
    /// Gets or sets the AJAX technique category.
    /// </summary>
    public string Technique { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the short task description.
    /// </summary>
    public string? Description { get; set; }

    /// <summary>
    /// Gets or sets a value indicating whether the task is complete.
    /// </summary>
    public bool IsCompleted { get; set; }

    /// <summary>
    /// Gets or sets the last update time.
    /// </summary>
    public DateTime LastUpdated { get; set; }
}
