using System.Collections.ObjectModel;
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using ComprehensiveTaskListApp.Models;
using ComprehensiveTaskListApp.Services;

namespace ComprehensiveTaskListApp.ViewModels;

/// <summary>
/// ViewModel for task list display, refresh, and list-level actions.
/// </summary>
public partial class TaskListViewModel : ObservableObject
{
	private readonly ITaskDataService _taskDataService;

	/// <summary>
	/// Collection bound to CollectionView.
	/// </summary>
	[ObservableProperty]
	private ObservableCollection<TaskItem> tasks = [];

	/// <summary>
	/// Indicates long-running list operations.
	/// </summary>
	[ObservableProperty]
	private bool isBusy;

	/// <summary>
	/// Indicates pull-to-refresh state.
	/// </summary>
	[ObservableProperty]
	private bool isRefreshing;

	/// <summary>
	/// User-facing status text displayed above the task list.
	/// </summary>
	[ObservableProperty]
	private string statusMessage = "Manage your tasks with add, edit, complete, and delete actions.";

	/// <summary>
	/// Tracks whether the first list load has completed.
	/// </summary>
	public bool HasLoadedOnce { get; private set; }

	/// <summary>
	/// Initializes the view model with an injected task service.
	/// </summary>
	/// <param name="taskDataService">Task persistence service.</param>
	public TaskListViewModel(ITaskDataService taskDataService)
	{
		_taskDataService = taskDataService;
	}

	/// <summary>
	/// Ensures the task list loads once when the page first appears.
	/// </summary>
	public async Task EnsureLoadedAsync()
	{
		if (HasLoadedOnce)
		{
			return;
		}

		await LoadTasksAsync();
		HasLoadedOnce = true;
	}

	/// <summary>
	/// Loads tasks from local persistence.
	/// </summary>
	[RelayCommand]
	private async Task LoadTasksAsync()
	{
		if (IsBusy)
		{
			return;
		}

		try
		{
			IsBusy = true;
			var loadedTasks = await _taskDataService.GetTasksAsync();
			Tasks = new ObservableCollection<TaskItem>(loadedTasks);
			StatusMessage = Tasks.Count == 0
				? "No tasks yet. Tap Add to create your first task."
				: $"Loaded {Tasks.Count} task(s).";
		}
		catch (Exception ex)
		{
			StatusMessage = "Unable to load tasks right now.";
			System.Diagnostics.Debug.WriteLine(ex);
		}
		finally
		{
			IsBusy = false;
		}
	}

	/// <summary>
	/// Pull-to-refresh command for reloading the task list.
	/// </summary>
	[RelayCommand]
	private async Task RefreshAsync()
	{
		if (IsRefreshing)
		{
			return;
		}

		try
		{
			IsRefreshing = true;
			await LoadTasksAsync();
			StatusMessage = $"Last refreshed at {DateTime.Now:HH:mm:ss}.";
		}
		finally
		{
			IsRefreshing = false;
		}
	}

	/// <summary>
	/// Navigates to detail page in create mode.
	/// </summary>
	[RelayCommand]
	private Task AddTaskAsync()
	{
		return Shell.Current.GoToAsync(AppRoutes.TaskDetailRoute);
	}

	/// <summary>
	/// Navigates to detail page in edit mode for the selected task.
	/// </summary>
	/// <param name="task">Task to edit.</param>
	[RelayCommand]
	private Task EditTaskAsync(TaskItem? task)
	{
		if (task is null)
		{
			return Task.CompletedTask;
		}

		return Shell.Current.GoToAsync($"{AppRoutes.TaskDetailRoute}?taskId={Uri.EscapeDataString(task.Id)}");
	}

	/// <summary>
	/// Toggles completion for the selected task.
	/// </summary>
	/// <param name="task">Task to toggle.</param>
	[RelayCommand]
	private async Task ToggleCompletedAsync(TaskItem? task)
	{
		if (task is null)
		{
			return;
		}

		await _taskDataService.ToggleCompletedAsync(task.Id);
		await LoadTasksAsync();
	}

	/// <summary>
	/// Deletes a task after user confirmation.
	/// </summary>
	/// <param name="task">Task to delete.</param>
	[RelayCommand]
	private async Task DeleteTaskAsync(TaskItem? task)
	{
		if (task is null)
		{
			return;
		}

		var confirm = await Shell.Current.DisplayAlertAsync(
			"Delete Task",
			$"Delete '{task.Title}'?",
			"Delete",
			"Cancel");

		if (!confirm)
		{
			return;
		}

		await _taskDataService.DeleteTaskAsync(task.Id);
		await LoadTasksAsync();
	}
}
