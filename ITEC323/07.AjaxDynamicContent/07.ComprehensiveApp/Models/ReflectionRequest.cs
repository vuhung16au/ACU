using System.ComponentModel.DataAnnotations;

namespace ComprehensiveApp.Models;

/// <summary>
/// Represents the HTMX-style reflection form input.
/// </summary>
public class ReflectionRequest
{
    /// <summary>
    /// Gets or sets the technique label.
    /// </summary>
    [Required]
    [StringLength(30, MinimumLength = 3)]
    public string Technique { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the reflection message.
    /// </summary>
    [Required]
    [StringLength(160, MinimumLength = 5)]
    public string Message { get; set; } = string.Empty;
}
