using HardwareAndPlatformAPIs.ViewModels;

namespace HardwareAndPlatformAPIs.Views;

/// <summary>
/// Main page that demonstrates camera, GPS, and file-system platform APIs.
/// UI is driven entirely by <see cref="HardwarePlatformViewModel"/> via data binding.
/// </summary>
public partial class MainPage : ContentPage
{
	private readonly HardwarePlatformViewModel _viewModel;

	/// <summary>
	/// Initializes the page with a constructor-injected view model.
	/// </summary>
	/// <param name="viewModel">View model resolved by the DI container.</param>
	public MainPage(HardwarePlatformViewModel viewModel)
	{
		InitializeComponent();
		_viewModel = viewModel;
		BindingContext = _viewModel;

			// Toggle the photo placeholder visibility when HasPhoto changes.
			// MAUI XAML does not include a built-in InverseBool converter, so we
			// subscribe to PropertyChanged here instead of adding a custom converter.
			_viewModel.PropertyChanged += OnViewModelPropertyChanged;
	}

	/// <summary>
	/// Updates the photo placeholder Frame visibility when HasPhoto changes.
	/// </summary>
	private void OnViewModelPropertyChanged(object? sender, System.ComponentModel.PropertyChangedEventArgs e)
	{
		if (e.PropertyName == nameof(HardwarePlatformViewModel.HasPhoto))
		{
			// PhotoPlaceholder should be visible only when there is NO photo.
			var placeholder = this.FindByName<Border>("PhotoPlaceholder");
			if (placeholder is not null)
			{
				placeholder.IsVisible = !_viewModel.HasPhoto;
			}
		}
	}

	/// <inheritdoc />
	protected override void OnDisappearing()
	{
		base.OnDisappearing();
		_viewModel.PropertyChanged -= OnViewModelPropertyChanged;
	}
}
