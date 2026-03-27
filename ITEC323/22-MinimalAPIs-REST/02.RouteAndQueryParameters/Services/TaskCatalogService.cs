using RouteAndQueryParametersDemo.Models;

namespace RouteAndQueryParametersDemo.Services;

/// <summary>
/// Supplies a small in-memory task list for route and query examples.
/// </summary>
public class TaskCatalogService
{
    private readonly List<TaskItem> _tasks =
    [
        new TaskItem
        {
            Id = 1,
            Title = "Read the Minimal API notes",
            Description = "Review the overview before coding.",
            IsCompleted = true,
            Priority = "Low"
        },
        new TaskItem
        {
            Id = 2,
            Title = "Build the first task endpoint",
            Description = "Practice a route parameter example.",
            IsCompleted = false,
            Priority = "High"
        },
        new TaskItem
        {
            Id = 3,
            Title = "Try a query string filter",
            Description = "Send requests with ?priority=Medium.",
            IsCompleted = false,
            Priority = "Medium"
        }
    ];

    /// <summary>
    /// Finds a task by its identifier.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <returns>The matching task, or <see langword="null"/> when it is not found.</returns>
    public TaskItem? GetById(int id)
    {
        return _tasks.FirstOrDefault(task => task.Id == id);
    }

    /// <summary>
    /// Filters tasks by priority when a priority value is provided.
    /// </summary>
    /// <param name="priority">The requested priority.</param>
    /// <returns>A filtered task list.</returns>
    public IReadOnlyList<TaskItem> FilterByPriority(string? priority)
    {
        if (string.IsNullOrWhiteSpace(priority))
        {
            return _tasks;
        }

        return _tasks
            .Where(task => string.Equals(task.Priority, priority.Trim(), StringComparison.OrdinalIgnoreCase))
            .ToList();
    }

    /// <summary>
    /// Filters tasks by completion state when a query value is provided.
    /// </summary>
    /// <param name="completed">The requested completion state.</param>
    /// <returns>A filtered task list.</returns>
    public IReadOnlyList<TaskItem> FilterByCompletion(bool? completed)
    {
        if (completed is null)
        {
            return _tasks;
        }

        return _tasks
            .Where(task => task.IsCompleted == completed.Value)
            .ToList();
    }
}
