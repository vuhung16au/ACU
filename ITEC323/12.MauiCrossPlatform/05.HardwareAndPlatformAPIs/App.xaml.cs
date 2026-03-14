using Microsoft.Extensions.DependencyInjection;

namespace HardwareAndPlatformAPIs;

/// <summary>
/// Root MAUI application object.
/// Resolves the app shell through dependency injection.
/// </summary>
public partial class App : Application
{
	private readonly IServiceProvider _services;

	/// <summary>
	/// Initializes the app with an injected shell instance.
	/// </summary>
	/// <param name="services">Root service provider used to resolve shell on first window creation.</param>
	public App(IServiceProvider services)
	{
		InitializeComponent();
		_services = services;
	}

	/// <summary>
	/// Creates the initial application window.
	/// </summary>
	/// <param name="activationState">Platform-specific activation metadata.</param>
	/// <returns>The first MAUI window hosting the app shell.</returns>
	protected override Window CreateWindow(IActivationState? activationState)
	{
		var appShell = _services.GetRequiredService<AppShell>();
		return new Window(appShell);
	}
}