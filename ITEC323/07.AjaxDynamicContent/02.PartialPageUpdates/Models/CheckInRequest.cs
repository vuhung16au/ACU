using System.ComponentModel.DataAnnotations;

namespace PartialPageUpdates.Models;

/// <summary>
/// Represents the student input used to create one new activity log entry.
/// </summary>
public class CheckInRequest
{
    /// <summary>
    /// Gets or sets the activity name selected by the student.
    /// </summary>
    [Required]
    [StringLength(60, MinimumLength = 3)]
    public string ActivityName { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets a short reflection message.
    /// </summary>
    [Required]
    [StringLength(140, MinimumLength = 5)]
    public string Reflection { get; set; } = string.Empty;
}
