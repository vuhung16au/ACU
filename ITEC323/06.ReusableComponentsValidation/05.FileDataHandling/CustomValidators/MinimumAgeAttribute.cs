using System.ComponentModel.DataAnnotations;

namespace FileDataHandlingDemo.CustomValidators;

/// <summary>
/// Validates that a date of birth meets a minimum age requirement.
/// </summary>
public class MinimumAgeAttribute : ValidationAttribute
{
    private readonly int _minimumAge;

    /// <summary>
    /// Initializes a new instance of the <see cref="MinimumAgeAttribute"/> class.
    /// </summary>
    /// <param name="minimumAge">The minimum allowed age in years.</param>
    public MinimumAgeAttribute(int minimumAge)
    {
        _minimumAge = minimumAge;
    }

    /// <summary>
    /// Validates that the supplied birth date is old enough.
    /// </summary>
    /// <param name="value">The field value being validated.</param>
    /// <param name="validationContext">The current validation context.</param>
    /// <returns>A validation result for the age check.</returns>
    protected override ValidationResult? IsValid(object? value, ValidationContext validationContext)
    {
        if (value is not DateTime birthDate)
        {
            return new ValidationResult("Date of birth is required.");
        }

        DateTime today = DateTime.Today;
        int age = today.Year - birthDate.Year;

        if (birthDate.Date > today.AddYears(-age))
        {
            age--;
        }

        if (age < _minimumAge)
        {
            return new ValidationResult($"You must be at least {_minimumAge} years old.");
        }

        return ValidationResult.Success;
    }
}
