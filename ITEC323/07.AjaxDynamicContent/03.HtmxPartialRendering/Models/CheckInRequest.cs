using System.ComponentModel.DataAnnotations;

namespace HtmxPartialRendering.Models;

/// <summary>
/// Represents the form input posted by the HTMX sample form.
/// </summary>
public class CheckInRequest
{
    /// <summary>
    /// Gets or sets the completed task name.
    /// </summary>
    [Required]
    [StringLength(60, MinimumLength = 3)]
    public string TaskName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the short reflection entered by the student.
    /// </summary>
    [Required]
    [StringLength(160, MinimumLength = 5)]
    public string Reflection { get; set; } = string.Empty;
}
