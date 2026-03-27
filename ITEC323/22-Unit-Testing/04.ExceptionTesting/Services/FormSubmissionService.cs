using System.Text.RegularExpressions;
using ExceptionTestingDemo.Models;

namespace ExceptionTestingDemo.Services;

/// <summary>
/// Validates and prepares submitted form values, throwing exceptions for failure paths.
/// </summary>
public class FormSubmissionService
{
    private static readonly Regex EmailPattern = new(
        @"^[^@\s]+@[^@\s]+\.[^@\s]+$",
        RegexOptions.Compiled | RegexOptions.CultureInvariant);

    private readonly IAsyncEmailRegistry _emailRegistry;

    /// <summary>
    /// Initializes a new instance of the <see cref="FormSubmissionService"/> class.
    /// </summary>
    /// <param name="emailRegistry">The async email registry used to check for duplicate addresses.</param>
    public FormSubmissionService(IAsyncEmailRegistry emailRegistry)
    {
        _emailRegistry = emailRegistry;
    }

    /// <summary>
    /// Validates and prepares the submitted values.
    /// </summary>
    /// <param name="input">The form input to validate and process.</param>
    /// <returns>A task containing the display-ready submission result.</returns>
    /// <exception cref="ArgumentException">Thrown when the name or email is missing.</exception>
    /// <exception cref="FormatException">Thrown when the email format is invalid.</exception>
    /// <exception cref="InvalidOperationException">Thrown when the email is already registered.</exception>
    public async Task<SubmissionResult> SubmitAsync(FormSubmissionInput input)
    {
        if (string.IsNullOrWhiteSpace(input.Name))
        {
            throw new ArgumentException("Name is required.");
        }

        if (string.IsNullOrWhiteSpace(input.Email))
        {
            throw new ArgumentException("Email is required.");
        }

        var trimmedEmail = input.Email.Trim();
        if (!EmailPattern.IsMatch(trimmedEmail))
        {
            throw new FormatException("Email must be a valid email address.");
        }

        if (await _emailRegistry.EmailExistsAsync(trimmedEmail))
        {
            throw new InvalidOperationException("Email is already registered.");
        }

        return new SubmissionResult
        {
            Name = input.Name.Trim(),
            Email = trimmedEmail,
            FavoriteLanguage = string.IsNullOrWhiteSpace(input.FavoriteLanguage)
                ? "Unselected"
                : input.FavoriteLanguage
        };
    }
}
