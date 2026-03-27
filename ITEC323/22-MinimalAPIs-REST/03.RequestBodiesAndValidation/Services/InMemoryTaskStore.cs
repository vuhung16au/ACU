using RequestBodiesAndValidationDemo.Models;

namespace RequestBodiesAndValidationDemo.Services;

/// <summary>
/// Stores tasks in memory for request-body and validation examples.
/// </summary>
public class InMemoryTaskStore
{
    private readonly List<TaskItem> _tasks =
    [
        new TaskItem
        {
            Id = 1,
            Title = "Plan the CRUD routes",
            Description = "List the body fields before coding.",
            IsCompleted = false,
            Priority = "Medium"
        },
        new TaskItem
        {
            Id = 2,
            Title = "Review the validation rules",
            Description = "Check the required title and priority values.",
            IsCompleted = false,
            Priority = "High"
        }
    ];

    private int _nextId = 3;

    /// <summary>
    /// Gets all stored tasks.
    /// </summary>
    /// <returns>The current task list.</returns>
    public IReadOnlyList<TaskItem> GetAll()
    {
        return _tasks.Select(CloneTask).ToList();
    }

    /// <summary>
    /// Finds a task by id.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <returns>The matching task, or <see langword="null"/> when not found.</returns>
    public TaskItem? GetById(int id)
    {
        var task = _tasks.FirstOrDefault(item => item.Id == id);
        return task is null ? null : CloneTask(task);
    }

    /// <summary>
    /// Adds a new task to the store.
    /// </summary>
    /// <param name="task">The task to add.</param>
    /// <returns>The stored task.</returns>
    public TaskItem Add(TaskItem task)
    {
        task.Id = _nextId++;
        _tasks.Add(CloneTask(task));
        return CloneTask(task);
    }

    /// <summary>
    /// Updates an existing task.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <param name="updatedTask">The new task values.</param>
    /// <returns>The updated task, or <see langword="null"/> when not found.</returns>
    public TaskItem? Update(int id, TaskItem updatedTask)
    {
        var existingTask = _tasks.FirstOrDefault(item => item.Id == id);
        if (existingTask is null)
        {
            return null;
        }

        existingTask.Title = updatedTask.Title;
        existingTask.Description = updatedTask.Description;
        existingTask.Priority = updatedTask.Priority;
        existingTask.IsCompleted = updatedTask.IsCompleted;

        return CloneTask(existingTask);
    }

    /// <summary>
    /// Deletes a task by id.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <returns><see langword="true"/> when the task was deleted.</returns>
    public bool Delete(int id)
    {
        var existingTask = _tasks.FirstOrDefault(item => item.Id == id);
        if (existingTask is null)
        {
            return false;
        }

        _tasks.Remove(existingTask);
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
