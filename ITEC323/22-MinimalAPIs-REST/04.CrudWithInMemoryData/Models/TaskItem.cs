namespace CrudWithInMemoryDataDemo.Models;

/// <summary>
/// Represents a task in the in-memory repository.
/// </summary>
public class TaskItem
{
    /// <summary>
    /// Gets or sets the task identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the task title.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the task description.
    /// </summary>
    public string Description { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets a value indicating whether the task is completed.
    /// </summary>
    public bool IsCompleted { get; set; }

    /// <summary>
    /// Gets or sets the task priority.
    /// </summary>
    public string Priority { get; set; } = string.Empty;
}
