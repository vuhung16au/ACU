namespace GeminiTextAndRagApiDemo.Models;

/// <summary>
/// Represents a simple error response for invalid API requests.
/// </summary>
public class ApiErrorResponse
{
    /// <summary>
    /// Gets or sets a short error code.
    /// </summary>
    public required string Error { get; set; }

    /// <summary>
    /// Gets or sets a beginner-friendly error message.
    /// </summary>
    public required string Message { get; set; }
}
