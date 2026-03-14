using ShellNavigation.Views;

namespace ShellNavigation;

/// <summary>
/// Shell root for list to detail to edit navigation flow.
/// </summary>
public partial class AppShell : Shell
{
	/// <summary>
	/// Builds shell content and registers URI routes.
	/// </summary>
	/// <param name="homePage">Home page resolved from the DI container.</param>
	public AppShell(HomePage homePage)
	{
		InitializeComponent();

		Items.Add(new FlyoutItem
		{
			Title = "Tasks",
			Items =
			{
				new ShellContent
				{
					Title = "Home",
					Route = AppRoutes.HomeRoute,
					Content = homePage
				}
			}
		});

		Routing.RegisterRoute(AppRoutes.TaskDetailRoute, typeof(DetailPage));
		Routing.RegisterRoute(AppRoutes.TaskEditRoute, typeof(EditPage));
	}
}
