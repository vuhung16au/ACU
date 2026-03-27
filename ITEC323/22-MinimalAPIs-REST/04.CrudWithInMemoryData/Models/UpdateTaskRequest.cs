namespace CrudWithInMemoryDataDemo.Models;

/// <summary>
/// Represents the request body used to update a task.
/// </summary>
public class UpdateTaskRequest : CreateTaskRequest
{
    /// <summary>
    /// Gets or sets a value indicating whether the task is completed.
    /// </summary>
    public bool IsCompleted { get; set; }
}
