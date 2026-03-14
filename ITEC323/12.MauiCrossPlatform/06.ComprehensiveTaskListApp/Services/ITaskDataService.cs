using ComprehensiveTaskListApp.Models;

namespace ComprehensiveTaskListApp.Services;

/// <summary>
/// Defines CRUD operations for persisted task data.
/// </summary>
public interface ITaskDataService
{
	/// <summary>
	/// Gets all saved tasks.
	/// </summary>
	/// <returns>A read-only list of tasks ordered for display.</returns>
	Task<IReadOnlyList<TaskItem>> GetTasksAsync();

	/// <summary>
	/// Finds a task by unique identifier.
	/// </summary>
	/// <param name="taskId">Task identifier.</param>
	/// <returns>The matching task or null when not found.</returns>
	Task<TaskItem?> GetTaskByIdAsync(string taskId);

	/// <summary>
	/// Adds a task to local storage.
	/// </summary>
	/// <param name="task">Task to add.</param>
	Task AddTaskAsync(TaskItem task);

	/// <summary>
	/// Updates an existing task in local storage.
	/// </summary>
	/// <param name="task">Task with modified values.</param>
	Task UpdateTaskAsync(TaskItem task);

	/// <summary>
	/// Deletes a task from local storage.
	/// </summary>
	/// <param name="taskId">Task identifier.</param>
	Task DeleteTaskAsync(string taskId);

	/// <summary>
	/// Flips a task completion state.
	/// </summary>
	/// <param name="taskId">Task identifier.</param>
	Task ToggleCompletedAsync(string taskId);
}
