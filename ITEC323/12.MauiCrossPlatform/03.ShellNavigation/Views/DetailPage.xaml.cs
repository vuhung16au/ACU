using ShellNavigation.ViewModels;

namespace ShellNavigation.Views;

/// <summary>
/// Detail page in list to detail to edit navigation chain.
/// </summary>
public partial class DetailPage : ContentPage
{
	/// <summary>
	/// Initializes detail page with constructor-injected view model.
	/// </summary>
	/// <param name="viewModel">Detail view model receiving Shell query values.</param>
	public DetailPage(DetailViewModel viewModel)
	{
		InitializeComponent();
		BindingContext = viewModel;
	}
}
