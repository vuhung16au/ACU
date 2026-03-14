using Microsoft.Extensions.Logging;
using ShellNavigation.Services;
using ShellNavigation.ViewModels;
using ShellNavigation.Views;

namespace ShellNavigation;

public static class MauiProgram
{
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

		builder.Services.AddSingleton<ITaskRepository, InMemoryTaskRepository>();
		builder.Services.AddTransient<HomeViewModel>();
		builder.Services.AddTransient<DetailViewModel>();
		builder.Services.AddTransient<EditViewModel>();

		builder.Services.AddTransient<HomePage>();
		builder.Services.AddTransient<DetailPage>();
		builder.Services.AddTransient<EditPage>();
		builder.Services.AddTransient<AppShell>();

#if DEBUG
		builder.Logging.AddDebug();
#endif

		return builder.Build();
	}
}
