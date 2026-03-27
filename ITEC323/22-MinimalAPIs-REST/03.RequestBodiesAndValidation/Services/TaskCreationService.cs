using RequestBodiesAndValidationDemo.Models;

namespace RequestBodiesAndValidationDemo.Services;

/// <summary>
/// Creates a response model for a new task request.
/// </summary>
public class TaskCreationService
{
    /// <summary>
    /// Creates a new task item from the request data.
    /// </summary>
    /// <param name="request">The validated request.</param>
    /// <returns>A created task model.</returns>
    public TaskItem Create(CreateTaskRequest request)
    {
        return new TaskItem
        {
            Id = 0,
            Title = request.Title!.Trim(),
            Description = request.Description?.Trim() ?? string.Empty,
            Priority = NormalizePriority(request.Priority!),
            IsCompleted = false
        };
    }

    /// <summary>
    /// Creates an updated task model from the request data.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <param name="request">The validated update request.</param>
    /// <returns>An updated task model.</returns>
    public TaskItem CreateUpdatedTask(int id, UpdateTaskRequest request)
    {
        return new TaskItem
        {
            Id = id,
            Title = request.Title!.Trim(),
            Description = request.Description?.Trim() ?? string.Empty,
            Priority = NormalizePriority(request.Priority!),
            IsCompleted = request.IsCompleted
        };
    }

    private static string NormalizePriority(string priority)
    {
        return priority.Trim().ToLowerInvariant() switch
        {
            "low" => "Low",
            "medium" => "Medium",
            _ => "High"
        };
    }
}
