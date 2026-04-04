using Google.GenAI;
using Microsoft.Extensions.Options;

namespace GeminiTextAndRagRazorDemo.Services;

/// <summary>
/// Sends text generation requests to the Google Gemini API.
/// </summary>
public class GeminiTextService : ITextGenerationService
{
    private readonly GeminiOptions _options;

    /// <summary>
    /// Initializes a new instance of the <see cref="GeminiTextService"/> class.
    /// </summary>
    /// <param name="options">The configured Gemini options.</param>
    public GeminiTextService(IOptions<GeminiOptions> options)
    {
        _options = options.Value;
    }

    /// <summary>
    /// Generates text from the supplied prompt by calling Gemini.
    /// </summary>
    /// <param name="prompt">The prompt sent to Gemini.</param>
    /// <returns>The generated text response.</returns>
    public async Task<string> GenerateTextAsync(string prompt)
    {
        var apiKey = Environment.GetEnvironmentVariable(_options.ApiKeyEnvironmentVariableName);
        if (string.IsNullOrWhiteSpace(apiKey))
        {
            throw new InvalidOperationException(
                $"The Gemini API key was not found. Set {_options.ApiKeyEnvironmentVariableName} in .env.local.");
        }

        var modelsToTry = GetModelsToTry();
        var client = new Client(apiKey: apiKey);
        var failures = new List<string>();

        foreach (var modelName in modelsToTry)
        {
            try
            {
                var response = await client.Models.GenerateContentAsync(model: modelName, contents: prompt);
                var responseText = response.Candidates?
                    .FirstOrDefault()?
                    .Content?
                    .Parts?
                    .FirstOrDefault()?
                    .Text;

                if (!string.IsNullOrWhiteSpace(responseText))
                {
                    return responseText.Trim();
                }

                failures.Add($"{modelName}: empty response");
            }
            catch (Exception ex)
            {
                failures.Add($"{modelName}: {ex.Message}");
            }
        }

        throw new InvalidOperationException(
            $"Gemini could not generate a response with any configured model. Attempts: {string.Join(" | ", failures)}");
    }

    private List<string> GetModelsToTry()
    {
        var models = new List<string>();

        if (!string.IsNullOrWhiteSpace(_options.Model))
        {
            models.Add(_options.Model.Trim());
        }

        foreach (var fallbackModel in _options.FallbackModels)
        {
            if (!string.IsNullOrWhiteSpace(fallbackModel) &&
                !models.Contains(fallbackModel.Trim(), StringComparer.OrdinalIgnoreCase))
            {
                models.Add(fallbackModel.Trim());
            }
        }

        if (models.Count == 0)
        {
            throw new InvalidOperationException("No Gemini model is configured. Set Gemini:Model in appsettings.json.");
        }

        return models;
    }
}
