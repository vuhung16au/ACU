namespace HelloMinimalApiDemo.Models;

/// <summary>
/// Stores the greeting returned by the sample greeting endpoint.
/// </summary>
public class GreetingResponse
{
    /// <summary>
    /// Gets or sets the greeting text.
    /// </summary>
    public string Greeting { get; set; } = string.Empty;
}
