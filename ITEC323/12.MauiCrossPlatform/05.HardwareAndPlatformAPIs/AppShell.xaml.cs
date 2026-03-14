namespace HardwareAndPlatformAPIs;

using HardwareAndPlatformAPIs.Views;

/// <summary>
/// Shell container that hosts the hardware and platform APIs demo page.
/// </summary>
public partial class AppShell : Shell
{
	/// <summary>
	/// Initializes shell content using the DI-provided hardware demo page.
	/// </summary>
	/// <param name="mainPage">Primary page that demonstrates camera, GPS, and file-system APIs.</param>
	public AppShell(MainPage mainPage)
	{
		InitializeComponent();

		Items.Add(new ShellContent
		{
			Title = "Hardware APIs",
			Route = "hardware",
			Content = mainPage
		});
	}
}
