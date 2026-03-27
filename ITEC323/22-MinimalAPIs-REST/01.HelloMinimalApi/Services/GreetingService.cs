namespace HelloMinimalApiDemo.Services;

/// <summary>
/// Builds friendly greeting messages for the sample API.
/// </summary>
public class GreetingService
{
    /// <summary>
    /// Creates a greeting for the supplied name.
    /// </summary>
    /// <param name="name">The name used in the greeting.</param>
    /// <returns>A beginner-friendly greeting message.</returns>
    public string CreateGreeting(string name)
    {
        var trimmedName = name.Trim();
        return string.IsNullOrWhiteSpace(trimmedName)
            ? "Hello, student!"
            : $"Hello, {trimmedName}!";
    }
}
