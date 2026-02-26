using Foundation;

namespace HelloWorldMaui;

/// <summary>
/// macOS app delegate for the MAUI desktop application.
/// </summary>
[Register("AppDelegate")]
public class AppDelegate : MauiUIApplicationDelegate
{
    /// <summary>
    /// Creates the MAUI application during macOS startup.
    /// </summary>
    /// <returns>The configured MAUI application.</returns>
    protected override MauiApp CreateMauiApp() => MauiProgram.CreateMauiApp();
}
