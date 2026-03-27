namespace ErrorHandlingAndStatusCodesDemo.Models;

/// <summary>
/// Represents the request used to create a task.
/// </summary>
public class CreateTaskRequest
{
    /// <summary>
    /// Gets or sets the task title.
    /// </summary>
    public string? Title { get; set; }

    /// <summary>
    /// Gets or sets the task description.
    /// </summary>
    public string? Description { get; set; }

    /// <summary>
    /// Gets or sets the task priority.
    /// </summary>
    public string? Priority { get; set; }
}
