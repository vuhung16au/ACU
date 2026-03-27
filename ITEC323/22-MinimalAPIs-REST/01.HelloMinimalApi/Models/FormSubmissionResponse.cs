namespace HelloMinimalApiDemo.Models;

/// <summary>
/// Stores the values returned by the sample submission endpoint.
/// </summary>
public class FormSubmissionResponse
{
    /// <summary>
    /// Gets or sets the final name value.
    /// </summary>
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the final email value.
    /// </summary>
    public string Email { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the final favourite language value.
    /// </summary>
    public string FavoriteLanguage { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets a short confirmation message.
    /// </summary>
    public string Message { get; set; } = string.Empty;
}
