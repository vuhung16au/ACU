using ComprehensiveMinimalApiDemo.Models;

namespace ComprehensiveMinimalApiDemo.Services;

/// <summary>
/// Stores tasks in memory for the final combined example.
/// </summary>
public class InMemoryTaskRepository : ITaskRepository
{
    private readonly List<TaskItem> _tasks =
    [
        new TaskItem
        {
            Id = 1,
            Title = "Plan the final demo",
            Description = "List the endpoints and expected responses.",
            IsCompleted = false,
            Priority = "High"
        },
        new TaskItem
        {
            Id = 2,
            Title = "Write the summary endpoint",
            Description = "Show total, completed, and pending tasks.",
            IsCompleted = true,
            Priority = "Medium"
        }
    ];

    private int _nextId = 3;

    /// <inheritdoc />
    public IReadOnlyList<TaskItem> GetAll()
    {
        return _tasks.Select(CloneTask).ToList();
    }

    /// <inheritdoc />
    public TaskItem? GetById(int id)
    {
        var task = _tasks.FirstOrDefault(item => item.Id == id);
        return task is null ? null : CloneTask(task);
    }

    /// <inheritdoc />
    public TaskItem Add(TaskItem task)
    {
        task.Id = _nextId++;
        _tasks.Add(CloneTask(task));
        return CloneTask(task);
    }

    /// <inheritdoc />
    public void Update(TaskItem task)
    {
        var existingTask = _tasks.First(item => item.Id == task.Id);
        existingTask.Title = task.Title;
        existingTask.Description = task.Description;
        existingTask.IsCompleted = task.IsCompleted;
        existingTask.Priority = task.Priority;
    }

    /// <inheritdoc />
    public void Delete(TaskItem task)
    {
        var existingTask = _tasks.First(item => item.Id == task.Id);
        _tasks.Remove(existingTask);
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
