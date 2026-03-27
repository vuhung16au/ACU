using CrudWithInMemoryDataDemo.Models;

namespace CrudWithInMemoryDataDemo.Services;

/// <summary>
/// Validates simple task request bodies.
/// </summary>
public class TaskValidator
{
    private static readonly string[] AllowedPriorities = ["Low", "Medium", "High"];

    /// <summary>
    /// Validates a create request.
    /// </summary>
    /// <param name="request">The request to validate.</param>
    /// <returns>An error message, or <see langword="null"/> when the request is valid.</returns>
    public string? ValidateCreate(CreateTaskRequest request)
    {
        return ValidateCore(request.Title, request.Priority);
    }

    /// <summary>
    /// Validates an update request.
    /// </summary>
    /// <param name="request">The request to validate.</param>
    /// <returns>An error message, or <see langword="null"/> when the request is valid.</returns>
    public string? ValidateUpdate(UpdateTaskRequest request)
    {
        return ValidateCore(request.Title, request.Priority);
    }

    private static string? ValidateCore(string? title, string? priority)
    {
        if (string.IsNullOrWhiteSpace(title))
        {
            return "Title is required.";
        }

        if (string.IsNullOrWhiteSpace(priority))
        {
            return "Priority is required.";
        }

        return AllowedPriorities.Contains(priority.Trim(), StringComparer.OrdinalIgnoreCase)
            ? null
            : "Priority must be Low, Medium, or High.";
    }
}
