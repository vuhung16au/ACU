using UIKit;

namespace HelloWorldMaui;

/// <summary>
/// Application entry point for Mac Catalyst.
/// </summary>
public static class Program
{
    /// <summary>
    /// Main entry point of the desktop app on macOS.
    /// </summary>
    /// <param name="args">Command-line arguments.</param>
    private static void Main(string[] args)
    {
        UIApplication.Main(args, null, typeof(AppDelegate));
    }
}
