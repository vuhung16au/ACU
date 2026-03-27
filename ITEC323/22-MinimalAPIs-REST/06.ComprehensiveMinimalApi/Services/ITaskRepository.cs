using ComprehensiveMinimalApiDemo.Models;

namespace ComprehensiveMinimalApiDemo.Services;

/// <summary>
/// Defines task storage operations for the comprehensive sample.
/// </summary>
public interface ITaskRepository
{
    /// <summary>
    /// Gets all stored tasks.
    /// </summary>
    /// <returns>The current task list.</returns>
    IReadOnlyList<TaskItem> GetAll();

    /// <summary>
    /// Finds a task by id.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <returns>The task, or <see langword="null"/> when not found.</returns>
    TaskItem? GetById(int id);

    /// <summary>
    /// Adds a task.
    /// </summary>
    /// <param name="task">The task to store.</param>
    /// <returns>The stored task.</returns>
    TaskItem Add(TaskItem task);

    /// <summary>
    /// Updates a task.
    /// </summary>
    /// <param name="task">The updated task.</param>
    void Update(TaskItem task);

    /// <summary>
    /// Deletes a task.
    /// </summary>
    /// <param name="task">The task to delete.</param>
    void Delete(TaskItem task);
}
