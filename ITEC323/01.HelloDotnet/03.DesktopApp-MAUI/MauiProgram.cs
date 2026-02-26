namespace HelloWorldMaui;

/// <summary>
/// Configures the MAUI application and registers required services.
/// </summary>
public static class MauiProgram
{
    /// <summary>
    /// Creates and configures the MAUI app instance.
    /// </summary>
    /// <returns>The configured <see cref="MauiApp"/> instance.</returns>
    public static MauiApp CreateMauiApp()
    {
        var builder = MauiApp.CreateBuilder();

        builder.UseMauiApp<App>();

        return builder.Build();
    }
}
