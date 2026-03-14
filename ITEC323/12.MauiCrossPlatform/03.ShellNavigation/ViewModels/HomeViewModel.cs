using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using ShellNavigation.Models;
using ShellNavigation.Services;
using System.Collections.ObjectModel;

namespace ShellNavigation.ViewModels;

/// <summary>
/// Home list view model for Shell navigation demos.
/// </summary>
public partial class HomeViewModel : ObservableObject
{
	private readonly ITaskRepository _repository;

	/// <summary>
	/// Tasks shown in CollectionView on the home page.
	/// </summary>
	[ObservableProperty]
	private ObservableCollection<NavigationTask> tasks = [];

	/// <summary>
	/// Short status text shown above the list.
	/// </summary>
	[ObservableProperty]
	private string statusMessage = "Use Shell routes to open a task detail page.";

	/// <summary>
	/// Indicates data loading in progress.
	/// </summary>
	[ObservableProperty]
	private bool isBusy;

	/// <summary>
	/// Initializes home view model with repository dependency.
	/// </summary>
	/// <param name="repository">Task data source for list and detail pages.</param>
	public HomeViewModel(ITaskRepository repository)
	{
		_repository = repository;
	}

	/// <summary>
	/// Loads tasks from repository into observable collection.
	/// </summary>
	[RelayCommand]
	private Task LoadAsync()
	{
		if (IsBusy)
		{
			return Task.CompletedTask;
		}

		try
		{
			IsBusy = true;
			Tasks = new ObservableCollection<NavigationTask>(_repository.GetAll());
			StatusMessage = $"Loaded {Tasks.Count} tasks. Tap View Detail to navigate.";
		}
		finally
		{
			IsBusy = false;
		}

		return Task.CompletedTask;
	}

	/// <summary>
	/// Navigates from list page to detail page with query parameter.
	/// </summary>
	/// <param name="task">Task selected by the user.</param>
	[RelayCommand]
	private async Task OpenDetailAsync(NavigationTask? task)
	{
		if (task is null)
		{
			return;
		}

		await Shell.Current.GoToAsync($"{AppRoutes.TaskDetailRoute}?taskId={task.Id}");
	}
}
