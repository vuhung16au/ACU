using System.ComponentModel.DataAnnotations;
using ComprehensiveAppDemo.CustomValidators;

namespace ComprehensiveAppDemo.Models;

/// <summary>
/// Represents a user managed by the comprehensive application.
/// </summary>
public class User
{
    /// <summary>
    /// Gets or sets the unique user identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the full name.
    /// </summary>
    [Required(ErrorMessage = "Full name is required.")]
    [StringLength(100, MinimumLength = 2, ErrorMessage = "Full name must be between 2 and 100 characters.")]
    [Display(Name = "Full Name")]
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the email address.
    /// </summary>
    [Required(ErrorMessage = "Email is required.")]
    [EmailAddress(ErrorMessage = "Enter a valid email address.")]
    public string Email { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the phone number.
    /// </summary>
    [Required(ErrorMessage = "Phone number is required.")]
    [Phone(ErrorMessage = "Enter a valid phone number.")]
    [RegularExpression(@"^04\d{8}$", ErrorMessage = "Australian mobile numbers must start with 04 and contain 10 digits.")]
    public string Phone { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the birth date.
    /// </summary>
    [Required(ErrorMessage = "Date of birth is required.")]
    [DataType(DataType.Date)]
    [Display(Name = "Date of Birth")]
    [MinimumAge(18)]
    public DateTime? DateOfBirth { get; set; }

    /// <summary>
    /// Gets or sets the password.
    /// </summary>
    [Required(ErrorMessage = "Password is required.")]
    [StringLength(100, MinimumLength = 8, ErrorMessage = "Password must be at least 8 characters.")]
    [RegularExpression(@"^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$", ErrorMessage = "Password must contain upper, lower, number, and special character.")]
    [DataType(DataType.Password)]
    public string Password { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the password confirmation.
    /// </summary>
    [Required(ErrorMessage = "Confirm password is required.")]
    [Compare("Password", ErrorMessage = "Password and confirmation must match.")]
    [DataType(DataType.Password)]
    [Display(Name = "Confirm Password")]
    public string ConfirmPassword { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the short biography text.
    /// </summary>
    [StringLength(500, ErrorMessage = "Bio must be 500 characters or fewer.")]
    public string Bio { get; set; } = string.Empty;

    /// <summary>
    /// Gets the calculated age.
    /// </summary>
    public int Age
    {
        get
        {
            if (DateOfBirth is null)
            {
                return 0;
            }

            DateTime today = DateTime.Today;
            int age = today.Year - DateOfBirth.Value.Year;

            if (DateOfBirth.Value.Date > today.AddYears(-age))
            {
                age--;
            }

            return age;
        }
    }

    /// <summary>
    /// Gets or sets the recent activity message used by the sidebar component.
    /// </summary>
    public string RecentActivity { get; set; } = string.Empty;
}
