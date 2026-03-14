using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using ShellNavigation.Models;
using ShellNavigation.Services;

namespace ShellNavigation.ViewModels;

/// <summary>
/// Detail view model that receives a task id from Shell query parameters.
/// </summary>
[QueryProperty(nameof(TaskId), "taskId")]
public partial class DetailViewModel : ObservableObject
{
	private readonly ITaskRepository _repository;

	[ObservableProperty]
	private string taskId = string.Empty;

	[ObservableProperty]
	private string title = "Task not found";

	[ObservableProperty]
	private string description = "No details available.";

	[ObservableProperty]
	private bool isCompleted;

	[ObservableProperty]
	private string statusMessage = "Waiting for route parameter...";

	private int ParsedTaskId => int.TryParse(TaskId, out var id) ? id : 0;

	/// <summary>
	/// Initializes detail view model with repository dependency.
	/// </summary>
	/// <param name="repository">Task data source used to resolve route ids.</param>
	public DetailViewModel(ITaskRepository repository)
	{
		_repository = repository;
	}

	partial void OnTaskIdChanged(string value)
	{
		LoadTask(value);
	}

	private void LoadTask(string value)
	{
		if (!int.TryParse(value, out var id))
		{
			Title = "Invalid task id";
			Description = "The route query parameter taskId is missing or invalid.";
			IsCompleted = false;
			StatusMessage = "Cannot load task from route.";
			return;
		}

		var task = _repository.GetById(id);
		if (task is null)
		{
			Title = "Task not found";
			Description = "No task exists for this route parameter.";
			IsCompleted = false;
			StatusMessage = "Try going back to the home list.";
			return;
		}

		Title = task.Title;
		Description = task.Description;
		IsCompleted = task.IsCompleted;
		StatusMessage = "Use Edit Task to navigate to the next route.";
	}

	/// <summary>
	/// Navigates to edit page while preserving task id in query string.
	/// </summary>
	[RelayCommand]
	private async Task EditAsync()
	{
		if (ParsedTaskId <= 0)
		{
			return;
		}

		await Shell.Current.GoToAsync($"{AppRoutes.TaskEditRoute}?taskId={ParsedTaskId}");
	}

	/// <summary>
	/// Navigates one level back to the home page.
	/// </summary>
	[RelayCommand]
	private async Task GoBackAsync()
	{
		await Shell.Current.GoToAsync("..");
	}
}
