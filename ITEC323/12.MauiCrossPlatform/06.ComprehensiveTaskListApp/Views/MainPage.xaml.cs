using ComprehensiveTaskListApp.ViewModels;

namespace ComprehensiveTaskListApp.Views;

/// <summary>
/// Main task list page that displays all tasks and list actions.
/// </summary>
public partial class MainPage : ContentPage
{
	private readonly TaskListViewModel _viewModel;

	/// <summary>
	/// Initializes the page with an injected list view model.
	/// </summary>
	/// <param name="viewModel">Task list ViewModel instance.</param>
	public MainPage(TaskListViewModel viewModel)
	{
		InitializeComponent();
		BindingContext = viewModel;
		_viewModel = viewModel;
	}

	/// <summary>
	/// Loads persisted tasks when the page appears.
	/// </summary>
	protected override async void OnAppearing()
	{
		base.OnAppearing();
		await _viewModel.EnsureLoadedAsync();
	}
}
