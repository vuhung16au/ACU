namespace HelloMinimalApiDemo.Models;

/// <summary>
/// Stores the values sent to the sample submission endpoint.
/// </summary>
public class FormSubmissionRequest
{
    /// <summary>
    /// Gets or sets the user's name.
    /// </summary>
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the user's email address.
    /// </summary>
    public string Email { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the user's favourite programming language.
    /// </summary>
    public string FavoriteLanguage { get; set; } = string.Empty;
}
