using LayoutsAndCollections.ViewModels;

namespace LayoutsAndCollections.Views;

/// <summary>
/// Main page that demonstrates responsive layouts and data-bound collections.
/// </summary>
public partial class MainPage : ContentPage
{
	private readonly LayoutsCollectionsViewModel _viewModel;

	/// <summary>
	/// Initializes the page with constructor-injected view model.
	/// </summary>
	/// <param name="viewModel">View model resolved by the DI container.</param>
	public MainPage(LayoutsCollectionsViewModel viewModel)
	{
		InitializeComponent();
		_viewModel = viewModel;
		BindingContext = _viewModel;
	}

	/// <summary>
	/// Loads initial item data every time the page appears.
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
