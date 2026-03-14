using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using ShellNavigation.Models;
using ShellNavigation.Services;

namespace ShellNavigation.ViewModels;

/// <summary>
/// Edit view model that demonstrates two-way binding and save navigation.
/// </summary>
[QueryProperty(nameof(TaskId), "taskId")]
public partial class EditViewModel : ObservableObject
{
	private readonly ITaskRepository _repository;

	[ObservableProperty]
	private string taskId = string.Empty;

	[ObservableProperty]
	private string title = string.Empty;

	[ObservableProperty]
	private string description = string.Empty;

	[ObservableProperty]
	private bool isCompleted;

	[ObservableProperty]
	private string statusMessage = "Edit fields, then tap Save.";

	private int ParsedTaskId => int.TryParse(TaskId, out var id) ? id : 0;

	/// <summary>
	/// Initializes edit view model with repository dependency.
	/// </summary>
	/// <param name="repository">Task repository used to load and save edits.</param>
	public EditViewModel(ITaskRepository repository)
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
			StatusMessage = "Invalid task id parameter.";
			return;
		}

		var task = _repository.GetById(id);
		if (task is null)
		{
			StatusMessage = "Task not found for editing.";
			return;
		}

		Title = task.Title;
		Description = task.Description;
		IsCompleted = task.IsCompleted;
		StatusMessage = "Route parameter resolved. Update values and save.";
	}

	/// <summary>
	/// Saves edits and navigates back one page to detail route.
	/// </summary>
	[RelayCommand]
	private async Task SaveAsync()
	{
		if (ParsedTaskId <= 0)
		{
			StatusMessage = "Cannot save without a valid task id.";
			return;
		}

		var safeTitle = string.IsNullOrWhiteSpace(Title) ? "Untitled task" : Title.Trim();
		var safeDescription = string.IsNullOrWhiteSpace(Description) ? "No description." : Description.Trim();

		_repository.Update(new NavigationTask
		{
			Id = ParsedTaskId,
			Title = safeTitle,
			Description = safeDescription,
			IsCompleted = IsCompleted
		});

		await Shell.Current.GoToAsync("..");
	}

	/// <summary>
	/// Discards edits and navigates back to detail page.
	/// </summary>
	[RelayCommand]
	private async Task CancelAsync()
	{
		await Shell.Current.GoToAsync("..");
	}
}
