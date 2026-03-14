using ShellNavigation.Models;

namespace ShellNavigation.Services;

/// <summary>
/// Data source contract for demo task navigation.
/// </summary>
public interface ITaskRepository
{
	/// <summary>
	/// Returns all tasks for the home list page.
	/// </summary>
	IReadOnlyList<NavigationTask> GetAll();

	/// <summary>
	/// Returns one task by identifier.
	/// </summary>
	/// <param name="id">Task identifier from Shell route query.</param>
	NavigationTask? GetById(int id);

	/// <summary>
	/// Applies edits to an existing task.
	/// </summary>
	/// <param name="task">Task instance with new values.</param>
	void Update(NavigationTask task);
}
