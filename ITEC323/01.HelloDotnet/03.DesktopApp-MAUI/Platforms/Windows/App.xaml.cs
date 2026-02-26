namespace HelloWorldMaui.WinUI;

/// <summary>
/// Windows entry-point application class for the MAUI app.
/// </summary>
public partial class App : MauiWinUIApplication
{
    /// <summary>
    /// Initializes a new instance of the Windows app class.
    /// </summary>
    public App()
    {
        InitializeComponent();
    }

    /// <summary>
    /// Creates the MAUI application instance for Windows startup.
    /// </summary>
    /// <returns>The configured MAUI application.</returns>
    protected override MauiApp CreateMauiApp() => MauiProgram.CreateMauiApp();
}
