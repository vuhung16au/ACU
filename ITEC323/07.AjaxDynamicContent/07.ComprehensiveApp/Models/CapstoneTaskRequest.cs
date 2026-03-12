using System.ComponentModel.DataAnnotations;

namespace ComprehensiveApp.Models;

/// <summary>
/// Represents the request body used to create or update a capstone task.
/// </summary>
public class CapstoneTaskRequest
{
    /// <summary>
    /// Gets or sets the task title.
    /// </summary>
    [Required]
    [StringLength(80, MinimumLength = 3)]
    public string Title { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the technique label.
    /// </summary>
    [Required]
    [StringLength(30, MinimumLength = 3)]
    public string Technique { get; set; } = string.Empty;

    /// <summary>
    /// Gets or sets the optional description.
    /// </summary>
    [StringLength(200)]
    public string? Description { get; set; }

    /// <summary>
    /// Gets or sets a value indicating whether the task is complete.
    /// </summary>
    public bool IsCompleted { get; set; }
}
