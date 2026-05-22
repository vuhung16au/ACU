namespace HelloWorldMAUI.Shared.Services;

/// <summary>
/// Stores the shared counter lesson state used by the native and web hosts.
/// </summary>
public class CounterState
{
    /// <summary>
    /// Gets the current number of button clicks.
    /// </summary>
    public int Count { get; private set; }

    /// <summary>
    /// Gets the current status message shown in the lesson UI.
    /// </summary>
    public string StatusMessage { get; private set; } = "Press the button to update this message.";

    /// <summary>
    /// Gets the text shown on the main counter button.
    /// </summary>
    public string ButtonText =>
        Count == 0
            ? "Tap to Count"
            : Count == 1
                ? "Clicked 1 time"
                : $"Clicked {Count} times";

    /// <summary>
    /// Increments the counter and updates the status message.
    /// </summary>
    public void Increment()
    {
        Count++;
        StatusMessage = $"Great! You clicked at {DateTime.Now:HH:mm:ss}.";
    }

    /// <summary>
    /// Resets the counter lesson back to its starting state.
    /// </summary>
    public void Reset()
    {
        Count = 0;
        StatusMessage = "Counter reset. Press the button to start again.";
    }
}
