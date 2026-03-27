namespace ComprehensiveMinimalApiDemo.Services;

/// <summary>
/// Represents an error when a task cannot be found.
/// </summary>
public class TaskNotFoundException : Exception
{
    /// <summary>
    /// Initializes a new instance of the <see cref="TaskNotFoundException"/> class.
    /// </summary>
    /// <param name="message">The error message.</param>
    public TaskNotFoundException(string message)
        : base(message)
    {
    }
}
