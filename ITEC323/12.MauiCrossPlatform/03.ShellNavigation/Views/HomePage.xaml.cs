using ShellNavigation.ViewModels;

namespace ShellNavigation.Views;

/// <summary>
/// Home page displaying the list route in Shell navigation flow.
/// </summary>
public partial class HomePage : ContentPage
{
	private readonly HomeViewModel _viewModel;

	/// <summary>
	/// Initializes page with constructor-injected view model.
	/// </summary>
	/// <param name="viewModel">Home list view model.</param>
	public HomePage(HomeViewModel viewModel)
	{
		InitializeComponent();
		BindingContext = _viewModel = viewModel;
	}

	/// <summary>
	/// Refreshes list each time the page appears.
	/// </summary>
	protected override async void OnAppearing()
	{
		base.OnAppearing();
		if (_viewModel.LoadCommand.CanExecute(null))
		{
			await _viewModel.LoadCommand.ExecuteAsync(null);
		}
	}
}
