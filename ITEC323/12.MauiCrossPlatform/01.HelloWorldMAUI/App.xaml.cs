namespace HelloWorldMAUI;

/// <summary>
/// Root MAUI application object that initializes app resources and window creation.
/// </summary>
public partial class App : Application
{
	/// <summary>
	/// Initializes a new instance of the <see cref="App"/> class.
	/// </summary>
	public App()
	{
		InitializeComponent();
	}

	/// <summary>
	/// Creates the first window and sets <see cref="AppShell"/> as the startup container.
	/// </summary>
	/// <param name="activationState">The activation state provided by the platform.</param>
	/// <returns>The configured application window.</returns>
	protected override Window CreateWindow(IActivationState? activationState)
	{
		return new Window(new AppShell());
	}
}