using System.ComponentModel;
using MvvmDependencyInjection.ViewModels;

namespace MvvmDependencyInjection.Views;

/// <summary>
/// Main learning page that demonstrates MVVM data binding.
/// </summary>
public partial class MainPage : ContentPage
{
	private readonly MainViewModel _viewModel;

	/// <summary>
	/// Initializes the page with constructor-injected view model.
	/// </summary>
	/// <param name="viewModel">View model resolved by the DI container.</param>
	public MainPage(MainViewModel viewModel)
	{
		InitializeComponent();
		_viewModel = viewModel;
		BindingContext = _viewModel;
		_viewModel.PropertyChanged += OnViewModelPropertyChanged;
	}

	/// <summary>
	/// Announces updated button text for accessibility when count changes.
	/// </summary>
	/// <param name="sender">The view model publishing the property change.</param>
	/// <param name="e">Property change metadata.</param>
	private void OnViewModelPropertyChanged(object? sender, PropertyChangedEventArgs e)
	{
		if (e.PropertyName == nameof(MainViewModel.CounterButtonText))
		{
			SemanticScreenReader.Announce(_viewModel.CounterButtonText);
		}
	}

	/// <summary>
	/// Unhooks events when this page is removed.
	/// </summary>
	protected override void OnDisappearing()
	{
		base.OnDisappearing();
		_viewModel.PropertyChanged -= OnViewModelPropertyChanged;
	}
}
