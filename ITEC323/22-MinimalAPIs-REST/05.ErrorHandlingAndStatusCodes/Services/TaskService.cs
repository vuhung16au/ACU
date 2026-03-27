using ErrorHandlingAndStatusCodesDemo.Models;

namespace ErrorHandlingAndStatusCodesDemo.Services;

/// <summary>
/// Provides task operations that demonstrate different failure paths.
/// </summary>
public class TaskService
{
    private static readonly string[] AllowedPriorities = ["Low", "Medium", "High"];
    private readonly List<TaskItem> _tasks =
    [
        new TaskItem
        {
            Id = 1,
            Title = "Review API status codes",
            Description = "Compare 400, 404, 409, and 500.",
            Priority = "Medium",
            IsCompleted = false
        }
    ];

    /// <summary>
    /// Finds a task by id.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <returns>The matching task.</returns>
    /// <exception cref="TaskNotFoundException">Thrown when the task does not exist.</exception>
    public TaskItem GetById(int id)
    {
        return _tasks.FirstOrDefault(task => task.Id == id)
            ?? throw new TaskNotFoundException($"Task with id {id} was not found.");
    }

    /// <summary>
    /// Creates a task after validation checks.
    /// </summary>
    /// <param name="request">The request body.</param>
    /// <returns>The created task.</returns>
    /// <exception cref="ArgumentException">Thrown when the request is invalid.</exception>
    /// <exception cref="DuplicateTaskTitleException">Thrown when the title already exists.</exception>
    public TaskItem Create(CreateTaskRequest request)
    {
        if (string.IsNullOrWhiteSpace(request.Title))
        {
            throw new ArgumentException("Title is required.");
        }

        if (string.IsNullOrWhiteSpace(request.Priority))
        {
            throw new ArgumentException("Priority is required.");
        }

        if (AllowedPriorities.Contains(request.Priority.Trim(), StringComparer.OrdinalIgnoreCase) == false)
        {
            throw new ArgumentException("Priority must be Low, Medium, or High.");
        }

        if (_tasks.Any(task => string.Equals(task.Title, request.Title.Trim(), StringComparison.OrdinalIgnoreCase)))
        {
            throw new DuplicateTaskTitleException("A task with this title already exists.");
        }

        var createdTask = new TaskItem
        {
            Id = _tasks.Max(task => task.Id) + 1,
            Title = request.Title.Trim(),
            Description = request.Description?.Trim() ?? string.Empty,
            Priority = request.Priority.Trim(),
            IsCompleted = false
        };

        _tasks.Add(createdTask);
        return createdTask;
    }

    /// <summary>
    /// Throws an unexpected exception for the global error handler demo.
    /// </summary>
    public void ThrowUnexpectedFailure()
    {
        throw new InvalidOperationException("This is a sample unhandled exception.");
    }
}
