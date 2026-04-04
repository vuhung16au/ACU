namespace GeminiTextAndRagApiDemo.Models;

/// <summary>
/// Represents the API response for direct text generation.
/// </summary>
public class GenerateTextResponse
{
    /// <summary>
    /// Gets or sets the original prompt.
    /// </summary>
    public required string Prompt { get; set; }

    /// <summary>
    /// Gets or sets the Gemini model name used for generation.
    /// </summary>
    public required string Model { get; set; }

    /// <summary>
    /// Gets or sets the generated text.
    /// </summary>
    public required string ResponseText { get; set; }
}
