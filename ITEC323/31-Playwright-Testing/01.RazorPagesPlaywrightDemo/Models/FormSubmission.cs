using System.ComponentModel.DataAnnotations;

namespace RazorPagesPlaywrightDemo.Models;

/// <summary>
/// Stores the values entered in the sample form.
/// </summary>
public class FormSubmission
{
    /// <summary>
    /// Gets or sets the user's name.
    /// </summary>
    [Display(Name = "Name")]
    public string Name { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the user's email address.
    /// </summary>
    [Display(Name = "Email")]
    public string Email { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the user's favourite programming language.
    /// </summary>
    [Display(Name = "Favourite Language")]
    public string FavoriteLanguage { get; set; } = string.Empty;
}
