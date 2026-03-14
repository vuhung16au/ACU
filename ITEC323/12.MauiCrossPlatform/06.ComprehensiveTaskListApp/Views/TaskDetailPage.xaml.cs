using ComprehensiveTaskListApp.ViewModels;

namespace ComprehensiveTaskListApp.Views;

/// <summary>
/// Task detail page for creating and editing tasks.
/// </summary>
public partial class TaskDetailPage : ContentPage
{
	private readonly TaskDetailViewModel _viewModel;

	/// <summary>
	/// Initializes the page with an injected detail view model.
	/// </summary>
	/// <param name="viewModel">Task detail ViewModel instance.</param>
	public TaskDetailPage(TaskDetailViewModel viewModel)
	{
		InitializeComponent();
		BindingContext = viewModel;
		_viewModel = viewModel;
	}

	/// <summary>
	/// Ensures create mode state is initialized when no route parameter is provided.
	/// </summary>
	protected override async void OnAppearing()
	{
		base.OnAppearing();
		if (string.IsNullOrWhiteSpace(_viewModel.TaskId))
		{
			await _viewModel.LoadTaskAsync();
		}
	}
}
