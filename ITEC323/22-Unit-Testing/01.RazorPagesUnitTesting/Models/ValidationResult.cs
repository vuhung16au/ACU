namespace RazorPagesUnitTestingDemo.Models;

/// <summary>
/// Represents the validation outcome for a form submission.
/// </summary>
public class ValidationResult
{
    /// <summary>
    /// Gets the list of validation errors.
    /// </summary>
    public List<string> Errors { get; } = new();

    /// <summary>
    /// Gets a value indicating whether validation succeeded.
    /// </summary>
    public bool IsValid => Errors.Count == 0;
}
