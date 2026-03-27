using RequestBodiesAndValidationDemo.Models;

namespace RequestBodiesAndValidationDemo.Services;

/// <summary>
/// Validates simple task creation requests.
/// </summary>
public class TaskRequestValidator
{
    /// <summary>
    /// Gets the allowed task priority values.
    /// </summary>
    public static readonly string[] AllowedPriorities = ["Low", "Medium", "High"];

    /// <summary>
    /// Validates a create task request.
    /// </summary>
    /// <param name="request">The request to validate.</param>
    /// <returns>An error message, or <see langword="null"/> when the request is valid.</returns>
    public string? Validate(CreateTaskRequest request)
    {
        if (string.IsNullOrWhiteSpace(request.Title))
        {
            return "Title is required.";
        }

        if (string.IsNullOrWhiteSpace(request.Priority))
        {
            return "Priority is required.";
        }

        var normalizedPriority = request.Priority.Trim();
        if (AllowedPriorities.Contains(normalizedPriority, StringComparer.OrdinalIgnoreCase) == false)
        {
            return "Priority must be Low, Medium, or High.";
        }

        return null;
    }

    /// <summary>
    /// Validates a task update request.
    /// </summary>
    /// <param name="request">The request to validate.</param>
    /// <returns>An error message, or <see langword="null"/> when the request is valid.</returns>
    public string? Validate(UpdateTaskRequest request)
    {
        return Validate((CreateTaskRequest)request);
    }
}
