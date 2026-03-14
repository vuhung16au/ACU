using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using ComprehensiveTaskListApp.Models;
using ComprehensiveTaskListApp.Services;

namespace ComprehensiveTaskListApp.ViewModels;

/// <summary>
/// ViewModel for adding and editing a single task.
/// </summary>
[QueryProperty(nameof(TaskId), "taskId")]
public partial class TaskDetailViewModel : ObservableObject
{
	private readonly ITaskDataService _taskDataService;

	/// <summary>
	/// Query string task identifier used for edit mode.
	/// </summary>
	[ObservableProperty]
	private string taskId = string.Empty;

	/// <summary>
	/// Editable task title input.
	/// </summary>
	[ObservableProperty]
	private string title = string.Empty;

	/// <summary>
	/// Editable task description input.
	/// </summary>
	[ObservableProperty]
	private string description = string.Empty;

	/// <summary>
	/// Editable completion state.
	/// </summary>
	[ObservableProperty]
	private bool isCompleted;

	/// <summary>
	/// Current detail view title.
	/// </summary>
	[ObservableProperty]
	private string pageTitle = "New Task";

	/// <summary>
	/// User-facing status message.
	/// </summary>
	[ObservableProperty]
	private string statusMessage = "Enter a title and optional notes.";

	/// <summary>
	/// Busy flag used to guard command execution.
	/// </summary>
	[ObservableProperty]
	[NotifyCanExecuteChangedFor(nameof(DeleteTaskCommand))]
	private bool isBusy;

	/// <summary>
	/// Indicates whether this page edits an existing task.
	/// </summary>
	[ObservableProperty]
	[NotifyCanExecuteChangedFor(nameof(DeleteTaskCommand))]
	private bool isEditMode;

	/// <summary>
	/// Initializes the detail view model.
	/// </summary>
	/// <param name="taskDataService">Task persistence service.</param>
	public TaskDetailViewModel(ITaskDataService taskDataService)
	{
		_taskDataService = taskDataService;
	}

	/// <summary>
	/// Determines if the delete command can execute.
	/// </summary>
	public bool CanDeleteTask => IsEditMode && !IsBusy;

	partial void OnTaskIdChanged(string value)
	{
		_ = LoadTaskAsync();
	}

	/// <summary>
	/// Loads an existing task for edit mode, or resets fields for create mode.
	/// </summary>
	public async Task LoadTaskAsync()
	{
		if (IsBusy)
		{
			return;
		}

		try
		{
			IsBusy = true;
			if (string.IsNullOrWhiteSpace(TaskId))
			{
				PrepareForCreateMode();
				return;
			}

			var task = await _taskDataService.GetTaskByIdAsync(TaskId);
			if (task is null)
			{
				PrepareForCreateMode();
				StatusMessage = "Task not found. You can create a new one.";
				return;
			}

			Title = task.Title;
			Description = task.Description;
			IsCompleted = task.IsCompleted;
			PageTitle = "Edit Task";
			IsEditMode = true;
			StatusMessage = "Update fields, then tap Save.";
		}
		catch (Exception ex)
		{
			StatusMessage = "Unable to load this task.";
			System.Diagnostics.Debug.WriteLine(ex);
		}
		finally
		{
			IsBusy = false;
		}
	}

	/// <summary>
	/// Saves task changes and returns to the list page.
	/// </summary>
	[RelayCommand]
	private async Task SaveAsync()
	{
		if (IsBusy)
		{
			return;
		}

		var trimmedTitle = Title.Trim();
		if (string.IsNullOrWhiteSpace(trimmedTitle))
		{
			StatusMessage = "Title is required.";
			return;
		}

		try
		{
			IsBusy = true;
			if (IsEditMode)
			{
				var existing = await _taskDataService.GetTaskByIdAsync(TaskId);
				if (existing is null)
				{
					StatusMessage = "Task no longer exists.";
					return;
				}

				existing.Title = trimmedTitle;
				existing.Description = Description.Trim();
				existing.IsCompleted = IsCompleted;
				await _taskDataService.UpdateTaskAsync(existing);
			}
			else
			{
				await _taskDataService.AddTaskAsync(new TaskItem
				{
					Title = trimmedTitle,
					Description = Description.Trim(),
					IsCompleted = IsCompleted
				});
			}

			await Shell.Current.GoToAsync("..");
		}
		catch (Exception ex)
		{
			StatusMessage = "Unable to save this task right now.";
			System.Diagnostics.Debug.WriteLine(ex);
		}
		finally
		{
			IsBusy = false;
		}
	}

	/// <summary>
	/// Cancels editing and returns to the list page.
	/// </summary>
	[RelayCommand]
	private Task CancelAsync()
	{
		return Shell.Current.GoToAsync("..");
	}

	/// <summary>
	/// Deletes the active task and returns to the list page.
	/// </summary>
	[RelayCommand(CanExecute = nameof(CanDeleteTask))]
	private async Task DeleteTaskAsync()
	{
		if (!CanDeleteTask)
		{
			return;
		}

		var confirm = await Shell.Current.DisplayAlertAsync(
			"Delete Task",
			"Delete this task permanently?",
			"Delete",
			"Cancel");

		if (!confirm)
		{
			return;
		}

		await _taskDataService.DeleteTaskAsync(TaskId);
		await Shell.Current.GoToAsync("..");
	}

	private void PrepareForCreateMode()
	{
		TaskId = string.Empty;
		Title = string.Empty;
		Description = string.Empty;
		IsCompleted = false;
		PageTitle = "New Task";
		IsEditMode = false;
		StatusMessage = "Enter a title and optional notes.";
	}
}
