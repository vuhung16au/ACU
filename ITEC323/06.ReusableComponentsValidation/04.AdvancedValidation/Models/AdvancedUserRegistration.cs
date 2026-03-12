using System.ComponentModel.DataAnnotations;
using AdvancedValidationDemo.CustomValidators;

namespace AdvancedValidationDemo.Models;

/// <summary>
/// Represents a registration form with advanced validation rules.
/// </summary>
public class AdvancedUserRegistration
{
    /// <summary>
    /// Gets or sets the username.
    /// </summary>
    [Required(ErrorMessage = "Username is required.")]
    [RegularExpression(@"^[a-zA-Z0-9_]{3,20}$", ErrorMessage = "Username must be 3 to 20 characters using letters, numbers, or underscores.")]
    public string Username { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the Australian mobile number.
    /// </summary>
    [Required(ErrorMessage = "Mobile number is required.")]
    [Display(Name = "Australian Mobile")]
    [RegularExpression(@"^04\d{8}$", ErrorMessage = "Australian mobile numbers must start with 04 and contain 10 digits.")]
    public string MobileNumber { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the postal code.
    /// </summary>
    [Required(ErrorMessage = "Postal code is required.")]
    [RegularExpression(@"^\d{4}$", ErrorMessage = "Postal code must contain exactly 4 digits.")]
    public string PostalCode { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the password.
    /// </summary>
    [Required(ErrorMessage = "Password is required.")]
    [DataType(DataType.Password)]
    [RegularExpression(@"^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$", ErrorMessage = "Password must contain upper, lower, number, special character, and be at least 8 characters.")]
    public string Password { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the password confirmation.
    /// </summary>
    [Required(ErrorMessage = "Confirm password is required.")]
    [DataType(DataType.Password)]
    [Compare("Password", ErrorMessage = "Password and confirmation must match.")]
    [Display(Name = "Confirm Password")]
    public string ConfirmPassword { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the user's birth date.
    /// </summary>
    [Required(ErrorMessage = "Date of birth is required.")]
    [DataType(DataType.Date)]
    [Display(Name = "Date of Birth")]
    [MinimumAge(18)]
    public DateTime? DateOfBirth { get; set; }
}
