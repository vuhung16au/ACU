using Microsoft.Extensions.Logging;

namespace HelloWorldMAUI;

/// <summary>
/// Configures the MAUI application services, fonts, and startup pipeline.
/// </summary>
public static class MauiProgram
{
	/// <summary>
	/// Creates and configures the MAUI app.
	/// </summary>
	/// <returns>A fully configured <see cref="MauiApp"/> instance.</returns>
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

#if DEBUG
		builder.Logging.AddDebug();
#endif

		return builder.Build();
	}
}
