using ShellNavigation.Models;

namespace ShellNavigation.Services;

/// <summary>
/// In-memory repository used for learning Shell navigation flow.
/// </summary>
public sealed class InMemoryTaskRepository : ITaskRepository
{
	private readonly List<NavigationTask> _tasks =
	[
		new() { Id = 1, Title = "Prepare MAUI demo", Description = "Create the list page and route to task detail.", IsCompleted = false },
		new() { Id = 2, Title = "Explain QueryProperty", Description = "Show how route query parameters are received on destination pages.", IsCompleted = false },
		new() { Id = 3, Title = "Implement edit route", Description = "Navigate detail to edit, save values, then navigate back with ..", IsCompleted = true }
	];

	/// <inheritdoc />
	public IReadOnlyList<NavigationTask> GetAll()
	{
		return _tasks
			.Select(task => Clone(task))
			.ToList();
	}

	/// <inheritdoc />
	public NavigationTask? GetById(int id)
	{
		var task = _tasks.FirstOrDefault(t => t.Id == id);
		return task is null ? null : Clone(task);
	}

	/// <inheritdoc />
	public void Update(NavigationTask task)
	{
		var existing = _tasks.FirstOrDefault(t => t.Id == task.Id);
		if (existing is null)
		{
			return;
		}

		existing.Title = task.Title.Trim();
		existing.Description = task.Description.Trim();
		existing.IsCompleted = task.IsCompleted;
	}

	private static NavigationTask Clone(NavigationTask task)
	{
		return new NavigationTask
		{
			Id = task.Id,
			Title = task.Title,
			Description = task.Description,
			IsCompleted = task.IsCompleted
		};
	}
}
