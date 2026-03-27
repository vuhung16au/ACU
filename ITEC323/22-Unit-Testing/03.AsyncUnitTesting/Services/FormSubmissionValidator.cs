using System.Text.RegularExpressions;
using AsyncUnitTestingDemo.Models;

namespace AsyncUnitTestingDemo.Services;

/// <summary>
/// Validates the simple form submission input and checks asynchronously for duplicate emails.
/// </summary>
public class FormSubmissionValidator
{
    private static readonly Regex EmailPattern = new(
        @"^[^@\s]+@[^@\s]+\.[^@\s]+$",
        RegexOptions.Compiled | RegexOptions.CultureInvariant);

    private readonly IAsyncEmailRegistry _emailRegistry;

    /// <summary>
    /// Initializes a new instance of the <see cref="FormSubmissionValidator"/> class.
    /// </summary>
    /// <param name="emailRegistry">The email registry used to check for duplicate addresses.</param>
    public FormSubmissionValidator(IAsyncEmailRegistry emailRegistry)
    {
        _emailRegistry = emailRegistry;
    }

    /// <summary>
    /// Validates the provided input values.
    /// </summary>
    /// <param name="input">The form input to validate.</param>
    /// <returns>A validation result containing any error messages.</returns>
    public async Task<ValidationResult> ValidateAsync(FormSubmissionInput input)
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

        if (await _emailRegistry.EmailExistsAsync(trimmedEmail))
        {
            result.Errors.Add("Email is already registered.");
        }

        return result;
    }
}
