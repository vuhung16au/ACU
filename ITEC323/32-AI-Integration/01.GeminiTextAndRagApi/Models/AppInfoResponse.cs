namespace GeminiTextAndRagApiDemo.Models;

/// <summary>
/// Represents the summary returned from the root endpoint.
/// </summary>
public class AppInfoResponse
{
    /// <summary>
    /// Gets or sets the API title.
    /// </summary>
    public required string Title { get; set; }

    /// <summary>
    /// Gets or sets the API description.
    /// </summary>
    public required string Description { get; set; }

    /// <summary>
    /// Gets or sets the configured model name.
    /// </summary>
    public required string Model { get; set; }

    /// <summary>
    /// Gets or sets the maximum number of retrieved documents.
    /// </summary>
    public int MaxDocuments { get; set; }

    /// <summary>
    /// Gets or sets the list of available endpoints.
    /// </summary>
    public required List<string> Endpoints { get; set; }
}
