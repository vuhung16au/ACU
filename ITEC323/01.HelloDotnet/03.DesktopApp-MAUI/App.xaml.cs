namespace HelloWorldMaui;

/// <summary>
/// Represents the root MAUI application object.
/// </summary>
public partial class App : Application
{
    /// <summary>
    /// Initializes a new instance of the <see cref="App"/> class.
    /// </summary>
    public App()
    {
        InitializeComponent();
        MainPage = new MainPage();
    }
}
