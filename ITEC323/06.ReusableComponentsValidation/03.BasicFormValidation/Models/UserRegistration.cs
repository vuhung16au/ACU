using System.ComponentModel.DataAnnotations;

namespace BasicFormValidationDemo.Models;

/// <summary>
/// Represents the registration form data with beginner-friendly validation rules.
/// </summary>
public class UserRegistration
{
    /// <summary>
    /// Gets or sets the user's full name.
    /// </summary>
    [Required(ErrorMessage = "Full name is required.")]
    [StringLength(100, MinimumLength = 3, ErrorMessage = "Full name must be between 3 and 100 characters.")]
    [Display(Name = "Full Name")]
    public string FullName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the user's email address.
    /// </summary>
    [Required(ErrorMessage = "Email address is required.")]
    [EmailAddress(ErrorMessage = "Enter a valid email address.")]
    [Display(Name = "Email Address")]
    public string Email { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the user's phone number.
    /// </summary>
    [Phone(ErrorMessage = "Enter a valid phone number.")]
    [Display(Name = "Phone Number")]
    public string? Phone { get; set; }

    /// <summary>
    /// Gets or sets the user's age.
    /// </summary>
    [Range(18, 120, ErrorMessage = "Age must be between 18 and 120.")]
    public int? Age { get; set; }
}
