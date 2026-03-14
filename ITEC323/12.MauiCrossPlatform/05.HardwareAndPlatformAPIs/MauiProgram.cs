using Microsoft.Extensions.Logging;
using HardwareAndPlatformAPIs.Services;
using HardwareAndPlatformAPIs.ViewModels;
using HardwareAndPlatformAPIs.Views;

namespace HardwareAndPlatformAPIs;

/// <summary>
/// Configures MAUI startup, fonts, logging, and dependency injection for the hardware demo.
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

		// IHardwareService wraps MediaPicker, Geolocation, and FileSystem APIs.
		builder.Services.AddSingleton<IHardwareService, HardwareService>();
		builder.Services.AddTransient<HardwarePlatformViewModel>();
		builder.Services.AddTransient<MainPage>();
		builder.Services.AddSingleton<AppShell>();

#if DEBUG
		builder.Logging.AddDebug();
#endif

		return builder.Build();
	}
}
