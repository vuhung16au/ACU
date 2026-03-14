using Microsoft.Extensions.DependencyInjection;

namespace ShellNavigation;

/// <summary>
/// Root MAUI application object.
/// Resolves shell through dependency injection after resource initialization.
/// </summary>
public partial class App : Application
{
	private readonly IServiceProvider _services;

	/// <summary>
	/// Initializes the app with the root service provider.
	/// </summary>
	/// <param name="services">Service provider used to resolve the shell at window creation time.</param>
	public App(IServiceProvider services)
	{
		InitializeComponent();
		_services = services;
	}

	/// <summary>
	/// Creates the first application window.
	/// </summary>
	/// <param name="activationState">Platform activation metadata.</param>
	/// <returns>A window hosting the application shell.</returns>
	protected override Window CreateWindow(IActivationState? activationState)
	{
		var shell = _services.GetRequiredService<AppShell>();
		return new Window(shell);
	}
}