namespace GeminiTextAndRagRazorDemo.Services;

/// <summary>
/// Stores configuration values used by Gemini-related services.
/// </summary>
public class GeminiOptions
{
    /// <summary>
    /// Gets the configuration section name for Gemini options.
    /// </summary>
    public const string SectionName = "Gemini";

    /// <summary>
    /// Gets or sets the Gemini model name.
    /// </summary>
    public string Model { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the fallback model names tried when the primary model fails.
    /// </summary>
    public List<string> FallbackModels { get; set; } = new();

    /// <summary>
    /// Gets or sets the environment variable name that contains the API key.
    /// </summary>
    public string ApiKeyEnvironmentVariableName { get; set; } = "GOOGLE_API_KEY";
}
