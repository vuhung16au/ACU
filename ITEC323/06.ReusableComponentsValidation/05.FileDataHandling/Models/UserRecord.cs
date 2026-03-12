using System.ComponentModel.DataAnnotations;
using FileDataHandlingDemo.CustomValidators;

namespace FileDataHandlingDemo.Models;

/// <summary>
/// Represents one user record stored in the CSV file.
/// </summary>
public class UserRecord
{
    /// <summary>
    /// Gets or sets the unique user identifier.
    /// </summary>
    public int Id { get; set; }

    /// <summary>
    /// Gets or sets the user's full name.
    /// </summary>
    [Required(ErrorMessage = "Name is required.")]
    [StringLength(100, MinimumLength = 2, ErrorMessage = "Name must be between 2 and 100 characters.")]
    [Display(Name = "Full Name")]
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the user's email address.
    /// </summary>
    [Required(ErrorMessage = "Email is required.")]
    [EmailAddress(ErrorMessage = "Enter a valid email address.")]
    public string Email { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the user's phone number.
    /// </summary>
    [Required(ErrorMessage = "Phone number is required.")]
    [Phone(ErrorMessage = "Enter a valid phone number.")]
    [RegularExpression(@"^04\d{8}$", ErrorMessage = "Australian mobile numbers must start with 04 and contain 10 digits.")]
    public string Phone { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the user's date of birth.
    /// </summary>
    [Required(ErrorMessage = "Date of birth is required.")]
    [DataType(DataType.Date)]
    [Display(Name = "Date of Birth")]
    [MinimumAge(18)]
    public DateTime? DateOfBirth { get; set; }

    /// <summary>
    /// Gets the user's age calculated from the date of birth.
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
}
