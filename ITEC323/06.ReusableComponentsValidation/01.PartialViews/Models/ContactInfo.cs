using System.ComponentModel.DataAnnotations;

namespace PartialViews.Models;

/// <summary>
/// Represents the data entered in the reusable contact form partial.
/// </summary>
public class ContactInfo
{
    /// <summary>
    /// Gets or sets the full name entered by the user.
    /// </summary>
    [Display(Name = "Full Name")]
    public string FullName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the email address entered by the user.
    /// </summary>
    [Display(Name = "Email Address")]
    [EmailAddress]
    public string Email { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the topic selected by the user.
    /// </summary>
    public string Topic { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the message entered by the user.
    /// </summary>
    public string Message { get; set; } = string.Empty;
}
