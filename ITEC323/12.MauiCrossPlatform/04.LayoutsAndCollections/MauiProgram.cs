using Microsoft.Extensions.Logging;
using LayoutsAndCollections.Services;
using LayoutsAndCollections.ViewModels;
using LayoutsAndCollections.Views;

namespace LayoutsAndCollections;

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

		builder.Services.AddSingleton<ICollectionDataService, CollectionDataService>();
		builder.Services.AddTransient<LayoutsCollectionsViewModel>();
		builder.Services.AddTransient<MainPage>();
		builder.Services.AddSingleton<AppShell>();

#if DEBUG
		builder.Logging.AddDebug();
#endif

		return builder.Build();
	}
}
