namespace LayoutsAndCollections;

using LayoutsAndCollections.Views;

/// <summary>
/// Shell container that hosts the layouts and collections demo page.
/// </summary>
public partial class AppShell : Shell
{
	/// <summary>
	/// Initializes shell content using DI-provided pages.
	/// </summary>
	/// <param name="mainPage">Primary page that demonstrates layouts and collections.</param>
	public AppShell(MainPage mainPage)
	{
		InitializeComponent();

		Items.Add(new ShellContent
		{
			Title = "Layouts",
			Route = "layouts",
			Content = mainPage
		});
	}
}
