using Microsoft.Extensions.Logging;
using ComprehensiveTaskListApp.Services;
using ComprehensiveTaskListApp.ViewModels;
using ComprehensiveTaskListApp.Views;
using Microsoft.Maui.Storage;

namespace ComprehensiveTaskListApp;

public static class MauiProgram
{
	/// <summary>
	/// Creates and configures the MAUI app instance.
	/// </summary>
	/// <returns>A configured <see cref="MauiApp"/> object.</returns>
	public static MauiApp CreateMauiApp()
	{
		var builder = MauiApp.CreateBuilder();
		builder
			.UseMauiApp<App>()
			.ConfigureFonts(fonts =>
			{
				fonts.AddFont("OpenSans-Regular.ttf", "OpenSansRegular");
				fonts.AddFont("OpenSans-Semibold.ttf", "OpenSansSemibold");
			});

		builder.Services.AddSingleton<IPreferences>(_ => Preferences.Default);
		builder.Services.AddSingleton<ITaskDataService, TaskDataService>();
		builder.Services.AddTransient<TaskListViewModel>();
		builder.Services.AddTransient<TaskDetailViewModel>();
		builder.Services.AddTransient<MainPage>();
		builder.Services.AddTransient<TaskDetailPage>();
		builder.Services.AddSingleton<AppShell>();

#if DEBUG
		builder.Logging.AddDebug();
#endif

		return builder.Build();
	}
}
