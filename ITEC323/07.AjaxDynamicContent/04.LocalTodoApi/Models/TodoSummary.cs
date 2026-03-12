namespace LocalTodoApi.Models;

/// <summary>
/// Represents summary data shown above the todo list.
/// </summary>
public class TodoSummary
{
    /// <summary>
    /// Gets or sets the number of todo items.
    /// </summary>
    public int TotalItems { get; set; }

    /// <summary>
    /// Gets or sets the number of completed items.
    /// </summary>
    public int CompletedItems { get; set; }

    /// <summary>
    /// Gets or sets the number of pending items.
    /// </summary>
    public int PendingItems { get; set; }

    /// <summary>
    /// Gets or sets the last refresh time.
    /// </summary>
    public DateTime GeneratedAt { get; set; }
}
