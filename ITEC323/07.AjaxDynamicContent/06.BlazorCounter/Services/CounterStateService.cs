namespace BlazorCounter.Services;

/// <summary>
/// Stores the shared counter state for the Blazor Server sample.
/// </summary>
public class CounterStateService
{
    private readonly object _syncRoot = new();
    private int _currentCount;

    /// <summary>
    /// Occurs when the counter value changes.
    /// </summary>
    public event Action? CounterChanged;

    /// <summary>
    /// Gets the current counter value.
    /// </summary>
    public int CurrentCount
    {
        get
        {
            lock (_syncRoot)
            {
                return _currentCount;
            }
        }
    }

    /// <summary>
    /// Increases the counter by one and notifies connected components.
    /// </summary>
    public void Increment()
    {
        lock (_syncRoot)
        {
            _currentCount++;
        }

        CounterChanged?.Invoke();
    }

    /// <summary>
    /// Decreases the counter by one and notifies connected components.
    /// </summary>
    public void Decrement()
    {
        lock (_syncRoot)
        {
            _currentCount--;
        }

        CounterChanged?.Invoke();
    }

    /// <summary>
    /// Resets the counter to zero and notifies connected components.
    /// </summary>
    public void Reset()
    {
        lock (_syncRoot)
        {
            _currentCount = 0;
        }

        CounterChanged?.Invoke();
    }
}
