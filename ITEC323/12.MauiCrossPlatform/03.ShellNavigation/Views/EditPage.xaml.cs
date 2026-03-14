using ShellNavigation.ViewModels;

namespace ShellNavigation.Views;

/// <summary>
/// Edit page in the Shell route hierarchy.
/// </summary>
public partial class EditPage : ContentPage
{
	/// <summary>
	/// Initializes edit page with constructor-injected view model.
	/// </summary>
	/// <param name="viewModel">Edit view model receiving task id from query string.</param>
	public EditPage(EditViewModel viewModel)
	{
		InitializeComponent();
		BindingContext = viewModel;
	}
}
