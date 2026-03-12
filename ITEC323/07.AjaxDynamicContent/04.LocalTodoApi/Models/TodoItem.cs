namespace LocalTodoApi.Models;

/// <summary>
/// Represents one local todo item stored in memory.
/// </summary>
public class TodoItem
{
    /// <summary>
    /// Gets or sets the todo item identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the todo title.
    /// </summary>
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the todo description.
    /// </summary>
    public string? Description { get; set; }

    /// <summary>
    /// Gets or sets the todo category.
    /// </summary>
    public string Category { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the optional due date.
    /// </summary>
    public DateTime? DueDate { get; set; }

    /// <summary>
    /// Gets or sets a value indicating whether the todo item is completed.
    /// </summary>
    public bool IsCompleted { get; set; }

    /// <summary>
    /// Gets or sets the last update time.
    /// </summary>
    public DateTime LastUpdated { get; set; }
}
