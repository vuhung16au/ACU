using Microsoft.Extensions.Logging;
using MvvmDependencyInjection.Services;
using MvvmDependencyInjection.ViewModels;
using MvvmDependencyInjection.Views;

namespace MvvmDependencyInjection;

/// <summary>
/// Configures MAUI startup, fonts, logging, and dependency injection.
/// </summary>
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

		builder.Services.AddSingleton<IWelcomeMessageService, WelcomeMessageService>();
		builder.Services.AddTransient<MainViewModel>();
		builder.Services.AddTransient<MainPage>();
		builder.Services.AddSingleton<AppShell>();

#if DEBUG
		builder.Logging.AddDebug();
#endif

		return builder.Build();
	}
}
