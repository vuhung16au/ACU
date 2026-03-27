namespace RequestBodiesAndValidationDemo.Models;

/// <summary>
/// Represents the JSON body used to create a task.
/// </summary>
public class CreateTaskRequest
{
    /// <summary>
    /// Gets or sets the task title.
    /// </summary>
    public string? Title { get; set; }

    /// <summary>
    /// Gets or sets the optional description.
    /// </summary>
    public string? Description { get; set; }

    /// <summary>
    /// Gets or sets the selected priority.
    /// </summary>
    public string? Priority { get; set; }
}
