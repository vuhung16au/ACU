namespace GeminiTextAndRagApiDemo.Services;

/// <summary>
/// Defines a simple text generation abstraction for Gemini and test doubles.
/// </summary>
public interface ITextGenerationService
{
    /// <summary>
    /// Generates text from the supplied prompt.
    /// </summary>
    /// <param name="prompt">The prompt sent to the model.</param>
    /// <returns>The generated text.</returns>
    Task<string> GenerateTextAsync(string prompt);
}
