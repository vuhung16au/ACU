using System.ComponentModel.DataAnnotations;

namespace BasicFormValidationDemo.Models;

/// <summary>
/// Represents the contact form data with validation rules.
/// </summary>
public class ContactMessage
{
    /// <summary>
    /// Gets or sets the sender's name.
    /// </summary>
    [Required(ErrorMessage = "Name is required.")]
    [StringLength(100, MinimumLength = 3, ErrorMessage = "Name must be between 3 and 100 characters.")]
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the sender's email address.
    /// </summary>
    [Required(ErrorMessage = "Email is required.")]
    [EmailAddress(ErrorMessage = "Enter a valid email address.")]
    [Display(Name = "Email Address")]
    public string Email { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the message body.
    /// </summary>
    [Required(ErrorMessage = "Message is required.")]
    [StringLength(300, MinimumLength = 10, ErrorMessage = "Message must be between 10 and 300 characters.")]
    public string Message { get; set; } = string.Empty;
}
