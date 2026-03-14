namespace HelloWorldMAUI;

/// <summary>
/// Main learning page for the first MAUI project.
/// Demonstrates XAML UI controls connected to C# code-behind events.
/// </summary>
public partial class MainPage : ContentPage
{
	private int _count;

	/// <summary>
	/// Initializes the main page and loads UI elements from XAML.
	/// </summary>
	public MainPage()
	{
		InitializeComponent();
	}

	/// <summary>
	/// Handles the counter button click and updates text displayed on screen.
	/// </summary>
	/// <param name="sender">The UI button that raised the event.</param>
	/// <param name="e">Event data from the click action.</param>
	private void OnCounterClicked(object? sender, EventArgs e)
	{
		_count++;

		if (_count == 1)
			CounterBtn.Text = $"Clicked {_count} time";
		else
			CounterBtn.Text = $"Clicked {_count} times";

		StatusLabel.Text = $"Great! You clicked at {DateTime.Now:HH:mm:ss}.";

		SemanticScreenReader.Announce(CounterBtn.Text);
	}

	/// <summary>
	/// Resets the counter and updates the UI to the default state.
	/// </summary>
	/// <param name="sender">The reset button that raised the event.</param>
	/// <param name="e">Event data from the click action.</param>
	private void OnResetClicked(object? sender, EventArgs e)
	{
		_count = 0;
		CounterBtn.Text = "Tap to Count";
		StatusLabel.Text = "Counter reset. Press the button to start again.";
		SemanticScreenReader.Announce(StatusLabel.Text);
	}
}
