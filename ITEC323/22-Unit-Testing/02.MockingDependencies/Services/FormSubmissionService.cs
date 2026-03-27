using MockingDependenciesDemo.Models;

namespace MockingDependenciesDemo.Services;

/// <summary>
/// Prepares submitted form values for display.
/// </summary>
public class FormSubmissionService
{
    /// <summary>
    /// Creates the values displayed after a valid submission.
    /// </summary>
    /// <param name="input">The validated form input.</param>
    /// <returns>A result object containing display-ready values.</returns>
    public SubmissionResult Process(FormSubmissionInput input)
    {
        return new SubmissionResult
        {
            Name = input.Name.Trim(),
            Email = input.Email.Trim(),
            FavoriteLanguage = string.IsNullOrWhiteSpace(input.FavoriteLanguage)
                ? "Unselected"
                : input.FavoriteLanguage
        };
    }
}
