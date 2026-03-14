namespace ComprehensiveTaskListApp;

using ComprehensiveTaskListApp.Views;

/// <summary>
/// Shell container that hosts task list navigation and detail routes.
/// </summary>
public partial class AppShell : Shell
{
	/// <summary>
	/// Initializes shell content using dependency-injected pages.
	/// </summary>
	/// <param name="mainPage">Primary list page displaying all tasks.</param>
	/// <param name="taskDetailPage">Detail page used for create and edit flows.</param>
	public AppShell(MainPage mainPage, TaskDetailPage taskDetailPage)
	{
		InitializeComponent();

		Items.Add(new ShellContent
		{
			Title = "Tasks",
			Route = AppRoutes.TaskListRoute,
			Content = mainPage
		});

		Routing.RegisterRoute(AppRoutes.TaskDetailRoute, typeof(TaskDetailPage));
	}
}
