using System.Text.RegularExpressions;
using RazorPagesUnitTestingDemo.Models;

namespace RazorPagesUnitTestingDemo.Services;

/// <summary>
/// Validates the simple form submission input.
/// </summary>
public class FormSubmissionValidator
{
    private static readonly Regex EmailPattern = new(
        @"^[^@\s]+@[^@\s]+\.[^@\s]+$",
        RegexOptions.Compiled | RegexOptions.CultureInvariant);

    /// <summary>
    /// Validates the provided input values.
    /// </summary>
    /// <param name="input">The form input to validate.</param>
    /// <returns>A validation result containing any error messages.</returns>
    public ValidationResult Validate(FormSubmissionInput input)
    {
        var result = new ValidationResult();

        if (string.IsNullOrWhiteSpace(input.Name))
        {
            result.Errors.Add("Name is required.");
        }

        if (string.IsNullOrWhiteSpace(input.Email))
        {
            result.Errors.Add("Email is required.");
        }
        else if (!EmailPattern.IsMatch(input.Email))
        {
            result.Errors.Add("Email must be a valid email address.");
        }

        return result;
    }
}
