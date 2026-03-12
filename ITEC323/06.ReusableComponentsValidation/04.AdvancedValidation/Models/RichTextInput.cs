using System.ComponentModel.DataAnnotations;

namespace AdvancedValidationDemo.Models;

/// <summary>
/// Represents rich text input used for the XSS prevention demo.
/// </summary>
public class RichTextInput
{
    /// <summary>
    /// Gets or sets the blog title.
    /// </summary>
    [Required(ErrorMessage = "Title is required.")]
    [StringLength(100, MinimumLength = 3, ErrorMessage = "Title must be between 3 and 100 characters.")]
    [Display(Name = "Blog Title")]
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the HTML body entered by the user.
    /// </summary>
    [Required(ErrorMessage = "Blog content is required.")]
    [StringLength(1000, MinimumLength = 10, ErrorMessage = "Blog content must be between 10 and 1000 characters.")]
    [Display(Name = "Blog Content")]
    public string Content { get; set; } = string.Empty;
}
