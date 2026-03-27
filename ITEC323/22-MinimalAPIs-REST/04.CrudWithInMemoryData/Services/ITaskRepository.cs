using CrudWithInMemoryDataDemo.Models;

namespace CrudWithInMemoryDataDemo.Services;

/// <summary>
/// Defines basic task repository operations.
/// </summary>
public interface ITaskRepository
{
    /// <summary>
    /// Gets all tasks.
    /// </summary>
    /// <returns>The current task list.</returns>
    IReadOnlyList<TaskItem> GetAll();

    /// <summary>
    /// Finds a task by id.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <returns>The matching task, or <see langword="null"/> when it is not found.</returns>
    TaskItem? GetById(int id);

    /// <summary>
    /// Creates a task.
    /// </summary>
    /// <param name="request">The task creation request.</param>
    /// <returns>The created task.</returns>
    TaskItem Create(CreateTaskRequest request);

    /// <summary>
    /// Updates a task.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <param name="request">The updated values.</param>
    /// <returns>The updated task, or <see langword="null"/> when it is not found.</returns>
    TaskItem? Update(int id, UpdateTaskRequest request);

    /// <summary>
    /// Deletes a task.
    /// </summary>
    /// <param name="id">The task identifier.</param>
    /// <returns><see langword="true"/> when the task was deleted.</returns>
    bool Delete(int id);
}
