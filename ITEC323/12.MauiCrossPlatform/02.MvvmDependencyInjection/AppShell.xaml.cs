namespace MvvmDependencyInjection;

using MvvmDependencyInjection.Views;

/// <summary>
/// Shell container that hosts the app's top-level pages.
/// </summary>
public partial class AppShell : Shell
{
	/// <summary>
	/// Initializes shell content using DI-provided pages.
	/// </summary>
	/// <param name="mainPage">Primary MVVM page.</param>
	public AppShell(MainPage mainPage)
	{
		InitializeComponent();

		Items.Add(new ShellContent
		{
			Title = "Home",
			Route = "MainPage",
			Content = mainPage
		});
	}
}
