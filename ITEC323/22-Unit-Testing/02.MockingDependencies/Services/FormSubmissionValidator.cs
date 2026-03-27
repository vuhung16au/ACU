using System.Text.RegularExpressions;
using MockingDependenciesDemo.Models;

namespace MockingDependenciesDemo.Services;

/// <summary>
/// Validates the simple form submission input and checks for duplicate emails.
/// </summary>
public class FormSubmissionValidator
{
    private static readonly Regex EmailPattern = new(
        @"^[^@\s]+@[^@\s]+\.[^@\s]+$",
        RegexOptions.Compiled | RegexOptions.CultureInvariant);

    private readonly IEmailRegistry _emailRegistry;

    /// <summary>
    /// Initializes a new instance of the <see cref="FormSubmissionValidator"/> class.
    /// </summary>
    /// <param name="emailRegistry">The email registry used to check for duplicate addresses.</param>
    public FormSubmissionValidator(IEmailRegistry emailRegistry)
    {
        _emailRegistry = emailRegistry;
    }

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
            return result;
        }

        var trimmedEmail = input.Email.Trim();
        if (!EmailPattern.IsMatch(trimmedEmail))
        {
            result.Errors.Add("Email must be a valid email address.");
            return result;
        }

        if (_emailRegistry.EmailExists(trimmedEmail))
        {
            result.Errors.Add("Email is already registered.");
        }

        return result;
    }
}
