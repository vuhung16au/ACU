using CrudWithInMemoryDataDemo.Models;

namespace CrudWithInMemoryDataDemo.Services;

/// <summary>
/// Stores tasks in memory for a simple CRUD example.
/// </summary>
public class InMemoryTaskRepository : ITaskRepository
{
    private readonly List<TaskItem> _tasks =
    [
        new TaskItem
        {
            Id = 1,
            Title = "Plan the CRUD routes",
            Description = "List the endpoints before coding.",
            IsCompleted = false,
            Priority = "Medium"
        },
        new TaskItem
        {
            Id = 2,
            Title = "Create the repository",
            Description = "Keep the storage in memory for this lesson.",
            IsCompleted = true,
            Priority = "High"
        }
    ];

    private int _nextId = 3;

    /// <inheritdoc />
    public IReadOnlyList<TaskItem> GetAll()
    {
        return _tasks
            .Select(CloneTask)
            .ToList();
    }

    /// <inheritdoc />
    public TaskItem? GetById(int id)
    {
        var task = _tasks.FirstOrDefault(item => item.Id == id);
        return task is null ? null : CloneTask(task);
    }

    /// <inheritdoc />
    public TaskItem Create(CreateTaskRequest request)
    {
        var task = new TaskItem
        {
            Id = _nextId++,
            Title = request.Title!.Trim(),
            Description = request.Description?.Trim() ?? string.Empty,
            Priority = request.Priority!.Trim(),
            IsCompleted = false
        };

        _tasks.Add(task);
        return CloneTask(task);
    }

    /// <inheritdoc />
    public TaskItem? Update(int id, UpdateTaskRequest request)
    {
        var task = _tasks.FirstOrDefault(item => item.Id == id);
        if (task is null)
        {
            return null;
        }

        task.Title = request.Title!.Trim();
        task.Description = request.Description?.Trim() ?? string.Empty;
        task.Priority = request.Priority!.Trim();
        task.IsCompleted = request.IsCompleted;

        return CloneTask(task);
    }

    /// <inheritdoc />
    public bool Delete(int id)
    {
        var task = _tasks.FirstOrDefault(item => item.Id == id);
        if (task is null)
        {
            return false;
        }

        _tasks.Remove(task);
        return true;
    }

    private static TaskItem CloneTask(TaskItem task)
    {
        return new TaskItem
        {
            Id = task.Id,
            Title = task.Title,
            Description = task.Description,
            IsCompleted = task.IsCompleted,
            Priority = task.Priority
        };
    }
}
