namespace ErrorHandlingAndStatusCodesDemo.Services;

/// <summary>
/// Represents an error when a task title already exists.
/// </summary>
public class DuplicateTaskTitleException : Exception
{
    /// <summary>
    /// Initializes a new instance of the <see cref="DuplicateTaskTitleException"/> class.
    /// </summary>
    /// <param name="message">The error message.</param>
    public DuplicateTaskTitleException(string message)
        : base(message)
    {
    }
}
