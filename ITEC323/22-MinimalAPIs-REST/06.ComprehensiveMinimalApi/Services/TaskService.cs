using ComprehensiveMinimalApiDemo.Models;

namespace ComprehensiveMinimalApiDemo.Services;

/// <summary>
/// Coordinates validation, filtering, CRUD operations, and summary data.
/// </summary>
public class TaskService
{
    private static readonly string[] AllowedPriorities = ["Low", "Medium", "High"];
    private readonly ITaskRepository _repository;

    /// <summary>
    /// Initializes a new instance of the <see cref="TaskService"/> class.
    /// </summary>
    /// <param name="repository">The underlying task repository.</param>
    public TaskService(ITaskRepository repository)
    {
        _repository = repository;
    }

    /// <summary>
    /// Gets all tasks.
    /// </summary>
    /// <returns>The task list.</returns>
    public IReadOnlyList<TaskItem> GetAll()
    {
        return _repository.GetAll();
    }

    /// <summary>
    /// Searches tasks by priority and completion state.
    /// </summary>
    /// <param name="priority">The optional priority filter.</param>
    /// <param name="completed">The optional completion filter.</param>
    /// <returns>The filtered task list.</returns>
    public IReadOnlyList<TaskItem> Search(string? priority, bool? completed)
    {
        var tasks = _repository.GetAll().AsEnumerable();

        if (string.IsNullOrWhiteSpace(priority) == false)
        {
            tasks = tasks.Where(task => string.Equals(task.Priority, priority.Trim(), StringComparison.OrdinalIgnoreCase));
        }

        if (completed is not null)
        {
            tasks = tasks.Where(task => task.IsCompleted == completed.Value);
        }

        return tasks.ToList();
    }

    /// <summary>
    /// Gets one task by id.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <returns>The matching task.</returns>
    /// <exception cref="TaskNotFoundException">Thrown when the task does not exist.</exception>
    public TaskItem GetById(int id)
    {
        return _repository.GetById(id)
            ?? throw new TaskNotFoundException($"Task with id {id} was not found.");
    }

    /// <summary>
    /// Creates a task.
    /// </summary>
    /// <param name="request">The creation request.</param>
    /// <returns>The created task.</returns>
    public TaskItem Create(CreateTaskRequest request)
    {
        var normalizedTitle = Validate(request.Title, request.Priority);

        EnsureUniqueTitle(normalizedTitle, null);

        return _repository.Add(new TaskItem
        {
            Title = normalizedTitle,
            Description = request.Description?.Trim() ?? string.Empty,
            Priority = request.Priority!.Trim(),
            IsCompleted = false
        });
    }

    /// <summary>
    /// Updates a task.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <param name="request">The updated values.</param>
    /// <returns>The updated task.</returns>
    public TaskItem Update(int id, UpdateTaskRequest request)
    {
        var existingTask = _repository.GetById(id)
            ?? throw new TaskNotFoundException($"Task with id {id} was not found.");

        var normalizedTitle = Validate(request.Title, request.Priority);

        EnsureUniqueTitle(normalizedTitle, id);

        existingTask.Title = normalizedTitle;
        existingTask.Description = request.Description?.Trim() ?? string.Empty;
        existingTask.Priority = request.Priority!.Trim();
        existingTask.IsCompleted = request.IsCompleted;

        _repository.Update(existingTask);
        return existingTask;
    }

    /// <summary>
    /// Deletes a task by id.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    public void Delete(int id)
    {
        var task = _repository.GetById(id)
            ?? throw new TaskNotFoundException($"Task with id {id} was not found.");

        _repository.Delete(task);
    }

    /// <summary>
    /// Builds a small summary of task counts.
    /// </summary>
    /// <returns>The task summary.</returns>
    public TaskSummary GetSummary()
    {
        var tasks = _repository.GetAll();
        var completed = tasks.Count(task => task.IsCompleted);

        return new TaskSummary
        {
            Total = tasks.Count,
            Completed = completed,
            Pending = tasks.Count - completed
        };
    }

    private void EnsureUniqueTitle(string normalizedTitle, int? currentId)
    {
        var conflictExists = _repository.GetAll().Any(task =>
            task.Id != currentId &&
            string.Equals(task.Title, normalizedTitle, StringComparison.OrdinalIgnoreCase));

        if (conflictExists)
        {
            throw new DuplicateTaskTitleException("A task with this title already exists.");
        }
    }

    private static string Validate(string? title, string? priority)
    {
        if (string.IsNullOrWhiteSpace(title))
        {
            throw new ArgumentException("Title is required.");
        }

        if (string.IsNullOrWhiteSpace(priority))
        {
            throw new ArgumentException("Priority is required.");
        }

        var normalizedPriority = priority.Trim();
        if (AllowedPriorities.Contains(normalizedPriority, StringComparer.OrdinalIgnoreCase) == false)
        {
            throw new ArgumentException("Priority must be Low, Medium, or High.");
        }

        return title.Trim();
    }
}
