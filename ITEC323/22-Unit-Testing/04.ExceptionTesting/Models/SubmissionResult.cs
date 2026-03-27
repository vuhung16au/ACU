namespace ExceptionTestingDemo.Models;

/// <summary>
/// Stores the values displayed after a successful submission.
/// </summary>
public class SubmissionResult
{
    /// <summary>
    /// Gets or sets the final name value to display.
    /// </summary>
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the final email value to display.
    /// </summary>
    public string Email { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the final favourite language value to display.
    /// </summary>
    public string FavoriteLanguage { get; set; } = string.Empty;
}
