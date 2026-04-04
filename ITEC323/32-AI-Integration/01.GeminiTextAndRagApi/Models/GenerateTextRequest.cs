namespace GeminiTextAndRagApiDemo.Models;

/// <summary>
/// Represents a request for direct Gemini text generation.
/// </summary>
public class GenerateTextRequest
{
    /// <summary>
    /// Gets or sets the prompt sent to Gemini.
    /// </summary>
    public required string Prompt { get; set; }
}
