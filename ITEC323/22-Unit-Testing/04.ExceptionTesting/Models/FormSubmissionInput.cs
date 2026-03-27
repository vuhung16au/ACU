namespace ExceptionTestingDemo.Models;

/// <summary>
/// Stores the values entered in the sample form.
/// </summary>
public class FormSubmissionInput
{
    /// <summary>
    /// Gets or sets the name entered by the user.
    /// </summary>
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the email entered by the user.
    /// </summary>
    public string Email { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the favourite language selected by the user.
    /// </summary>
    public string FavoriteLanguage { get; set; } = string.Empty;
}
